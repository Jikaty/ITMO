import { createServer } from 'node:http';
import { readFile } from 'node:fs/promises';

function validNumber(x,y,r){
    if(Number.isInteger(x) === false || x < -4 || x > 4){
        return false;
    }
    if(!Number.isFinite(y) || y <= -3 || y>= 5){
        return false;
    }
    const validR = [1,1.5,2,2.5,3];
    if(validR.includes(r) === false){
        return false;
    }
    return true;
}

async function handleRequest(request,response){
    const url = new URL(request.url, 'http://localhost').pathname;
    response.setHeader('Cache-Control', 'no-store');
    response.setHeader('X-Content-Type-Options', 'nosniff');

    if (url === '/api/validate' && request.method === 'POST') {
        try {
            let text = '';
            for await (const part of request) {
                text += part;
                if (text.length > 4096) throw new Error('Слишком большой запрос');
            }
            const point = JSON.parse(text);
            if (!point || !validNumber(point.x, point.y, point.r)) {
                throw new Error('Неверные координаты');
            }
            response.writeHead(200);
            response.end('OK');
        } catch {
            response.writeHead(400);
            response.end('Invalid data');
        }
        return;
    }

    const files = {
        '/': ['index.html', 'text/html'],
        '/index.html': ['index.html', 'text/html'],
        '/style.css': ['style.css', 'text/css'],
        '/script.js': ['script.js', 'text/javascript'],
    };
    if (request.method !== 'GET' || !Object.hasOwn(files, url)) {
        response.writeHead(404);
        response.end('Not found');
        return;
    }
    try {
        const file = files[url];
        const content = await readFile(new URL('./client/' + file[0], import.meta.url));
        response.writeHead(200, { 'Content-Type': file[1] + '; charset=utf-8' });
        response.end(content);
    } catch {
        response.writeHead(500);
        response.end('Server error');
    }
}
const server = createServer(handleRequest);

function ls(){
    console.log('Откройте http://localhost:' + (process.env.PORT || 8080));
}

server.listen(Number(process.env.PORT || 8080), process.env.HOST || '127.0.0.1',ls)
