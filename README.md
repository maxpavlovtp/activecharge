# Project to transform you garage or any public spot to electric car charging station.

### Setup dev env:
1. install java 17 from https://www.azul.com/downloads/?version=java-17-lts&package=jdk
2. install npm
3. run: sh run-220-dev.sh

### FE react endpoint: 
http://220-km.com/

### BE spring boot endpoints:
1. http://220-km.com:8080/device/start
2. http://220-km.com:8080/device/startSecs?secs=20
3. http://220-km.com:8080/device/getChargeTimeLeftSecs
4. http://220-km.com:8080/device/getChargingStatus
5. http://220-km.com:8080/device/getDeviceStatus
6. http://220-km.com:8080/device/isDeviceOn
7. http://220-km.com:8080/device/getChargeTime

### SDLC:
1. We push to master
2. We deploy FE every 20 mins BE every day from master using crontab + systemctl. 
3. If deployment broken revert or use last tag https://github.com/maxpavlovdp/activecharge/releases/tag/v1.1
4. Kanban board: https://esound.youtrack.cloud/youtrack/agiles/87-6/current

### Design:
https://app.diagrams.net/#G1xBm3YxmDuKMkPA-N649yD8FCtsIpkAai
<img width="963" alt="image" src="https://user-images.githubusercontent.com/5563023/171100461-22780c99-c5f7-4d60-9adb-db8363a91b57.png">


[![Java CI with Gradle](https://github.com/maxpavlovdp/activecharge/actions/workflows/gradle.yml/badge.svg)](https://github.com/maxpavlovdp/activecharge/actions/workflows/gradle.yml)
