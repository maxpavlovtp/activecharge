# Project to transform your garage or any public spot to electric car charging station.
[![CI](https://github.com/maxpavlovdp/activecharge/actions/workflows/ci.yml/badge.svg)](https://github.com/maxpavlovdp/activecharge/actions/workflows/ci.yml)
[![CI prod](https://github.com/maxpavlovdp/activecharge/actions/workflows/ci-prod.yml/badge.svg)](https://github.com/maxpavlovdp/activecharge/actions/workflows/ci-prod.yml)

## FE react endpoint:
1. local: http://localhost:3000?station=3
2. dev: http://178.18.251.90?station=4
3. prod: http://220-km.com?station=5

## BE Swagger:
http://178.18.251.90:8080/swagger-ui/index.html

## Setup dev env:
1. install java 17 from https://www.azul.com/downloads/?version=java-17-lts&package=jdk
2. set JAVA_HOME
3. install npm
4. install docker
5. run: sh run-220-local.sh
6. check FE: http://localhost:3000/?station=3
7. check BE swagger: http://localhost:8080/swagger-ui/index.html
8. download DB tool: https://dbeaver.io/download/
9. configure connection:
    url: jdbc:postgresql://localhost:5432/km220
    username: km220
    password: Nopassword1
9. buy device: https://smartunit.com.ua/p1468345789-wifi-rele-tomzn.html?source=merchant_center&gclid=CjwKCAjwlqOXBhBqEiwA-hhitJnVhc58A84dNE5pXfiLi6m56wuSncTd-V8D6tC-fHsVxSQa6EZCBBoCJB8QAvD_BwE
10. go deeper :)

## SDLC:
1. We deploy every PR to dev 178.18.251.90 using git actions + ssh + systemctl.
2. Kanban board: https://esound.youtrack.cloud/youtrack/agiles/87-6/current

## Design:
https://app.diagrams.net/#G1xBm3YxmDuKMkPA-N649yD8FCtsIpkAai
<img width="898" alt="image" src="https://user-images.githubusercontent.com/5563023/183231489-647590bd-ba61-407a-ae71-6b7c243f0fba.png">
