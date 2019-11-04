# weather

> This is a get weather project, we can get three cities' weathers.
Steps: When the project is running, open the link: http://localhost:8981, it will call the tianqi api, it will return the weather.

## Setup

``` bash
# install apache-maven

# install java-1.8.0-openjdk

mvn spring-boot:run

### DEPLOY
mvn clean package -Dmaven.test.skip=true

java  -DgroupID=boya -Did=weather -jar ~/back-end/target/weather-0.0.1-SNAPSHOT.jar &

