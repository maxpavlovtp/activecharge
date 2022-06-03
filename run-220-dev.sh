cd "$(dirname "$0")" || exit


lsof -ti tcp:8080 | xargs kill -kill

./gradlew clean build -x test
nohup java -jar build/libs/220-km.com-0.0.1-SNAPSHOT.jar &

lsof -ti tcp:5000 | xargs kill -kill
nohup ./src-js/nest/run-nest-dev.sh &

lsof -ti tcp:3000 | xargs kill -kill
nohup ./src-js/react/run-react-dev.sh &

tail -f nohup.out