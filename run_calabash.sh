#!/usr/bin/env sh

find /home/travis/.rvm/rubies/ruby-2.4.1/ -name '*'
cp ./app/build/outputs/apk/debug/app-debug.apk ./calabahTests/app.apk
cd ./calabahTests/
calabash-android run app.apk