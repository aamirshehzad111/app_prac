#!/usr/bin/env groovy

import hudson.model.*

def getUser(){
    def job = Jenkins.getInstance().getItemByFullName(env.JOB_BASE_NAME, Job.class)
    def build = env.BUILD_NUMBER
    def userId = build.getCause(Cause.UserIdCause).getUserId()
    return userId
}

def call() {
  def buildResult = currentBuild.currentResult
  def currentUser = getUser()
  def duration = currentBuild.durationString.replace(' and counting', '')
  def BUILD_URL="${BUILD_URL}console"
    if(!env.BUILD_NUMBER.equals("1")){
        if ( buildResult == "SUCCESS") {
          slackSend color: "good", message: "Started Job: ${env.JOB_NAME} \n Details: ${env.BRANCH_NAME} #${env.BUILD_NUMBER} \n Status: Success after ${duration} \n Started By: ${currentUser} \n (<${BUILD_URL}|Open>)"
        }
        else if (buildResult == "FAILURE") {
            slackSend color: "danger", message: "Started Job: ${env.JOB_NAME} \n Details: ${env.BRANCH_NAME} #${env.BUILD_NUMBER} \n Status: Failure after ${duration} \n Started By: ${currentUser} \n (<${BUILD_URL}|Open>)"
        }
        else if (buildResult == "STABLE") {
            slackSend color: "good", message: "Started Job: ${env.JOB_NAME} \n Details: ${env.BRANCH_NAME} #${env.BUILD_NUMBER} \n Status: Back to normal after ${duration} \n Started By: ${currentUser} \n (<${BUILD_URL}|Open>)"
        }
        else if (buildResult == "UNSTABLE") {
            slackSend color: "warning", message: "Started Job: ${env.JOB_NAME} \n Details: ${env.BRANCH_NAME} #${env.BUILD_NUMBER} \n Status: Unstable after ${duration} \n Started By: ${currentUser} \n (<${BUILD_URL}|Open>)"
        }
        else {
            slackSend color: "danger", message: "Started Job: ${env.JOB_NAME} \n Details: ${env.BRANCH_NAME} #${env.BUILD_NUMBER} \n Status: Unhandled result ${buildResult} after ${duration} ${duration} \n Started By: ${currentUser} \n (<${BUILD_URL}|Open>)"
        }
    }
}
