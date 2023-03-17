#!/bin/bash

release_name=$1

mkdir releases/"$release_name"

tar --exclude='./target' --exclude='./.idea' --exclude='./.git' --exclude='./releases' -cvzf releases/"$release_name"/score-convert-java-"$release_name".tar.gz .
