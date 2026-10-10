
pipeline {
    agent any

    stages {

        // Stage 1: Checkout source code
        stage('Checkout') {
            steps {
                echo 'Checking out source code from GitHub...'
                checkout scm
            }
        }

        // Stage 2: Build Spring Boot application
        stage('Build') {
            steps {
                echo 'Building Spring Boot application...'
                sh './mvnw clean package -Dmaven.test.skip=true'
            }
        }

        // Stage 3: Build Docker image
        stage('Docker Build') {
            steps {
                echo 'Building Docker image...'
                sh 'docker build -t 14_userservice:latest .'
            }
        }

        // Stage 4: Push Docker image to Docker Hub
        stage('Docker Push') {
            steps {
                echo 'Pushing Docker image to Docker Hub...'

                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'abhiksha4552',
                        passwordVariable: 'Abhi@2004'
                    )
                ]) {
                    sh '''
                        set -eu

                        echo "Abhi@2004" | docker login \
                            --username "abhiksha4552" \
                            --password-stdin

                        docker tag 14_userservice:latest \
                            "abhiksha4552/14_userservice:latest"

                        docker push "abhiksha4552/14_userservice:latest"

                        docker logout
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'CI/CD pipeline completed successfully!'
            echo 'Docker image pushed to Docker Hub.'
        }

        failure {
            echo 'Pipeline failed. Please check the Console Output.'
        }

        always {
            echo 'Pipeline execution finished.'
        }
    }
}
