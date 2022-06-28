cd "$(dirname "$0")" || exit

systemctl stop run-220-nest.service
systemctl stop run-220-spring.service
systemctl stop run-220.service

pkill -f java
pkill -f react
pkill -f nest

git pull

nohup ./src-js/nest/run-nest-prod.sh &
nohup ./src-js/react/run-react-prod.sh &
./gradlew clean build -x test
java -jar build/libs/220-km.com-0.0.1-SNAPSHOT.jar &
