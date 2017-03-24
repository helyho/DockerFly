FROM openjdk:8-jre

COPY ./ /data/dockerfly/

RUN chmod -R 777 /data/dockerfly/ \
    && sed -i 's/http:\/\/archive\.ubuntu\.com\/ubuntu\//http:\/\/mirrors\.163\.com\/ubuntu\//g' /etc/apt/sources.list \
    && apt-get update && apt-get install -y socat


EXPOSE 28083

WORKDIR /data/dockerfly

ENTRYPOINT  ["/bin/bash" , "/data/dockerfly/start.sh"]
