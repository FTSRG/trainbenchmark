#!/bin/bash

cd "$( cd "$( dirname "$0" )" && pwd )/../"

./gradlew shadowJar generate benchmark plot page
