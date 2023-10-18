pipeline {
    agent any

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
       
  stages {
         stage('Code checkout according to environment') {    
             steps
             {       
              script
                 {           
                     if(params.Environment=='Dev')
                     {
                        checkout scmGit(branches: [[name: '*/Dev']], extensions: [], userRemoteConfigs: [[credentialsId: '9697fc83-78b5-4285-a54d-5ff22adce8e1', url: 'https://github.com/AayushMalviyaa/MiniAssignment.git']])
                     }
                     else if(params.Environment=='Prod')
                     {
                         checkout scmGit(branches: [[name: '*/Prod']], extensions: [], userRemoteConfigs: [[credentialsId: '9697fc83-78b5-4285-a54d-5ff22adce8e1', url: 'https://github.com/AayushMalviyaa/MiniAssignment.git']])
                     }
                     else 
                     {
                         error('Invalid choice')
                     }


                 }
             }
         }

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
        
        stage('SonarQube analysis') {
        steps{
            withSonarQubeEnv('sonarqube-9.4') {
                sh "mvn -f pom.xml clean install sonar:sonar"
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
                    "pattern": "*.war",
                    "target": "Main/"
                    }
                ]
                }''',
                )
            }
        }
stage("Deploy to Tomcat") {
            steps {
                script {
                    def warFileInWorkspace = "/var/lib/jenkins/workspace/com.nagarro.Pipeline.MiniPipeline.AayushMalviya/target/ROOT.war"
                    def tomcatUrl = params.Environment == 'Dev' ? "http://192.168.56.101:8081/" : "http://192.168.56.101:9090/"

 

                    echo "Deploying $warFileInWorkspace to Tomcat at $tomcatUrl"

                    sh "curl --upload-file ${warFileInWorkspace} '$tomcatUrl/manager/text/deploy?path=/app&update=true' -u tomcat:password"
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
