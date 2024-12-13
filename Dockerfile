FROM null

ENV APP_NAME=test-app

COPY docker/supervisord/conf.d/ /zserver/supervisord/conf.d
COPY . ./
COPY server/conf/ /zserver/java-projects/conf/

RUN ln -sf /data/log /zserver/java-projects/log
RUN chown -R ${APP_USER}:${APP_GRP} ${APP_HOME}
