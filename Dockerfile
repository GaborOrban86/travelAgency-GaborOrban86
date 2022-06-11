FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY target/vizsgaremek-GaborOrban86-1.0-SNAPSHOT.jar /opt/app/vizsgaremek-GaborOrban86.jar
CMD ["java", "-jar", "/opt/app/vizsgaremek-GaborOrban86.jar"]