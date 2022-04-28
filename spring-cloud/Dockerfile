FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
COPY target/spring-cloud-1.0.jar DiscoveryServer.jar
ENTRYPOINT ["java","-jar","DiscoveryServer.jar"]