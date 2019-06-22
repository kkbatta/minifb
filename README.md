PocketFB (Pocket-Facebook)
=========

PocketFB is simple spring-boot application that helps Users register, add friends and approve firends request. Its ready to build and deployed as docker container in any public cloud environemnt.

Current implemenation Details:
=============================
As this was a quick whip for interview, I chose a cost effective and low memory footprint design to implement. Plan was to use spring-boot and inmemory H2 DB. But H2 DB schema syntax was very different compared to mysql and any of the sql functions were not supported in the same way. Thus was taking longer than it should have. So I chose to use in-memory cache. It is desinged to easily add any persistence later for in memory model.


Other choices made:
==================
Used Maven for build. <br />
Maven-Docker plugin for dockerization <br />
Public docker repo for code push. https://cloud.docker.com/repository/docker/kkbatta/pocketfb <br />
Digital Ocean droplet for production deployment. Login credentials can be provided on request. <br />
SpringBootTest and Junit for unit testing. <br />

Goal was to develop, test and deploy to production using docker. 

Followinfg are fully supported in the PocketFB
==============================================
1) Register new user
2) Send Friendship request to other users
3) Approve / Deny/ Recall friendship requests.
4) Track your awaiting requests with other users.
5) IF a user denies someelses frienship request, the other person will not be aware of the action.
6) User will be able to tracks all his incoing friends request and will be always able to see any Recalls done by others.
7) Remove friends. you can fully manage your friends list and keep track of changes.
8) Admin Operations : WIP



Deploy steps:
=============
#./run.sh 
Above command Builds the code, creates a docker image, pushes it to remote rpository  <br />(https://cloud.docker.com/repository/docker/kkbatta/pocketfb) and deployed the image in localhost 8085 port.


On public Cloud:
================
The same image has been deployed in digitalocean public cloud. <br />
Its basic health page can be accessed at http://167.99.105.172:8085/health


Work In Progress:
================
Followinfg are work in progress and can be achieved with little more time
1) Swagger api support. All dependies are added but need to enable it. With sprint-boot , its a bit of learnign but definetly doable.
2) Jenkins based CI and remote docker rollout.
3) Kubernetes service setup with automatic resource management and HA. Very easy to set this up but the Digital Ocean droplet I purchase was too small for run docker and Kubenetes with multiple instances. It crashed on me a couple of times and went dead
4) Admin management API to manage the userbase and customer supported.


Future tasks:
=============
Security layer <br /> 
In memory data persistence


API Details
===========
Swagger API : http://167.99.105.172:8085/swagger-ui.html#/
