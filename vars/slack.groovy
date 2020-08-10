#!/usr/bin/env groovy

import hudson.model.*

def getUser(int build_number){
    def job = Jenkins.getInstance().getItemByFullName(env.JOB_BASE_NAME, Job.class)
    def build =  job.getBuildByNumber(System.getenv("BUILD_NUMBER") as int)
    def userId = build.getCause(Cause.UserIdCause).getUserId()
    return userId
}

def call(String build_number) {
  def buildResult = currentBuild.currentResult
  def currentUser = getUser(build_number.toInteger())
  def duration = currentBuild.durationString.replace(' and counting', '')
  def BUILD_URL="${BUILD_URL}console"
    if(!env.BUILD_NUMBER.equals("1")){
        if ( buildResult == "SUCCESS") {
          slackSend color: "good", message: "Started Job: ${env.JOB_NAME} \n Details: ${env.BRANCH_NAME} #${build_number} \n Status: Success after ${duration} \n Started By: ${currentUser} \n (<${BUILD_URL}|Open>)"
        }
        else if (buildResult == "FAILURE") {
            slackSend color: "danger", message: "Started Job: ${env.JOB_NAME} \n Details: ${env.BRANCH_NAME} #${build_number} \n Status: Failure after ${duration} \n Started By: ${currentUser} \n (<${BUILD_URL}|Open>)"
        }
        else if (buildResult == "STABLE") {
            slackSend color: "good", message: "Started Job: ${env.JOB_NAME} \n Details: ${env.BRANCH_NAME} #${build_number} \n Status: Back to normal after ${duration} \n Started By: ${currentUser} \n (<${BUILD_URL}|Open>)"
        }
        else if (buildResult == "UNSTABLE") {
            slackSend color: "warning", message: "Started Job: ${env.JOB_NAME} \n Details: ${env.BRANCH_NAME} #${build_number} \n Status: Unstable after ${duration} \n Started By: ${currentUser} \n (<${BUILD_URL}|Open>)"
        }
        else {
            slackSend color: "danger", message: "Started Job: ${env.JOB_NAME} \n Details: ${env.BRANCH_NAME} #${build_number} \n Status: Unhandled result ${buildResult} after ${duration} ${duration} \n Started By: ${currentUser} \n (<${BUILD_URL}|Open>)"
        }
    }
}
