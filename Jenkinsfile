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



         stage('JUNIT/Mock test'){
            steps{
                sh 'mvn test'
            }
        }



        stage('Nexus'){

             steps{
                 sh 'mvn deploy '
             }
         }

                  stage('Docker build')
                {
                    steps {
                         sh 'docker build -t alabh/achat .'
                    }
                }



               stage('Docker login')
               {
                   steps {
                       sh 'echo $dockerhub_PSW | docker login -u username -p password'
                   }

               }



              stage('Push docker hub') {

                steps {
                    sh 'docker push alabh/achat'
                }
            }

                    stage('DockerCompose') {

                       steps {
                                sh 'cd /var/lib/jenkins/workspace/benhamida'
								sh 'docker-compose up -d'
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
