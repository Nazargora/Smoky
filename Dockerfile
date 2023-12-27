FROM maven:3.5-jdk-8-alpine as builder

WORKDIR /usr/src/app


COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM tomcat:latest


COPY --from=builder /usr/src/app/target/Hookah.war /usr/local/tomcat/webapps/Hookah.war



