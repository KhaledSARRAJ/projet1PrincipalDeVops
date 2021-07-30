FROM tomcat:9.0

MAINTAINER KS <khaledinat@gmail.com>

# Delete existing ROOT folder
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy to images tomcat path
COPY ROOT.war /usr/local/tomcat/webapps/
