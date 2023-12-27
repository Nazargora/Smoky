#!/bin/bash

# Wait for the MySQL service to be ready
until nc -z -w 1 mysql 3306; do
  echo "Waiting for MySQL to be ready..."
  sleep 1
done

# Execute the Maven build
mvn -f /usr/src/app/pom.xml clean package

# Start Tomcat
catalina.sh run
