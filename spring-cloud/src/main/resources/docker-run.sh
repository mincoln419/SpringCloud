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