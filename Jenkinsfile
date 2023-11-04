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
stage('Nexus Deployment') {
    steps {
        script {
            def server = Artifactory.server 'nexus-server' // Configure your Nexus server in Jenkins
            def buildInfo = server.newBuildInfo()

            // Maven deployment configuration
            def deployer = Artifactory.newMavenBuild()
            deployer.tool = 'Maven' // Specify the Maven tool installation name
            deployer.deployerServer = server
            deployer.deployerRepo = 'deploymentRepo' // Use the repository ID from your pom.xml
            deployer.deployerSnapshotRepo = 'deploymentRepo' // Use the same repository for snapshots

            // Publish Maven artifacts
            deployer.deployArtifacts(buildInfo)

            // Publish build information to Nexus
            server.publishBuildInfo buildInfo
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