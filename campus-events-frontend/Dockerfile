FROM node:20-bookworm AS builder
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
ENV NEXT_DISABLE_TURBOPACK=1
RUN npm run build

FROM node:20-bookworm
WORKDIR /app

COPY --from=builder /app ./

EXPOSE 3000
CMD ["npm", "start"]
