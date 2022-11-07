FROM openjdk:17
COPY aas_package/target/dependency-jars /run/dependency-jars
ADD aas_package/target/my-maven-docker-project.jar /run/application.jar
# ENTRYPOINT ["java", "-jar","run/application.jar"]
ENTRYPOINT java -jar run/application.jar -D exec.mainClass="New_ProcessModel.Simple_AAS"
EXPOSE 8080