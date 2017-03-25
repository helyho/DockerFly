FROM nimmis/alpine-java

MAINTAINER helyho <helyho@gmail.com>

RUN mkdir /dockerfly

COPY ./ /dockerfly/

RUN echo "http://mirrors.aliyun.com/alpine/v3.5/main" > /etc/apk/repositories
RUN apk --no-cache add socat
RUN echo "#!/bin/sh"

EXPOSE 28083

WORKDIR /dockerfly

RUN chmod +x /dockerfly/start.sh

ENTRYPOINT  ["/bin/sh" , "/dockerfly/start.sh"]
