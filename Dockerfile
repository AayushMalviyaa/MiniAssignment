FROM tomcat:11.0-jdk17
COPY ./target/Assignment.war /usr/local/tomcat/webapps/
