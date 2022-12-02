FROM maven:3.8.6-openjdk-18 AS build
COPY pom_docker.xml /home/app/pom.xml
RUN mvn -f /home/app/pom.xml clean package

COPY /src /home/app/src
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17
COPY --from=build /home/app/target/my-maven-docker-project.jar /usr/local/lib/demo.jar
COPY --from=build /home/app/target/dependency-jars /usr/local/lib/dependency-jars
EXPOSE 4002
ENTRYPOINT java -jar /usr/local/lib/demo.jar -D exec.mainClass="New_ProcessModel.SubmodelService"
