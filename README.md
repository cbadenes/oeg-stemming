#OEG Stemming Micro-Service
[![Build Status](https://travis-ci.org/cbadenes/oeg-stemming.svg)](https://travis-ci.org/cbadenes/oeg-stemming) 

## Overview
Proof of concept creating micro-services in a structured way. It is a web service that allows reducing words to their word stem.  
 

## Project Structure

The project is defined as a *[multi-module Maven project](http://books.sonatype.com/mvnex-book/reference/multimodule.html)* including the following modules:  
- **/lib**: algorithm implementation.  
    *It allows* ***to include*** *the algorithm as a library dependency in other projects*.
- **/web**: web-service wrapper.  
    *It allows* ***to use*** *the algorithm as internal service (WAR archive)*.
- **/app**: docker template.  
    *It allows* ***to publish*** *the algorithm as external service*.


## Get started!

### Pre-requisites
- [Java](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Docker](https://docs.docker.com/installation/)
- [Maven 3.x](https://maven.apache.org/download.cgi)
- Include the host: `microservices.oeg` in your local configuration or DNS server:  
    a). **Linux Users**:  
        edit `/etc/hosts` and include:  
        ```
        127.0.0.1 microservices.oeg
        ```  
    b). **Mac OSx Users**:  
        edit `/private/etc/hosts/` and include the ip obtained by `boot2docker ip` or `docker-machine ip`:  
        ```  
        [ip]  microservices.oeg  
        ```   
    c). **Windows Users**: 
        edit `C:\Windows\System32\drivers\etc\hosts` and include the ip obtained by `boot2docker ip` or `docker-machine ip`:  
        ```  
        [ip]  microservices.oeg  
        ```  
    

### To build from source
1. Compile the project  
    ```
    mvn clean package
    ```  
2. Build the docker image  
    ```
    cd app/  
    ./docker.build
    ```  
3. Run it  
    ```
    ./docker.vrun
    ```  

### To run from DockerHub
1. Download the image  
    ```
    docker pull cbadenes/stemming
    ```  
2. Run it  
    ```
    docker run -it --rm -p 8080:8080 cbadenes/stemming
    ```  
    
## ... and now Test it!
Check [http://microservices.oeg:8080/stemming](http://microservices.oeg:8080/stemming)