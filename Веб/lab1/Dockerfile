FROM node:24-alpine

WORKDIR /app

COPY package.json server.js ./
COPY client ./client

ENV HOST=0.0.0.0
ENV PORT=8080

EXPOSE 8080
CMD ["node", "server.js"]