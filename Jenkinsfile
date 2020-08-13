@Library('app_prac') _  
pipeline { 
    environment {
        doError = '1'
        gitUser = sh(script: "git log -1 --pretty=format:'%an'", , returnStdout: true).trim()
        gitUserEmail = sh(script: "git log -1 --pretty=format:'%ce'", , returnStdout: true).trim()
    }
    agent any
    stages {
        stage('Error') {
            steps {
                echo "${env.BUILD_NUMBER}"
            }
        }
    }
     post {
         always {
             slack("${env.gitUser}")
             sendEmail("${env.gitUserEmail}")
         }   
     }
 }
