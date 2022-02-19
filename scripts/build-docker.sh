#!/bin/bash

TAG="0.1"
docker build --no-cache -t sbishyr/matrix-architect:${TAG} -f ../architect/Dockerfile ../architect/.
docker push sbishyr/matrix-architect:${TAG}

docker build --no-cache -t sbishyr/matrix-oracle:${TAG} -f ../oracle/Dockerfile ../oracle/.
docker push sbishyr/matrix-oracle:${TAG}

docker build --no-cache -t sbishyr/matrix-agent:${TAG} -f ../agent/Dockerfile ../agent/.
docker push sbishyr/matrix-agent:${TAG}

docker build --no-cache -t sbishyr/matrix-merovingian:${TAG} -f ../merovingian/Dockerfile ../merovingian/.
docker push sbishyr/matrix-merovingian:${TAG}

docker build --no-cache -t sbishyr/matrix-twins:${TAG} -f ../twins/Dockerfile ../twins/.
docker push sbishyr/matrix-twins:${TAG}
