docker run -d -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=true --name mysql mysql:5.7

docker exec --it mysql bash

docker run -d -p 13306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=true --name mariadb mariadb


docker  build  -t  edowon0623/users-service:1.0  .  

docker push edowon0623/user-service:1.0

docker pull edowon0623/user-service:1.0


mvn clean complie package -DskipTests=true


docker networkd create --driver bridge

docker system prune

docker network ls

docker network create --gateway 172.18.0.1 --subnet 172.18.0.0/16 ecommerce-network

docker network inspect ecommerce-network


docker run -d --name rabbitmq --network ecommerce-network \
 -p 15672:15672 -p 5672:5672 -p 15671:15671 -p 5671:5671 -p 4369:4369 \
 -e RABBITMQ_DEFAULT_USER=guest \
 -e RABBITMQ_DEFAULT_PASS=guest rabbitmq:management
 
docker run -d -p 8888:8888 --network ecommerce-network \
 -e "spring.rabbitmq.host=rabbitmq" \
 -e "spring.profiles.active=default" \
  --name config-service mincoln419/spring-cloud-config:1.0

docker build --tag mincoln419/spring-cloud:1.0

docker push mincoln419/spring-cloud:1.0


docker run -d -p 8761:8761 --network ecommerce-network \
 -e "spring.cloud.config.uri=http://config-service:8888" \
 --name discovery-service mincoln419/spring-cloud:1.0
 
 
 
 docker run -d -p 8000:8000 --network ecommerce-network \
 -e "spring.cloud.config.uri=http://config-service:8888" \
 -e "spring.rabbitmq.host=rabbitmq" \
 -e "eureka.client.serviceUrl.defaultZone=http://discovery-service:8761/eureka/" \
 --name apigateway-service \
 mincoln419/api-gate-way:1.0
 
FROM mariadb
ENV MYSQL_ROOT_PASSWORD pass
ENV MYSQL_DATABASE mydb
COPY ./mysql_data/mysql /var/lib/mysql
EXPOSE 3306
ENTRYPOINT ["mysqld", "--user=root"]


docker-compose -f docker-compose-single-broker.yml up -d


#Prometheus
docker run -d -p 9090:9090 \
 --network ecommerce-network \
 --name prometheus \
 -v /C/Users/minco/prometheus/prometheus-2.35.0.windows-amd64/prometheus.yml:/etc/prometheus/prometheus.yml \
 prom/prometheus 

#Grafana
docker run -d -p 3000:3000 \
 --network ecommerce-network \
 --name grafana \
 grafana/grafana 
