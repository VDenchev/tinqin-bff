FROM amazoncorretto:21-alpine3.18-jdk

COPY rest/target/rest-0.0.1-SNAPSHOT.jar bff-app.jar

ENTRYPOINT ["java","-jar","/bff-app.jar"]
