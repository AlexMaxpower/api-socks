FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar api-socks.jar
ENTRYPOINT ["java","-jar","/api-socks.jar","-Dfile.encoding=UTF-8"]