cd "$(dirname "$0")" || exit

pkill -f java
pkill -f react

lsof -ti tcp:8080 | xargs kill -kill
lsof -ti tcp:3000 | xargs kill -kill

#FE
nohup ./src-js/react/run-react-local.sh &

#DB
#docker-compose down && rm -rf ./db-data
nohup docker-compose up &

#BE
./gradlew clean build -x test
#sudo mkdir /var/log/km220/ && sudo chmod 777 /var/log/km220/
java -jar -Dspring.profiles.active=vlad build/libs/220-km.com-0.0.1-SNAPSHOT.jar

#tail -f nohup.out
