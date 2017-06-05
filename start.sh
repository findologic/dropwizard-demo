#!/bin/bash
#
# Helper script to build and run the Dropwizard demo application. Requires JDK,
# Maven, and Docker to be installed.

echo 'Building JAR...'
mvn package
if [ $? -ne 0 ]; then
    echo 'Failed to build JAR. Aborting.'
    exit 1
fi

echo 'Building Docker image and starting up...'
sudo docker-compose up --build