FROM docker.io/openjdk:11-jdk
LABEL PROJECT_NAME=order \
      PROJECT=order

COPY target/order-0.0.1-SNAPSHOT.jar  order.jar
EXPOSE 8080 8080
ENTRYPOINT ["java","-jar","/order.jar"]
