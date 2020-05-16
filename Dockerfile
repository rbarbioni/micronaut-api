FROM openjdk:11-jdk-slim
WORKDIR /opt/app/
VOLUME /tmp
ADD build/libs/*-all.jar app.jar
RUN sh -c 'touch app.jar'
EXPOSE 8080
EXPOSE 5005
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar