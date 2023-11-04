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
