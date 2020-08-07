#!/usr/bin/env groovy

def call() {
  def buildResult = currentBuild.currentResult
  def currentUser = hudson.model.User.current()
  def duration = currentBuild.durationString.replace(' and counting', '')
  BUILD_URL="${BUILD_URL}/console"
    if(!env.BUILD_NUMBER.equals("1")){
        if ( buildResult == "SUCCESS") {
          slackSend color: "good", USER: ${currentUser} ,message: "${env.JOB_NAME} - #${env.BUILD_NUMBER} Success after ${duration} (<${BUILD_URL}|Open>)"
        }
        else if (buildResult == "FAILURE") {
            slackSend color: "danger", USER: ${currentUser} , message: "${env.JOB_NAME} - #${env.BUILD_NUMBER} Failure after ${duration} (<${BUILD_URL}|Open>)"
        }
        else if (buildResult == "STABLE") {
            slackSend color: "good", USER: ${currentUser} ,message: "${env.JOB_NAME} - #${env.BUILD_NUMBER} Back to normal after ${duration} (<${BUILD_URL}|Open>)"
        }
        else if (buildResult == "UNSTABLE") {
            slackSend color: "warning", USER: ${currentUser} ,message: "${env.JOB_NAME} - #${env.BUILD_NUMBER} Unstable after ${duration} (<${BUILD_URL}|Open>)"
        }
        else {
            slackSend color: "danger", USER: ${currentUser} ,message: "${env.JOB_NAME} - #${env.BUILD_NUMBER} Unhandled result ${buildResult} after ${duration} (<${BUILD_URL}|Open>)"
        }
    }
}
