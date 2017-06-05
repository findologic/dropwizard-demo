FROM openjdk:8-jre-alpine

EXPOSE 8080 8081
ENV BASE_DIR=/opt/dropwizard-demo

WORKDIR $BASE_DIR
ENTRYPOINT ["java", "-jar", "dropwizardDemo.jar", "server", "config.yml"]

COPY config.yml $BASE_DIR/config.yml
COPY target/dropwizard-demo-1.0-SNAPSHOT.jar $BASE_DIR/dropwizardDemo.jar