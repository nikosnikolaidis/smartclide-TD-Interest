FROM adoptopenjdk:16-jre-hotspot
VOLUME /tmp
ADD jars/interest.jar interest.jar
RUN apt-get update && apt-get install -y git && apt-get install -y unzip
RUN curl -L -o /sonar-scanner-cli-4.6.2.2472-linux.zip https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-4.6.2.2472-linux.zip && \
    unzip -o -q /sonar-scanner-cli-4.6.2.2472-linux.zip -d . && \
    rm -rf /sonar-scanner-cli-4.6.2.2472-linux.zip
RUN chmod a+x /sonar-scanner-4.6.2.2472-linux/bin/sonar-scanner
RUN chmod 755 /sonar-scanner-4.6.2.2472-linux/jre/bin/java
ADD target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar", "--org.eclipse.opensmartclide.sonarqube.url=${GR_NIKOS_SMARTCLIDE_SONARQUBE_URL}"]