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
                        withSonarQubeEnv(credentialsId: 'jenkinsToken', installationName: 'sonar-10.2.1') {
                            sh 'mvn sonar:sonar'
                        }
                    }
                }
            }
        }
        stage('Publish To Nexus') {
            steps {
                script {
                    sh 'cd achat && mvn deploy'
                }
            }
        }

        stage('Build images') {
            steps {
                script {
                    sh 'docker build -t zainebbouallagui/zainebbouallagui:backend .'
                    sh 'docker pull mysql:latest'
                }
            }
        }

        stage('Push images to hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerHub', variable: 'dockerHub')]) {
                        sh 'docker login -u zainebbouallagui -p ${dockerHub}'
                        sh 'docker push zainebbouallagui/devops:backend'
                    }
                }
            }
        }

        stage('Docker Compose') {
            steps {
                script {
                    sh 'docker-compose up -d '
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
