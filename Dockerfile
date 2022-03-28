FROM adoptopenjdk/openjdk11:latest

VOLUME /tmp

ADD target/product-ms-*.jar app.jar

EXPOSE 9999

ENTRYPOINT ["java","-Dserver.port=9999","-jar","app.jar"]