pipeline {
    agent any
    stages {

        stage('Récupération du code source') {
                steps {
                    git branch: 'Ala', url: 'https://github.com/AymenMzoughi/DevOpsProject.git'
                }
            }


        stage('Compilation du projet') {
            steps {
                sh 'mvn clean compile'
            }
        }
    }
    post {
        success {
            echo 'Le pipeline a réussi!'
        }
        failure {
            echo 'Le pipeline a échoué!'
        }
    }
}