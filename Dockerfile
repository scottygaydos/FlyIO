# Use a base image with Java
FROM eclipse-temurin:21

# Copy the built jar file into the image
COPY target/FlyIO.jar app.jar

# Set the entry point to run your application
ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 8080