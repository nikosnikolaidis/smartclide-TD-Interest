FROM adoptopenjdk:11-jre-hotspot
VOLUME /tmp
ADD target/interest-api-0.0.1-SNAPSHOT.jar app.jar
ADD jars/Interest_Calculator.jar interest.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]