cd target
cp SeedOnTanda-0.0.1-SNAPSHOT.jar ../docker/
cd ..
cd docker
docker build -t seedon .
docker network create -d bridge --internal no-internet
docker network create -d bridge internet
docker compose -f docker-compose-infra.yml -f docker-compose.yml up -d
