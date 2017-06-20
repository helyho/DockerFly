#!/bin/sh
if [ -d ./src ]; then

    if [ ! -d ./classes ]; then
        mkdir classes
    fi

    if [ ! -d ./classes/org/voovan/dockerfly ]; then
        echo "Try to build source"
        find . -name "*.java" > source.list
        javac -d classes -classpath ./bin/voovan-framework.jar:./lib/JDocker.jar:./lib/vestful.jar:./lib/sqlite-jdbc-3.16.1.jar @source.list
        rm source.list
    fi

    if [ ! -f ./classes/logger.properties ]; then
        cp src/main/resources/logger.properties classes/logger.properties
    fi
fi

nohup socat tcp4-listen:2735,reuseaddr,fork unix-connect:/var/run/docker.sock >> /dev/null 2>&1 &
java -jar ./bin/voovan-framework.jar
