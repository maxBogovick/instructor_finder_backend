# Step 1: Use a base image with Java
# Adjust the base image as necessary to match your Java version requirement
FROM openjdk:21-slim

# Step 2: Set a working directory inside the container
WORKDIR /app

# Step 3: Copy the built jar from your host to the container
# Replace 'your-application.jar' with the path and name of your actual jar file
COPY build/libs/app.jar app.jar

# Step 4: Expose the port your application uses
EXPOSE 8080

# Step 5: Run your Spring Boot application
CMD ["java", "-jar", "app.jar"]
