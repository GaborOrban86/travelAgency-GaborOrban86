FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY target/travel-agency-1.0-SNAPSHOT.jar /opt/app/travel-agency.jar
CMD ["java", "-jar", "/opt/app/travel-agency.jar"]