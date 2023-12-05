FROM ubuntu:latest
ARG DEBIAN_FRONTED=noninteractive
RUN apt update; apt dist-upgrade -y 
RUN  apt install default-jdk -y
RUN mkdir /opt/tomcat
WORKDIR /opt/tomcat
ADD https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.83/bin/apache-tomcat-9.0.83.tar.gz .
RUN tar -xvzf apache-tomcat-9.0.83.tar.gz
RUN mv apache-tomcat-9.0.83/* /opt/tomcat
COPY ./context.xml /opt/tomcat/conf/context.xml
COPY ./Hookah.war /opt/tomcat/webapps
# Install MySQL JDBC driver
ADD https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.30/mysql-connector-java-8.0.30.jar /opt/tomcat/lib/
CMD  /opt/tomcat/bin/catalina.sh run


