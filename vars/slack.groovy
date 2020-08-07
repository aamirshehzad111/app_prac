#!/usr/bin/env groovy

import hudson.model.*

def getUser(){
    def job = Jenkins.getInstance().getItemByFullName(env.JOB_BASE_NAME, Job.class)
    def build = job.getBuildByNumber(env.BUILD_ID as int)
    def userId = build.getCause(Cause.UserIdCause).getUserId()
    return userId
}

def call() {
  def buildResult = currentBuild.currentResult
  def currentUser = getUser()
  def duration = currentBuild.durationString.replace(' and counting', '')
  def BUILD_URL="${BUILD_URL}/console"
    if(!env.BUILD_NUMBER.equals("1")){
        if ( buildResult == "SUCCESS") {
          slackSend color: "good", message: "${currentUser} - ${env.JOB_NAME} - #${env.BUILD_NUMBER} Success after ${duration} (<${BUILD_URL}|Open>)"
        }
        else if (buildResult == "FAILURE") {
            slackSend color: "danger", message: "Started By: ${currentUser} \n ${env.JOB_NAME} - #${env.BUILD_NUMBER} Failure after ${duration} (<${BUILD_URL}|Open>)"
        }
        else if (buildResult == "STABLE") {
            slackSend color: "good", message: "${currentUser} - ${env.JOB_NAME} - #${env.BUILD_NUMBER} Back to normal after ${duration} (<${BUILD_URL}|Open>)"
        }
        else if (buildResult == "UNSTABLE") {
            slackSend color: "warning", message: "${currentUser} - ${env.JOB_NAME} - #${env.BUILD_NUMBER} Unstable after ${duration} (<${BUILD_URL}|Open>)"
        }
        else {
            slackSend color: "danger", message: "${currentUser}  - ${env.JOB_NAME} - #${env.BUILD_NUMBER} Unhandled result ${buildResult} after ${duration} (<${BUILD_URL}|Open>)"
        }
    }
}
