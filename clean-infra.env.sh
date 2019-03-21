#!/bin/sh
docker stop kafka
echo "Stopped kafka container"
docker stop zookeeper
echo "Stopped zookeeper container"
docker stop mysql-server
echo "Stopped mysql-server container"

docker rm kafka
echo "Removed kafka container"
docker rm zookeeper
echo "Removed zookeeper container"
docker rm mysql-server
echo "Removed mysql-server container"

docker network rm kafka
echo "Removed kafka network"
