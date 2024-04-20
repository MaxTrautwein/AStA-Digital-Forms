FROM quay.io/keycloak/keycloak:latest


# In case of Proxy Setup
#ENV KC_PROXY=edge

COPY ./initEnv.sh /initEnv.sh
#RUN chmod o+x /initEnv.sh

ENTRYPOINT ["/initEnv.sh"]