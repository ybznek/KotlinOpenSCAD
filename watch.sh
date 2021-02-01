#!/bin/bash

# waits for file modifications and then rebuild/run project
while true;
do
	./watchForChange.sh
	clear
	./gradlew --watch-fs compileKotlin run
done
