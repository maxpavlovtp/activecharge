# run this sh, wait 60 secs and go to http://localhost:3000/?station=1
cd "$(dirname "$0")" || exit

pkill -f java
pkill -f react
pkill -f nest

lsof -ti tcp:8080 | xargs kill -kill
lsof -ti tcp:5000 | xargs kill -kill
lsof -ti tcp:3000 | xargs kill -kill

#FE
nohup ./src-js/nest/run-nest-dev.sh &
nohup ./src-js/react/run-react-local.sh &

#BE
#docker-compose down && rm -rf ./db-data
nohup docker-compose up &
./gradlew clean build -x test
echo "sleep for 10 secs..."
sleep 10
java -jar -Dspring.profiles.active=dima build/libs/220-km.com-0.0.1-SNAPSHOT.jar

#tail -f nohup.out 