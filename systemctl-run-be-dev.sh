cd "$(dirname "$0")" || exit

java -Dspring.profiles.active=220dev -jar build/libs/220-km.com-0.0.1-SNAPSHOT.jar
