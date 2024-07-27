#!/bin/bash
# branch name
BRANCH=$1

REPOSITORY=/home/ec2-user/app
PROJECT_NAME=maven_practice

cd $REPOSITORY/$PROJECT_NAME/

# step1 - fetch the branch and download the source file
git fetch origin $BRANCH
git checkout $BRANCH
echo "> git pull origin $BRANCH"
git pull origin $BRANCH

# step2 - using maven, build jar file
mvn install -Dmaven.test.skip=true

# step3 - print the current last commit hash and message
# -1: recent one commit, %H: commit hash, %s: commit message
git log -1 --pretty=format:"%H %s"


# step4 - check whether the app is running or not
CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)
echo "> Running application PID: $CURRENT_PID"

# step5 - if app is running, shutdown gracefully
if [ -z "$CURRENT_PID" ]; then
  echo "> Running application not found"
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

# step6 - start application with jvm options
JAR_NAME=$(ls -rt $REPOSITORY/target | grep jar | tail -n 1)
echo "> JAR Name : $JAR_NAME"

nohup java -jar $REPOSITORY/target/$JAR_NAME --spring.profiles.active=prod 2>&1 &
