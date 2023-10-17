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
stage('Tomcat Deployment'){
            steps{
                script{
                    if(params.Environment == 'Dev'){
                        def tomcatStatus = sh(returnStdout: true, script: 'netstat -tuln | grep 8083 || true')
                        if(tomcatStatus)
                            {
                                echo "Tomcat is running. Stopping Tomcat...."
                                sh "sh /root/apache-tomcat-7.0.109/bin/catalina.sh stop"
                    }
                    else{
                        echo "Tomcat not running, Starting one...."
                    }
                    sh 'sudo sed -i "s/8083/8082/" /root/apache-tomcat-7.0.109/conf/server.xml'
                    sh 'sudo rm -r /root/apache-tomcat-7.0.109/webapps/*'
                    sh 'sudo cp calculatorSpring/target/calculatorSpring.war /root/apache-tomcat-7.0.109/webapps/ROOT.war'
                    sh 'sudo sh /root/apache-tomcat-7.0.109/bin/catalina.sh start'
                    }
                    else if(params.Environment == 'Prod'){
                        def tomcatStatus = sh(returnStdout: true, script: 'netstat -tuln | grep 8082 || true')
                        if(tomcatStatus)
                            {
                                echo "Tomcat is running. Stopping Tomcat...."
                                sh "sh /root/apache-tomcat-7.0.109/bin/catalina.sh stop"
                            }
                        else{
                                echo "Tomcat not running, Starting one...."
                        }
                    sh 'sudo sed -i "s/8082/8083/" /root/apache-tomcat-7.0.109/conf/server.xml'
                    sh 'sudo rm -r /root/apache-tomcat-7.0.109/webapps/*'
                    sh 'sudo cp calculatorSpring/target/calculatorSpring.war /root/apache-tomcat-7.0.109/webapps/ROOT.war'
                    sh 'sudo sh /root/apache-tomcat-7.0.109/bin/catalina.sh start'
                    }
                    else{
                        error('Kindly lookup the code')
                    }
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
