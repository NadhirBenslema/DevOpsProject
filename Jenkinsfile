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

        stage('SonarQube analysis') {
            steps {
                script {
                    withSonarQubeEnv(installationName: 'sq1') {
                        sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.4.0.905:sonar'
                    }
                }
            }
        }


        stage('JUNIT/Mockito') {
            steps {
                script {
                    // Étape de compilation et d'exécution des tests Maven
                    sh 'mvn clean test'
                }
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
