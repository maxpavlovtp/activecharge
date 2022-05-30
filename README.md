# Project to transform you garage or any public spot to electric car charging station.

### Setup dev env:
1. install java 17
2. run: ./gradlew clean build
3. Kanban board: https://esound.youtrack.cloud/youtrack/agiles/87-6/current

### FE react endpoint: 
http://220-km.com/

### BE spring boot endpoints:
1. http://220-km.com:8080/on/start
2. http://220-km.com:8080/on/getChargingStatus
3. http://220-km.com:8080/on/getDeviceStatus

### SDLC:
1. We push to master
2. We deploy FE every 20 mins BE every day from master using crontab + systemctl. 
3. If deployment broken revert or use last tag https://github.com/maxpavlovdp/activecharge/releases/tag/v1.1

### Design:
https://app.diagrams.net/#G1xBm3YxmDuKMkPA-N649yD8FCtsIpkAai
<img width="1164" alt="image" src="https://user-images.githubusercontent.com/5563023/170946617-ced3502b-1c24-43bb-b3b7-6cb974d56542.png">


[![Java CI with Gradle](https://github.com/maxpavlovdp/activecharge/actions/workflows/gradle.yml/badge.svg)](https://github.com/maxpavlovdp/activecharge/actions/workflows/gradle.yml)
