#!/bin/bash

debug=$1

if [[ $debug -eq "-d" ]]; then
	sudo ./mvnw --debug spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8080"
else
	sudo ./mvnw spring-boot:run
fi
