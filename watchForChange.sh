#!/bin/bash

function lastModified() {
	find . -name "*.kt" -exec stat -c %y {} \; | sort -r | head -n 1
}

prevModified=$(lastModified)

while [ "$(lastModified)" == "$prevModified" ];
do
	sleep 1s
done
