FROM tomcat:9.0-jdk11-openjdk
COPY target/ROOT.war /usr/local/tomcat/webapps/ROOT.war
ENTRYPOINT ["catalina.sh", "run"]
