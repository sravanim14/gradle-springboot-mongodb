FROM anapsix/alpine-java
LABEL maintainer="sravani.madireddy14@gmail.com"
ADD build/libs/springbootapp-0.0.1-SNAPSHOT.jar /home/springbootapp-0.0.1-SNAPSHOT.jar
RUN chmod -R 777 /home/springbootapp-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","-Dspring.data.mongodb.host=mongo-service","/home/springbootapp-0.0.1-SNAPSHOT.jar"]
