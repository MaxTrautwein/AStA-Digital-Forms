#!/bin/sh
# Credit to https://github.com/keycloak/keycloak/issues/10816#issuecomment-1693821018
for i in $(ls -1 /run/secrets)
do
	export ${i}=$(cat /run/secrets/${i})
done

/opt/keycloak/bin/kc.sh start-dev