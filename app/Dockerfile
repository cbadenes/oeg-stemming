FROM tomcat:8-jre8

MAINTAINER Carlos Badenes (cbadenes@fi.upm.es)

RUN wget -O /usr/local/tomcat/webapps/stemming.war https://raw.github.com/cbadenes/oeg-stemming/mvn-repo/es/upm/oeg/oeg-stemming-web/1.0.0/oeg-stemming-web-1.0.0.war

CMD ["catalina.sh", "run"]
