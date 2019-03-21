#!/bin/sh

docker network create kafka

docker run -d --net=kafka --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=2181 confluentinc/cp-zookeeper:5.0.0
docker run -d --net=kafka --name=kafka -p 9092:9092 -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 confluentinc/cp-kafka:5.0.0


docker run -d -p 3306:3306 --name=mysql-server -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=kafka mysql

docker ps


echo ""
echo ""
echo "If you can't connect, add this line to /etc/hosts:"
echo ""
echo "127.0.0.1     kafka"
echo ""
echo "to ensure proper routing to container network 'kafka'."
echo ""

