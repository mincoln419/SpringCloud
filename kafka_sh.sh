

$KAFKA_HOME/bin/windows/zookeeper-server-start.bat $KAFKA_HOME/config/zookeeper.properties

$KAFKA_HOME/bin/windows/kafka-server-start.bat $KAFKA_HOME/config/server.properties


vim $KAFKA_CONNECT_HOME/etc/kafka/connect-distributed.properties

$KAFKA_CONNECT_HOME/bin/windows/connect-distributed.bat $KAFKA_CONNECT_HOME/etc/kafka/connect-distributed.properties

#Data-source connect 추가

{
    "name":"my-source-connect",
    "config": {
        "connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector",
        "connection.url":"jdbc:mysql://localhost:3306/mydb",
        "connection.user":"root",
        "connection.password":"pass",
        "mode":"incrementing",
        "incrementing.column.name": "id",
        "table.whitelist":"users",
        "topic.prefix":"my_topic_",
        "tasks.max": "1"
    }
}


#Data-sink connect 추가

{
    "name":"my-sink-connect",
    "config": {
        "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",
        "connection.url":"jdbc:mysql://localhost:3306/mydb",
        "connection.user":"root",
        "connection.password":"pass",
        "auto.create":"true",
        "auto.evolve":"true",
        "delete.enabled":"false",
        "tasks.max": "1",
        "topics":"my_topic_users"
    }
}



$KAFKA_HOME/bin/windows/kafka-topics.bat --bootstrap-server localhost:9092 --list

$KAFKA_HOME/bin/windows/kafka-topics.bat --describe --topic my_topic_users --bootstrap-server localhost:9092

$KAFKA_HOME/bin/windows/kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic my_topic_users --from-beginning


#producer 의 경우 git bash에서 명령이 안먹힌다 -> powershell에서 수행할 것
$KAFKA_HOME/bin/windows/kafka-console-producer.bat --broker-list localhost:9092 --topic my_topic_users
{"schema":{"type":"struct","fields":[{"type":"int32","optional":false,"field":"id"},{"type":"string","optional":true,"field":"user_id"},{"type":"string","optional":true,"field":"pw"},{"type":"string","optional":true,"field":"name"},{"type":"int64","optional":true,"name":"org.apache.kafka.connect.data.Timestamp","version":1,"field":"created_at"}],"optional":false,"name":"users"},"payload":{"id":7,"user_id":"zdzccefd3","pw":"za1231","name":"zcvchalre","created_at":1650355059000}}













