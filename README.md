# mus-org
![master](https://github.com/vlarr/mus-org/workflows/master/badge.svg) 
![develop](https://github.com/vlarr/mus-org/workflows/develop/badge.svg)

## Описание

Органайзер музыки. Используется для:
- ручного заполнения данных с вебформы (исполнитель+трек).
- парсинга данных с плейлиста deezer
 
Использует заранее подготовленную SQLite базу данных (`.\local\sqlite_db\mus.db`).

## Запуск

1

> cd compose 

2 Сервера redis и rabbitMq. Требуется около 30 сек на старт

> docker-compose --env-file=params.env -f .\docker-compose.1-infr.yml up --build

3 Веб
> docker-compose --env-file=params.env -f .\docker-compose.2-web.yml up --build

3.1 Запуск веба через jar

> java -Drmq.addr=\<raddit-mq-address> -Dredis.addr=\<Dredis-address> -Drmq.username=\<raddit-mq-username> -Drmq.password=\<raddit-mq-password> -jar raw-track-info-web-helper-\<version\>.jar

4 Служебные
> docker-compose --env-file=params.env -f .\docker-compose.2-proc.yml up --build

5 Инициализация списка плейлистов для веба. Требует запуска `infr` и `proc`.
> http://localhost:8086/init-redis

6 Заполнение БД. Требует запуска `infr` и `web`. Приземление данных автоматом при запуске `proc`. 
> http://localhost:8082/addTrackInfo