#!/bin/bash
#mvn -T0.75C clean install -DskipTests=true
docker build -t homework01:2.0.1-SNAPSHOT .
docker tag homework01:2.0.1-SNAPSHOT ovb1973/pub_ovb:homework02-2
docker push ovb1973/pub_ovb:homework02-2
