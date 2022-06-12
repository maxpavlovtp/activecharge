# Project to transform you garage or any public spot to electric car charging station.
[![Java CI with Gradle](https://github.com/maxpavlovdp/activecharge/actions/workflows/gradle.yml/badge.svg)](https://github.com/maxpavlovdp/activecharge/actions/workflows/gradle.yml)

### Setup dev env:

1. install java 17 from https://www.azul.com/downloads/?version=java-17-lts&package=jdk
2. install npm
3. run: sh run-220-dev.sh

### FE react endpoint:

http://220-km.com/

### BE spring boot endpoints local:

1. http://localhost:8080/device/start
2. http://localhost:8080/device/startSecs?secs=7
3. http://localhost:8080/device/getDeviceStatus
4. http://localhost:8080/device/getDeviceStatusNewAPI
5. http://localhost:8080/device/getWSDeviceStatus
6. http://localhost:8080/device/getChargingStatus
7. http://localhost:8080/device/getChargingDurationLeftSecs
8. http://localhost:8080/device/isDeviceOn

#### overload check

1. http://localhost:8080/device/getPower
2. http://localhost:8080/device/isPowerLimitOvelrloaded
3. http://localhost:8080/device/getPowerLimit
4. http://localhost:8080/device/isOverloadCheckCompleted

### BE spring boot endpoints:

1. http://220-km.com:8080/device/start
2. http://220-km.com:8080/device/startSecs?secs=7
3. http://220-km.com:8080/device/getDeviceStatus
4. http://220-km.com:8080/device/getDeviceStatusNewAPI
5. http://220-km.com:8080/device/getChargingStatus
6. http://220-km.com:8080/device/getChargingDurationLeftSecs
7. http://220-km.com:8080/device/isDeviceOn

#### overload check

1. http://220-km.com:8080/device/getPower
2. http://220-km.com:8080/device/isPowerLimitOvelrloaded
3. http://220-km.com:8080/device/getPowerLimit
4. http://220-km.com:8080/device/isOverloadCheckCompleted

### SDLC:

1. We push to master
2. We deploy FE every 20 mins BE every day from master using crontab + systemctl.
3. If deployment broken revert or use last
   tag https://github.com/maxpavlovdp/activecharge/releases/tag/v1.1
4. Kanban board: https://esound.youtrack.cloud/youtrack/agiles/87-6/current

### Design:

https://app.diagrams.net/#G1xBm3YxmDuKMkPA-N649yD8FCtsIpkAai
<img width="963" alt="image" src="https://user-images.githubusercontent.com/5563023/171100461-22780c99-c5f7-4d60-9adb-db8363a91b57.png">
<img width="823" alt="image" src="https://user-images.githubusercontent.com/5563023/171879571-2491e33c-9e92-4ac8-93cc-ebbf428136e7.png">
