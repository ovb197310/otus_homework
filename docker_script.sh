#!/bin/bash
#mvn -T0.75C clean install -DskipTests=true
docker build -t homework01:3 .
docker tag homework01:3 ovb1973/pub_ovb:homework01-3
docker push ovb1973/pub_ovb:homework01-3
