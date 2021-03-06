#!/usr/bin/env groovy

import hudson.model.*
    
def getUser(String User){
    def job = Jenkins.instance.getItemByFullName(env.JOB_NAME, Job.class)
    println job
    def build =  job.getBuildByNumber(env.BUILD_NUMBER as int)
    println build
    try{
        def userId = build.getCause(Cause.UserIdCause).getUserId()
        println "User id to notify to: " + userId
        return userId
    }catch(Exception ex) {
        return User
    }

}

def call(String User) {
  println "Getting user information"
  def buildResult = currentBuild.currentResult
  def currentUser = getUser(User)
  println "User id found"
  def duration = currentBuild.durationString.replace(' and counting', '')
  def BUILD_URL="${BUILD_URL}console"
    if(!env.BUILD_NUMBER.equals("1")){
        println "Send slack notification"
        if ( buildResult == "SUCCESS") {
            slackSend color: "good", message: "Started Job: ${env.JOB_NAME} \n Details: ${env.BRANCH_NAME} #${build_number} \n Status: Success after ${duration} \n Started By: ${currentUser} \n (<${BUILD_URL}|Open>)"
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
