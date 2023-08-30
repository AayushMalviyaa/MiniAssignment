pipeline {
    agent any
 environment {
        DOCKER_HUB_CREDENTIALS = credentials('97c36c51-b00f-4bd1-911b-3143b0f3b00d')
    }
    tools {
        maven "maven.3.2.5"
    }
parameters {
        choice(
            choices: ['Dev', 'Prod'],
            description: 'Select the environment',
            name: 'Environment'
        )
    }
    //
    stages {
        stage("Maven Build") {
            steps {
                sh "mvn -f pom.xml clean install"
            }
        }
        
        stage('Test') {
            steps {
                sh "mvn test"
            }
            post {
                success {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Sonar Analysis') {
            steps {
                withSonarQubeEnv('sonarqube-9.4') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
         stage("Pushing Artifacts"){
            steps{
                rtUpload (
                serverId: 'arti',
                spec: '''{
                "files": [
                    {
                    "pattern": "*.jar",
                    "target": "Main/"
                    }
                ]
                }''',
                )
            }
        }
         stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image
                    docker.build("aayushmalviya/calculator-app:${env.BUILD_ID}", "-f Dockerfile .")
                }
            }
        }
       
     
         stage('Run the container') {
    steps {
        script {
            def docker_container = sh(returnStdout: true, script: 'docker ps -a -f name="MiniAssignment" -q').trim()
            
            if (docker_container) {
                sh "docker stop ${docker_container}"
                sh "docker rm --force ${docker_container}"
            }
            
            def port = params.Environment == 'Dev' ? '8084' : '8085'
            sh "docker run -d --name MiniAssignment -p ${port}:8080 aayushmalviya/calculator-app:${env.BUILD_ID}"
        }
    }
}
        stage('Email Notification') {
    steps {
        emailext body: 'Deployment completed successfully.',
                 recipientProviders: [[$class: 'CulpritsRecipientProvider']],
                 subject: 'Deployment Status',
                 to: 'aayushmalviya202@gmail.com' // Replace with the recipient's email address
    }
}

//         stage('Push Docker Image') {
//     steps {
//         script {
//             // Push the Docker image to Docker Hub
//             docker.withRegistry('https://registry.hub.docker.com', '97c36c51-b00f-4bd1-911b-3143b0f3b00d') {
//                 docker.image("aayushmalviya/calculator-app:${env.BUILD_ID}").push()
//             }
//         }
//     }
// }
    


        
        
    }
}
