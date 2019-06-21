#!/bin/bash

mvn clean install dockerfile:build
docker build -t kkbatta/kksampleapi:latest
docker push kkbatta/pocketfb:latest
docker run -d -p 8085:8080 kkbatta/pocketfb:latest

