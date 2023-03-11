#!/bin/bash

kubectl apply -f architect.yaml
kubectl apply -f agent.yaml
kubectl apply -f oracle_configuration.yaml
kubectl apply -f merovingian.yaml
kubectl apply -f twins.yaml