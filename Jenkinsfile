pipeline {
    agent any
    stages {
        stage('Récupération du code source') {
            steps {
                checkout([$class: 'GitSCM',
                    branches: [[name: 'ZainebBouallagui']],
                    userRemoteConfigs: [[url: 'https://github.com/AymenMzoughi/DevOpsProject.git']],
                    extensions: [[$class: 'CleanBeforeCheckout']]
                ])
            }
        }
        stage('Nettoyage du projet') {
            steps {
                script {
                    def repoPath = "achat"
                    dir(repoPath) {
                        sh "mvn clean"
                    }
                }
            }
        }
               stage('Unit Tests') {
            steps {
                script {
                    def repoPath = "achat"
                    sh "cd ${repoPath} && mvn test"
                }  
            }
        }
        
        stage('Artifact Construction') {
            steps {
                script {
                    def repoPath = "achat"
                    sh "cd ${repoPath} && mvn package -DskipTests"
                }
            }
        }
         stage('Code Quality Check via SonarQube') {
            steps {
                script {
                    def repoPath = "achat"
                    dir(repoPath) {
                        withSonarQubeEnv(credentialsId: 'Secret text', installationName: 'sonar-10.2.1') {
                            sh 'mvn sonar:sonar'
                        }
                    }
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
