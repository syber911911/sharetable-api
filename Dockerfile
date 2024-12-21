FROM openjdk:17-jdk
WORKDIR /share-table
COPY build/libs/*SNAPSHOT.jar share-table-api.jar
ENTRYPOINT ["java", "-jar", "/share-table/share-table-api.jar"]