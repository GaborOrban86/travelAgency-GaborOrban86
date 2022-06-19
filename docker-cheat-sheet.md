**Docker parancsok az applikáció létrehozásához és indításához:**

docker network create travel_network

docker run --name traveldb --network travel_network -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=travel -d -p 3311:3306 mysql:latest

mvn clean package

docker build -t travelagency .

docker run --name travelagency --network travel_network -p 8080:8080 -d travelagency
