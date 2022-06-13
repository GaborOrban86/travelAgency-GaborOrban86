FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY target/travelagency-1.0-SNAPSHOT.jar /opt/app/travelagency.jar
CMD ["java", "-jar", "/opt/app/travelagency.jar"]