#
# Build stage
#
FROM gradle:6.9.1-alpine as build
WORKDIR /app
COPY . /app
RUN gradle build

#
# Package stage
#
FROM adoptopenjdk/openjdk11:alpine-jre as builder
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app/ecommerce-app.jar
EXPOSE 80
EXPOSE 443
EXPOSE 8080

ENTRYPOINT ["java","-XX:MaxRAM=256m","-jar","app/ecommerce-app.jar"]