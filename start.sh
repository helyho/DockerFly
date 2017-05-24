find . -name "*.java" > source.list
javac -d classes -classpath ./bin/voovan-framework.jar:./lib/JDocker.jar:./lib/vestful.jar:./lib/sqlite-jdbc-3.16.1.jar @source.list
rm source.list
cp classes/main/resources/logger.properties logger.properties
rm -rf classes/main
nohup socat tcp4-listen:2735,reuseaddr,fork unix-connect:/var/run/docker.sock >> /dev/null 2>&1 &
java -jar ./bin/voovan-framework.jar 
