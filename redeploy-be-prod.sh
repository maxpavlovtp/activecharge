cd "$(dirname "$0")" || exit

git pull
pkill -f java
./gradlew clean build -x test
nohup java -jar build/libs/220-km.com-0.0.1-SNAPSHOT.jar &