FROM ubuntu:latest
ARG DEBIAN_FRONTED=noninteractive
RUN apt update; apt dist-upgrade -y && apt install -y default-jdk mysql-server
RUN  apt install default-jdk -y
RUN mkdir /opt/tomcat
WORKDIR /opt/tomcat
ADD https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.83/bin/apache-tomcat-9.0.83.tar.gz .
RUN tar -xvzf apache-tomcat-9.0.83.tar.gz
RUN mv apache-tomcat-9.0.83/* /opt/tomcat
EXPOSE 8080 3306
COPY ./Hookah.war /opt/tomcat/webapps
# Install MySQL JDBC driver
ADD https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.30/mysql-connector-java-8.0.30.jar /opt/tomcat/lib/
# Set environment variables for Spring Boot application
ENV SPRING_DATASOURCE_URL jdbc:mysql://localhost:3306/hookah_shop?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
ENV MYSQL_ROOT_PASSWORD=1
ENV MYSQL_DATABASE=hookah_shop
ENV MYSQL_USER=sammy
ENV MYSQL_PASSWORD=rifubrib337yt43bUVYU
# Run MySQL commands to create user, database, and grant permissions
RUN service mysql start \
    && mysql -u root -p${MYSQL_ROOT_PASSWORD} -e \
        "CREATE DATABASE IF NOT EXISTS ${MYSQL_DATABASE};" \
    && mysql -u root -p${MYSQL_ROOT_PASSWORD} -e \
        "CREATE USER IF NOT EXISTS '${MYSQL_USER}'@'%' IDENTIFIED WITH 'mysql_native_password' BY '${MYSQL_PASSWORD}';" \
    && mysql -u root -p${MYSQL_ROOT_PASSWORD} -e \
        "GRANT ALL PRIVILEGES ON *.* TO '${MYSQL_USER}'@'%';" \
    && mysql -u root -p${MYSQL_ROOT_PASSWORD} -e \
        "FLUSH PRIVILEGES;"
CMD service mysql start && /opt/tomcat/bin/catalina.sh run

