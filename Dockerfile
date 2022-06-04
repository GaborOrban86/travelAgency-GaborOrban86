FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY target/vizsgaremek-GaborOrban86-1.0-SNAPSHOT.jar /opt/app/travel.jar
CMD ["java", "-jar", "/opt/app/travel.jar"]