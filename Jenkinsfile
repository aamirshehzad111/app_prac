@Library('app_prac') _  
pipeline {
    environment {
        //This variable need be tested as string
        doError = '1'
    }
   
    agent any
    
    stages {
        stage('Error') {
            when {
                expression { doError == '1' }
            }
            steps {
                echo "Failure"
                error "failure test. It's work"
            }
        }
        
        stage('Success') {
            when {
                expression { doError == '0' }
            }
            steps {
                echo "ok"
            }
        }

        

    }
    post {
        always {
            echo 'I will always say Hello again!'
        slack()
        sendEmail('aamirshehzad8822@gmail.com, aamir@eurustechnologies.com', '${BUILD_URL}', env.BRANCH_NAME)
    }
}
}