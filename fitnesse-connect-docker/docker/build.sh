docker run -d --name fit-connect-content -v /fitnesse zsuffazs/fit-connect-content
docker run -d -p 9123:9123 -p 9124:9124 --name fit-connect-server --volumes-from fit-connect-content zsuffazs/fit-connect-server mvn -f fitnesse-connect-connect/pom.xml clean verify -P wiki -Dmaven.test.skip=true