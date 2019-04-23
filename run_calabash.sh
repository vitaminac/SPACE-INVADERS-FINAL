#!/usr/bin/env sh

cp ./app/build/outputs/apk/debug/app-debug.apk ./calabahTests/app.apk
# cd ./calabahTests/
cat ./output
# calabash-android run app.apk