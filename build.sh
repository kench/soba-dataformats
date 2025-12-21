#!/usr/bin/env sh
./gradlew clean
rm -rf /tmp/soba-test && mkdir -p /tmp/soba-test
./gradlew flywayMigrate -i
./gradlew build -i