
pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code from GitHub...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building Spring Boot application...'
                sh './mvnw clean package -Dmaven.test.skip=true'
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building Docker image...'
                sh 'docker build -t 14_userservice:latest .'
            }
        }

        stage('Docker Push') {
            steps {
                echo 'Pushing Docker image to Docker Hub...'

                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'abhiksha4552', passwordVariable: 'Abhi@2004')]) {
                    sh 'echo "Abhi@2004" | docker login --username "abhiksha4552" --password-stdin'
                    sh 'docker tag 14_userservice:latest "abhiksha4552/14_userservice:latest"'
                    sh 'docker push "abhiksha4552/14_userservice:latest"'
                    sh 'docker logout'
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                echo 'Deploying User Service on EC2...'
                sh 'docker pull abhiksha4552/14_userservice:latest'
                sh 'docker rm -f user-service || true'
                sh 'docker run -d --name user-service --restart unless-stopped -p 8081:8080 abhiksha4552/14_userservice:latest'
                sh 'docker ps --filter name=user-service'
            }
        }
    }

    post {
        success {
            echo 'CI/CD pipeline completed successfully!'
            echo 'Docker image pushed and deployment stage completed.'
        }

        failure {
            echo 'Pipeline failed. Please check the Console Output.'
        }

        always {
            echo 'Pipeline execution finished.'
        }
    }
}
