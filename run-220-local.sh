cd "$(dirname "$0")" || exit

pkill -f java
pkill -f react
pkill -f nest

lsof -ti tcp:8080 | xargs kill -kill
lsof -ti tcp:5000 | xargs kill -kill
lsof -ti tcp:3000 | xargs kill -kill

nohup ./src-js/nest/run-nest-dev.sh &
nohup ./src-js/react/run-react-dev.sh &

docker-compose down && rm -r ./db-data
nohup docker compose up &
sleep 50s
./gradlew clean build -x test
java -Dspring.profiles.active=local -jar build/libs/220-km.com-0.0.1-SNAPSHOT.jar

#tail -f nohup.out 