FROM tomcat:11.0-jdk17
COPY ./target/ROOT.war /usr/local/tomcat/webapps/
