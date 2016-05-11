#!/usr/bin/env bash

echo "deploying restapi"
mvn clean install -Dmaven.test.skip;
cd comic-rest-api;
mvn clean install -Dmaven.test.skip;
nohup java -jar target/comic-rest-api-1.3.2.RELEASE &