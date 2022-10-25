# Transform any public spot to EV charging station.
[![CI/CD Stage](https://github.com/maxpavlovdp/activecharge/actions/workflows/ci-stage.yml/badge.svg)](https://github.com/maxpavlovdp/activecharge/actions/workflows/ci-stage.yml)
[![CI/CD Prod](https://github.com/maxpavlovdp/activecharge/actions/workflows/ci-prod.yml/badge.svg)](https://github.com/maxpavlovdp/activecharge/actions/workflows/ci-prod.yml) 


## FE endpoints:
1. local: http://localhost:3000
2. stage: http://49.12.19.42
3. prod: http://220-km.com

## BE endpoints:
1. http://localhost:8080/swagger-ui/index.html
2. http://49.12.19.42:8080/swagger-ui/index.html
3. metrics: [admin/Nopassword1] http://157.90.252.84:3000/d/81TBNvN5o/charging-jobs?orgId=1
4. openvpn: [stage-admin/Nopassword1] https://157.90.252.84/

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

## SDLC:
1. We deploy every PR to dev 178.18.251.90 using git actions + ssh + systemctl.
2. Kanban board: https://esound.youtrack.cloud/youtrack/agiles/87-6/current

## Design:
https://app.diagrams.net/#G1xBm3YxmDuKMkPA-N649yD8FCtsIpkAai
<img width="906" alt="image" src="https://user-images.githubusercontent.com/5563023/183234275-1f28ef40-e86e-4cef-8cc4-6de7d8e3b299.png">
<img width="724" alt="image" src="https://user-images.githubusercontent.com/5563023/183415299-e86c51c5-f378-4c0d-9b39-91a3656e73ce.png">
