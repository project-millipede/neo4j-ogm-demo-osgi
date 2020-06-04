FROM openjdk:11-jre-slim

# Karaf setup
ENV KARAF_INSTALL_PATH /opt
ENV KARAF_HOME $KARAF_INSTALL_PATH/karaf
ENV PATH $PATH:$KARAF_HOME/bin

COPY maven/karaf/ $KARAF_HOME

# Healthcheck (at service level) setup
COPY wait-for-it.sh ./
RUN chmod +x wait-for-it.sh

CMD ["karaf", "run"]