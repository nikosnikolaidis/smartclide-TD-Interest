FROM adoptopenjdk:16-jre-hotspot
VOLUME /tmp
ADD target/interest-api-0.0.1-SNAPSHOT.jar app.jar
ADD jars/interest.jar interest.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]