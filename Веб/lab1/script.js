'use strict'

const form = document.getElementById('form')
const yInput = document.getElementById('y')
const message = document.getElementById('message')
const submitButton = document.getElementById('submit');
const clearButton = document.getElementById('clear');
const table = document.getElementById('results');
const canvas = document.getElementById('canvas');
const ctx = canvas.getContext('2d')
const storageKey = 'point-area-lab1.history.v1';
let points = [];


function validNumber(x,y,r){
    if(Number.isInteger(x) === false || x < -4 || x > 4){
        return false;
    }
    if(Number.isFinite(y) || y <= -3 || y>= 5){
        return false;
    }
    const validR = [1,1.5,2,2.5,3];
    if(validR.includes(r) === false){
        return false;
    }
    return true;
}

function checkY(){
    const text = yInput.value;
    const y = Number(text.replace(',','.'));
    const correctFormat = /^[+-]?\d+([.,]\d+)?$/.test(text);
    const correctNumber = Number.isFinite(y);
    const correctRange = y > -3 && y< 5;
    const valid = correctFormat && correctNumber && correctRange;
    if(valid){
        yInput.setCustomValidity('');
    } else{
        yInput.setCustomValidity('Введите число строго между -3 и 5, например -0.5.');
    }
}

function isHit(x,y,r){
    const rectangle = x>=-r && x<=0 && y>=0 && y<=r;
    const circle = x <= 0 && y<= 0 && x*x + y*y <=r*r;
    const triangle = x >= 0 && y>= 0 && x + y <= r/2;
    return rectangle || circle || triangle;
}



function px(x){
    return 220+x*40;
}
function py(y){
    return 220-y*40;
}

// const point = { x: x, y: y, r: r, hit: isHit(x, y, r), timestamp: Date.now() };

function draw(){
    const r = Number(form.elements.r.value);
    if(![1,1.5,2,2.5,3].includes(r)) return;
    ctx.clearRect(0,0,440,440)
    ctx.fillStyle = '#3399ff';
    ctx.fillRect(px(-r),py(r),r*40,r*40)

    ctx.beginPath();
    ctx.moveTo(px(0),py(0));
    ctx.arc(px(0),py(0),r*40,Math.PI,Math.PI/2,true);
    ctx.closePath();
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(px(0),py(0));
    ctx.lineTo(px(0),py(r/2));
    ctx.lineTo(px(r/2),py(0));
    ctx.closePath();
    ctx.fill();

    ctx.strokeStyle = '#000000';
    ctx.beginPath();
    ctx.moveTo(20, 220);
    ctx.lineTo(420, 220);

    ctx.moveTo(415, 215);
    ctx.lineTo(420, 220);
    ctx.lineTo(415, 225);

    ctx.moveTo(220, 420);
    ctx.lineTo(220, 20);

    ctx.moveTo(215, 25);
    ctx.lineTo(220, 20);
    ctx.lineTo(225, 25);
    ctx.stroke();

    ctx.fillStyle = '#000000';
    ctx.font = 'Arial 14px';
    ctx.fillText('X',410,235);
    ctx.fillText('Y',227,30);

    const values = [r,r/2,-r,-r/2];
    const labels = ['R','R/2','-R','-R/2'];
    for(let i = 0;i<values.length;i++){
        ctx.beginPath();
        ctx.moveTo(px(values[i]), 217);
        ctx.lineTo(px(values[i]), 223);

        ctx.moveTo(217, py(values[i]));
        ctx.lineTo(223, py(values[i]));

        ctx.stroke();
        ctx.fillText(labels[i], px(values[i]) - 10, 210);
        ctx.fillText(labels[i], 230, py(values[i]) + 2);
    }
    for(const point of points){
        if(point.r === r){
            ctx.fillStyle = point.hit ? 'green' : 'red';
            ctx.beginPath();
            ctx.arc(px(point.x),py(point.y),4,0,2*Math.PI);
            ctx.fill();
        }
    }
}

function updateDate(){
    for(const time of document.querySelectorAll('time')){
        time.textContent = new Date(time.dateTime).toLocaleString('ru-Ru');
    }
}

function showTable(){
    table.textContent = '';
    for(const point of points){
        const row = table.insertRow();
        row.insertCell().textContent = point.x;
        row.insertCell().textContent = point.y;
        row.insertCell().textContent = point.r;
        row.insertCell().textContent = point.hit ? 'Попадание' : 'Промах';
        const time = new Date(point.timestamp).toISOString();
        row.insertCell().append(time);
    }
    updateDate();
}
yInput.addEventListener('input', checkY);
form.addEventListener('change', draw);





showTable();
draw();






