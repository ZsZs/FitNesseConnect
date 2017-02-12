# Pull base image.
FROM maven:latest

MAINTAINER zsolt.zsuffa@gmail.com

# Define working directory.
WORKDIR /fitnesse
VOLUME ["/fitnesse"]
ADD pom.xml /fitnesse
RUN mvn package
CMD ls
