

$KAFKA_HOME/bin/windows/zookeeper-server-start.bat $KAFKA_HOME/config/zookeeper.properties

$KAFKA_HOME/bin/windows/kafka-server-start.bat $KAFKA_HOME/config/server.properties

$KAFKA_CONNECT_HOME/bin/windows/connect-distributed.bat $KAFKA_CONNECT_HOME/etc/kafka/connect-distributed.properties



$KAFKA_HOME/bin/windows/kafka-topics.bat --bootstrap-server localhost:9092 --list

$KAFKA_HOME/bin/windows/kafka-topics.bat --bootstrap-server localhost:9092 --topic my_topic_users3
