#!/bin/bash

helm repo add bitnami https://charts.bitnami.com/bitnami

helm install postgresql \
  --set postgresqlPassword=secretpassword,postgresqlDatabase=usersmanagement \
    bitnami/postgresql