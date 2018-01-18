docker build -t zsuffazs/fit-connect-fitnesse .
docker run -d -p 9123:9123 --name fit-connect-fitnesse zsuffazs/fit-connect-fitnesse