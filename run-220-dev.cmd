taskkill /f /im java.exe & ^

gradlew clean build -x test
java -jar build/libs/220-km.com-0.0.1-SNAPSHOT.jar