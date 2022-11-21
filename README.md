# AAS_Hackathon

docker build -t DOCKERHUBUSENAME/registry_service .
docker push DOCKERHUBUSENAME/registry_service
docker pull DOCKERHUBUSENAME/registry_service
docker run -it --expose 4002 - p 4002:4002 DOCKERHUBUSENAME/registry_service