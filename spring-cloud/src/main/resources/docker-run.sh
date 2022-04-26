docker run -d -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=true --name mysql mysql:5.7

docker exec --it mysql bash

docker run -d -p 13306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=true --name mariadb mariadb


docker  build  -t  edowon0623/users-service:1.0  .  

docker push edowon0623/user-service:1.0

docker pull edowon0623/user-service:1.0


mvn clean complie package -DskipTests=true

