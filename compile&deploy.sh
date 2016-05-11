#!/usr/bin/env bash

echo "deploying service"
mvn clean install -Dmaven.test.skip;

cd target;
tar -xvzf comic-deploy.tar.gz
cd comic-deploy;
./bin/start_low_mem.sh;

cd ../../;
cd comic-rest-api
mvn clean install -Dmaven.test.skip;