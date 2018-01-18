# Pull base image.
FROM zsuffazs/fit-connect-java8

# Define working directory.
WORKDIR /data

# Define default command.
#CMD ["bash"]

MAINTAINER zsolt.zsuffa@gmail.com
EXPOSE 9124
ADD ./fit-connect-testbed.jar /data/fit-connect-testbed.jar
CMD java -jar fit-connect-testbed.jar -Dspring.profiles.active=local
