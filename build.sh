#!/usr/bin/env bash

APP_NAME="SleepingTA"
APP_TYPE="app-image"

JFX_PATH="/usr/lib/jvm/java-23-openjfx/jmods/"
JFX_MODULES="javafx.controls,javafx.fxml"

CLASS="org.SleepingTA.App"
JAR_NAME="app.jar"

IN="./app/build/libs"
OUT="./image"

if [ ! -d "$OUT" ]; then
    mkdir $OUT
fi

if [ -d "$OUT/$APP_NAME" ]; then
    rm -rf "$OUT/$APP_NAME"
fi

jpackage \
    --name $APP_NAME \
    --input $IN \
    --dest $OUT\
    --main-jar $JAR_NAME \
    --main-class $CLASS \
    --type $APP_TYPE \
    --module-path $JFX_PATH \
    --add-modules $JFX_MODULES