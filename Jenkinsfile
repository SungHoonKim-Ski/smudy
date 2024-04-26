def backendImage
def dummyImage
def AIImage
def modelImage

pipeline {
    agent any

    environment {
        DUMMY_IMAGE_NAME = "${env.DUMMY_IMAGE_NAME}"

        // BACK_IMAGE_NAME = "${env.BACK_IMAGE_NAME}"
        // AI_IMAGE_NAME = "${env.AI_IMAGE_NAME}"
        // CONTAINER_NAME = 'Back'
        // AI_CONTAINER_NAME = 'AI'
        // DATABASE_URL = "${env.DATABASE_URL}"
    }
    
    stages {
        stage('Checkout') {
            steps {
                script {
                    checkout([$class: 'GitSCM', 
                        branches: [[name: '*/deploy']], 
                        userRemoteConfigs: [[
                            url: 'https://lab.ssafy.com/s10-final/S10P31D111.git',
                            credentialsId: 'geunbo'
                        ]]
                    ])
                }
            }
        }
        // stage('Add Env') {
        //     steps {
        //         dir('back/src/main/resources') {
        //             withCredentials([file(credentialsId: 'application', variable: 'application')]) {
        //                 sh 'cp ${application} application-deploy.yml'
        //             }
        //         }

        //     }
        // }
        stage('Build and Push the Dummy Back-end Docker Image') {
            steps {
                script {
                    sh 'echo "Starting Build Dummy Back Docker Image"'
                    dir('smudy_backend/backend_dummy') {
                            withDockerRegistry(credentialsId: 'docker', url: 'https://registry.hub.docker.com') {
                                dummyImage = docker.build("${DUMMY_IMAGE_NAME}:${env.BUILD_NUMBER}", ".")

                                // Docker 빌드 결과 출력
                                if (dummyImage != 0) {
                                    echo "Docker build succeeded: ${DUMMY_IMAGE_NAME}:${env.BUILD_NUMBER}"
                                    docker.withRegistry('https://registry.hub.docker.com', 'docker') {
                                        backendImage.push()
                                    }
                                } else {
                                    error "Docker build failed"
                                }
                        }
                    }
                }
            }
        }
        // stage('Build and Push the ai-recommend Docker Image') {
        //     steps {
        //         script {
        //             sh 'echo "Starting Build AI-recommend Docker Image"'
        //             dir('AI') {
        //                     withDockerRegistry(credentialsId: 'docker', url: 'https://registry.hub.docker.com') {
        //                         AIImage = docker.build("${AI_IMAGE_NAME}:${env.BUILD_NUMBER}", 
        //                             "--build-arg DATABASE_URL=${env.DATABASE_URL} .")

        //                         // Docker 빌드 결과 출력
        //                         if (AIImage != 0) {
        //                             echo "Docker build succeeded: ${AI_IMAGE_NAME}:${env.BUILD_NUMBER}"
        //                             docker.withRegistry('https://registry.hub.docker.com', 'docker') {
        //                                 AIImage.push()
        //                             }
        //                         } else {
        //                             error "Docker build failed"
        //                         }
        //                 }
        //             }
        //         }
        //     }
            
        // }

        // stage('Build and Push the Front-end Docker Image') {
        //     steps {
        //         script {
        //             sh 'echo "Starting Build Front Docker Image"'
        //             dir('front') {
        //                 withDockerRegistry(credentialsId: 'docker', url: 'https://registry.hub.docker.com') {
                            
        //                      frontendImage = docker.build("${FRONT_IMAGE_NAME}:${env.BUILD_NUMBER}",
        //                       "--build-arg REACT_APP_API_BASE_URL=${env.REACT_APP_API_BASE_URL} .")
        //                     // Docker 빌드 결과 출력
        //                     if (frontendImage != 0) {
        //                         echo "Docker build succeeded: ${FRONT_IMAGE_NAME}:${env.BUILD_NUMBER}"
        //                         docker.withRegistry('https://registry.hub.docker.com', 'docker') {
        //                             frontendImage.push()
        //                     }
        //                     } else {
        //                         error "Docker build failed"
        //                     }
        //                 }
        //             }
        //         }
        //     }
            
        // }
        // stage('Build and Push the Ai-model Image') {
        //     steps {
        //         script {
        //             sh 'echo "Starting Build Ai-model Docker Image"'
        //             dir('back/app/ai_models/face') {
        //                 withDockerRegistry(credentialsId: 'docker', url: 'https://registry.hub.docker.com') {
                            
        //                      modelImage = docker.build("${MODEL_IMAGE_NAME}:${env.BUILD_NUMBER}")
        //                     // Docker 빌드 결과 출력
        //                     if (modelImage != 0) {
        //                         echo "Docker build succeeded: ${MODEL_IMAGE_NAME}:${env.BUILD_NUMBER}"
        //                         docker.withRegistry('https://registry.hub.docker.com', 'docker') {
        //                             modelImage.push()
        //                     }
        //                     } else {
        //                         error "Docker build failed"
        //                     }
        //                 }
        //             }
        //         }
        //     }
            
        // }     
    
        // stage('Deploy') {
        //     steps {
        //         script {
        //             sh 'docker rm -f Back || true'
        //             sh "docker run -d --name ${CONTAINER_NAME} -p 8000:8000 ${BACK_IMAGE_NAME}:${env.BUILD_NUMBER}"

        //             sh 'docker rm -f AI || true'
        //             sh "docker run -d --name ${AI_CONTAINER_NAME} -p 8081:8081 ${AI_IMAGE_NAME}:${env.BUILD_NUMBER}"
        //         }
        //     }
        // }
    }
}
