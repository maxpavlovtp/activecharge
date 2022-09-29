# Transform any public spot to EV charging station.
[![CI/CD Stage](https://github.com/maxpavlovdp/activecharge/actions/workflows/ci-stage.yml/badge.svg)](https://github.com/maxpavlovdp/activecharge/actions/workflows/ci-stage.yml)
[![CI/CD Prod](https://github.com/maxpavlovdp/activecharge/actions/workflows/ci-prod.yml/badge.svg)](https://github.com/maxpavlovdp/activecharge/actions/workflows/ci-prod.yml)

## FE endpoints:
1. local: http://localhost:3000
2. stage (load balancer): http://49.12.19.42
3. prod: http://220-km.com/start?station=1

## BE endpoints:
1. http://localhost:8080/swagger-ui/index.html
3. http://49.12.19.42:8080/swagger-ui/index.html
4. metrics Java: [admin/Nopassword1] http://157.90.252.84:3000/d/J8DlY3MVz/jvm-micrometer?orgId=1
5. metrics Charging: [admin/Nopassword1] http://157.90.252.84:3000/d/81TBNvN5o/charging-jobs?orgId=1
6. logs Loki: [admin/Nopassword1] http://157.90.252.84:3000/explore?orgId=1&left=%7B%22datasource%22:%22P982945308D3682D1%22,%22queries%22:%5B%7B%22refId%22:%22A%22,%22editorMode%22:%22builder%22,%22expr%22:%22%7Bjob%3D%5C%22varlogs%5C%22%7D%20%7C%3D%20%60%60%22,%22queryType%22:%22range%22%7D%5D,%22range%22:%7B%22from%22:%22now-15m%22,%22to%22:%22now%22%7D%7D
7. openvpn: [stage-admin/Nopassword1] https://157.90.252.84/

## Setup dev env:
1. install java 17 from https://www.azul.com/downloads/?version=java-17-lts&package=jdk
2. set JAVA_HOME
3. install npm
4. install docker
5. run: sh run-220-local.sh
6. check FE: http://localhost:3000
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
<img width="906" alt="image" src="https://user-images.githubusercontent.com/5563023/183234275-1f28ef40-e86e-4cef-8cc4-6de7d8e3b299.png">
<img width="724" alt="image" src="https://user-images.githubusercontent.com/5563023/183415299-e86c51c5-f378-4c0d-9b39-91a3656e73ce.png">
