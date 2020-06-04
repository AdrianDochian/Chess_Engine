#!/bin/bash

ENGINE="./dist/l2-pa-project-samanii.jar"
ENGINE_PARAM="java -jar"
DEBUG="-debug"

# Connect Xboard to the Java engine
if [[ -e "$ENGINE" ]]; then
	echo "Starting Xboard with the engine..."
	xboard -fcp "$ENGINE_PARAM $ENGINE" "$DEBUG"
else
	echo "Could not locate $ENGINE.!"
	echo "Run \"make\" first!"
fi