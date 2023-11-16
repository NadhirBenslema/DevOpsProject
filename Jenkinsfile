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

        stage('Analyse SonarQube') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=alamadara1998'
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