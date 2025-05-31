FROM tomcat:9.0-jdk17

# Eliminar apps por defecto
RUN rm -rf /usr/local/tomcat/webapps/*

# Copiar WAR compilado al servidor Tomcat
COPY target/TransPerfumes.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
