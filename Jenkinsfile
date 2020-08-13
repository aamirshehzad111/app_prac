@Library('app_prac') _  
pipeline {
    environment {
        //This variable need be tested as string
        doError = '1'
        gitUser = sh(script: "git log -1 --pretty=format:'%an'", , returnStdout: true).trim()
    }
    agent any
    stages {
        stage('Error') {
            steps {
                echo "${env.BUILD_NUMBER}"
                echo "${env.gitUser}"
            }
        }
    }
     post {
         always {
             //script {
               //   sh "echo ${env.gitUser}"
             //}
             slack("${env.gitUser}")
         }   
     }
 }
