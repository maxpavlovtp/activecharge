cd "$(dirname "$0")" || exit

pkill -f java
nohup java -jar build/libs/220-km.com-0.0.1-SNAPSHOT.jar &