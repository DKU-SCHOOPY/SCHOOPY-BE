#!/bin/bash

# 실행할 JAR 이름
JAR_NAME=back-0.0.1-SNAPSHOT.jar

# JAR 파일이 있는 디렉토리 (절대경로 추천)
APP_DIR=/home/ubuntu/.ssh/SCHOOPY-BE/back/build/libs

cd $APP_DIR

# 기존 애플리케이션 프로세스 종료
PID=$(pgrep -f $JAR_NAME)
if [ -n "$PID" ]; then
  echo "Stopping existing application with PID: $PID"
  kill -9 $PID
  sleep 3
else
  echo "No existing application running."
fi

# 새로 애플리케이션 실행
echo "Starting $JAR_NAME..."
nohup java -jar $JAR_NAME &
echo "Application started."
