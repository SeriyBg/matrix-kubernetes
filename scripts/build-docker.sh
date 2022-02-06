#!/bin/bash

docker build -t sbishyr/matrix-architect:0.1 -f ../architect/Dockerfile ../architect/.
docker push sbishyr/matrix-architect:0.1

docker build -t sbishyr/matrix-oracle:0.1 -f ../oracle/Dockerfile ../oracle/.
docker push sbishyr/matrix-oracle:0.1

docker build -t sbishyr/matrix-agent:0.1 -f ../agent/Dockerfile ../agent/.
docker push sbishyr/matrix-agent:0.1

docker build -t sbishyr/matrix-merovingian:0.1 -f ../merovingian/Dockerfile ../merovingian/.
docker push sbishyr/matrix-merovingian:0.1

docker build -t sbishyr/matrix-twins:0.1 -f ../twins/Dockerfile ../twins/.
docker push sbishyr/matrix-twins:0.1
