pipeline {
    agent any
    stages {
        stage('Récupération du code source') {
            steps {
                checkout([$class: 'GitSCM',
                    branches: [[name: 'ameni']],
                    userRemoteConfigs: [[url: 'https://github.com/AymenMzoughi/DevOpsProject.git']],
                    extensions: [[$class: 'CleanBeforeCheckout']]
                ])
            }
        }
        stage('Maven Clean') {
            steps {
                sh 'mvn clean'
            }
        }
        stage('Maven Compile') {
            steps {
                sh 'mvn compile'
            }
        }
       stage('Maven SonarQube') {
    steps {
        sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=sonarqube'
    }
}
stage('JaCoCo Code Coverage') {
    steps {
        sh 'mvn clean test jacoco:prepare-agent jacoco:report'
        // Publish the JaCoCo coverage report
        step([$class: 'JacocoPublisher', changeBuildStatus: true, execPattern: '**/target/jacoco.exec', classPattern: '**/target/classes', sourcePattern: '**/src/main/java', inclusionPattern: '**/*', exclusionPattern: '**/*Test*'])
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