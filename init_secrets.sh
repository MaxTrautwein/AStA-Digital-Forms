#!/bin/bash
# Generates initial secrets for docker

mkdir ./secrets
openssl rand -base64 20 > ./secrets/mongo_user
openssl rand -base64 40 > ./secrets/mongo_pw
openssl rand -base64 40 > ./secrets/KC_DB_PASSWORD
openssl rand -base64 40 > ./secrets/KEYCLOAK_ADMIN_PASSWORD
openssl rand -base64 20 > ./secrets/KEYCLOAK_ADMIN

#For testing
echo "localhost" > ./secrets/Backend_URL
echo "localhost" > ./secrets/KC_HOSTNAME