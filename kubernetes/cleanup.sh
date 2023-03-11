#!/bin/bash

kubectl delete -f architect.yaml
kubectl delete -f agent.yaml
kubectl delete -f oracle_configuration.yaml
kubectl delete -f merovingian.yaml
kubectl delete -f twins.yaml