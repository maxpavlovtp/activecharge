cd "$(dirname "$0")" || exit

java -jar build/libs/220-km.com-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=stage
