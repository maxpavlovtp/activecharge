cd "$(dirname "$0")" || exit

pkill -f react

pkill -f nest
pkill -f java

nohup java -jar build/libs/220-km.com-0.0.1-SNAPSHOT.jar &
nohup ./src-js/nest/run-nest-dev.sh &

nohup ./src-js/react/run-react-dev.sh &