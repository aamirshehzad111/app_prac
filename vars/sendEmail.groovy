#!/usr/bin/env groovy

@NonCPS
def getEMail() {
  def user = currentBuild.rawBuild.causes[0].userId 
  return  def user = build.causes[0].userId
}

def call(String buildUrl, String branch) {
 def status, logRegex
 
 def emailID = getEMail()
    switch (currentBuild.currentResult) {
        case 'SUCCESS':
            status = 'successed'
            logRegex = 'SUCCESS'
            break

        case 'UNSTABLE':
            status = 'unstable'
            logRegex = 'FAILURE'
            break

        case 'FAILURE':
            status = 'failed'
            logRegex = 'FAILURE'
            break

        case 'ABORTED':
            status = 'canceled'
            logRegex = 'ABORTED'
            break

    }
    emailext(subject: "Build $status - ${JOB_NAME} #${BUILD_NUMBER} ",
            body: """ Job: ${env.JOB_NAME}\n Branch: ${branch}\n Build Number: ${BUILD_NUMBER}\n Build Url: ${buildUrl}\n Status: ${currentBuild.currentResult}"""
            , from: '"Jenkins server" <foo@acme.org>'
             , to: "${emailID}")

}
