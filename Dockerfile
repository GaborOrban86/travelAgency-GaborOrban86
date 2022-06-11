FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY target/travel-app-1.0-SNAPSHOT.jar /opt/app/travel-app.jar
CMD ["java", "-jar", "/opt/app/travel-app.jar"]