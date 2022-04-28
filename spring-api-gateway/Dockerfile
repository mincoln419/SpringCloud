FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
COPY target/spring-api-gateway-1.0.jar ApiGatewayServer.jar
ENTRYPOINT ["java","-jar","ApiGatewayServer.jar"]