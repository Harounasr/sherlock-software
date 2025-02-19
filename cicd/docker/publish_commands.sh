#!/usr/bin/env bash
# Author: Leon Foeckersperger

set -e

echo "This file should not be executed."

DOCKER_HUB_USERNAME="vollmannv"

docker build -t $DOCKER_HUB_USERNAME/sep2023:def-1.0 cicd/docker/default
docker push $DOCKER_HUB_USERNAME/sep2023:def-1.0

docker build -t $DOCKER_HUB_USERNAME/sep2023:st cicd/docker/system-test
docker push $DOCKER_HUB_USERNAME/sep2023:st
