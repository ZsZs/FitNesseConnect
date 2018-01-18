docker build -t zsuffazs/fit-connect-testbed .
docker run -d -p 9124:9124 --name fit-connect-testbed zsuffazs/fit-connect-testbed