FROM openjdk:11.0.2-jdk-oraclelinux7
ARG APP_HOME=/www/app
RUN mkdir $APP_HOME -p; \
    mkdir $APP_HOME/logs -p
WORKDIR $APP_HOME
COPY target/app*.jar ./app.jar
USER default
EXPOSE 8080/tcp
CMD java -jar app.jar