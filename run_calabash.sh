#!/usr/bin/env sh

find . -name '*'
cp ./app/build/outputs/apk/debug/app-debug.apk ./calabahTests/app.apk
cd ./calabahTests/
calabash-android run app.apk