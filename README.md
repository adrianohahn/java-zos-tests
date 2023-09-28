# java-zos-tests
Collection of projects to test Java on z/OS and interactions with native code

* [Dataset Info](./dataset-info/README.md)

## Requirements

### IBM JZOS 8 

#### Maven/gradle

com.ibm:ibmjzos:8

#### Installation
    
`mvn install:install-file -Dfile=<path-to-file> -DgroupId=com.ibm -DartifactId=ibmjzos -Dversion=8 -Dpackaging=jar`
