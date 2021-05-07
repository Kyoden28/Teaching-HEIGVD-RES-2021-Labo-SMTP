FROM openjdk:11

LABEL maintainer="christian.gomes@heig-vd.ch"

COPY MockMock.jar /app/MockMock.jar

WORKDIR /app

EXPOSE 25
EXPOSE 8282

CMD ["java", "-jar", "MockMock.jar"]


