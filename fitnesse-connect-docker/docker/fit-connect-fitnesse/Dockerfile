FROM maven
MAINTAINER zsolt.zsuffa@gmail.com
ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/jre MAVEN_HOME=/usr/share/maven
EXPOSE 9123
COPY settings.xml /usr/share/maven/conf/settings.xml
COPY -a ../../target/mavenRepository/. /maven/repository/
WORKDIR /fitnesse
VOLUME ["/fitnesse"]
VOLUME ["/maven/repository"]
CMD mvn -f fitnesse-connect-connect/pom.xml clean install -P wiki -Dmaven.test.skip=true
