import groovy.json.JsonSlurperClassic
def jsonParse(def json) {
    new groovy.json.JsonSlurperClassic().parseText(json)
}
pipeline {
    agent any
    environment {
        CHANNEL='C04B17VE0JH'
        NEXUS_USER         = credentials('nexus-user')
        NEXUS_PASSWORD     = credentials('nexus-password')
    }
    stages {
        stage("Paso 1: Build && Test"){
            steps {
                script{
                    env.STAGE='Paso 1'
                    sh "echo 'Build && Test!'"
                    sh "./mvnw clean package -e"
                }
            }
        }
        stage("Paso 2: Sonar - Análisis Estático"){
            steps {
                script{
                    env.STAGE='Paso 2'
                    sh "echo 'Análisis Estático!'"
                        withSonarQubeEnv('sonarqube') {
                            sh "echo 'Calling sonar by ID!'"
                            // Run Maven on a Unix agent to execute Sonar.
                            sh './mvnw clean verify sonar:sonar -Dsonar.projectKey=laboratorio4-grupo6 -Dsonar.projectName=laboratorio4-grupo6 -Dsonar.java.binaries=build'
                        }
                }
            }
        }
        stage("Paso 3: Curl Springboot maven sleep 20"){
            steps {
                script{
                    env.STAGE='Paso 3'
                    sh "nohup bash ./mvnw spring-boot:run  & >/dev/null"
                    sh "sleep 20 && curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'"
                }
            }
        }
        stage("Paso 4: Detener Spring Boot"){
            steps {
                script{
                    env.STAGE='Paso 4'
                    sh '''
                        echo 'Process Spring Boot Java: ' $(pidof java | awk '{print $1}')
                        sleep 20
                        kill -9 $(pidof java | awk '{print $1}')
                    '''
                }
            }
        }
        stage("Paso 5: Subir Artefacto a Nexus"){
            steps {
                script{
                    env.STAGE='Paso 5'
                    nexusPublisher nexusInstanceId: 'nexus',
                        nexusRepositoryId: 'maven-usach-ceres',
                        packages: [
                            [$class: 'MavenPackage',
                                mavenAssetList: [
                                    [classifier: '',
                                    extension: 'jar',
                                    filePath: 'build/DevOpsUsach2020-1.0.1.jar'
                                ]
                            ],
                                mavenCoordinate: [
                                    artifactId: 'DevOpsUsach2020',
                                    groupId: 'com.devopsusach2020',
                                    packaging: 'jar',
                                    version: '1.0.1'
                                ]
                            ]
                        ]
                }
            }
        }
        stage("Paso 6: Descargar Nexus"){
            steps {
                script{
                    env.STAGE='Paso 6'
                    sh ' curl -X GET -u $NEXUS_USER:$NEXUS_PASSWORD "http://nexus:8081/repository/maven-usach-ceres/com/devopsusach2020/DevOpsUsach2020/1.0.1/DevOpsUsach2020-1.0.1.jar" -O'
                }
            }
        }
         stage("Paso 7: Levantar Artefacto Jar en server Jenkins"){
            steps {
                script{
                    env.STAGE='Paso 7'
                    sh 'nohup java -jar DevOpsUsach2020-1.0.1.jar & >/dev/null'
                }
            }
        }
          stage("Paso 8: Testear Artefacto - Dormir(Esperar 20sg) "){
            steps {
                script{
                    env.STAGE='Paso 8'
                    sh "sleep 20 && curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'"
                }
            }
        }
        stage("Paso 9:Detener Atefacto jar en Jenkins server"){
            steps {
                script {
                    env.STAGE='Paso 9'
                    sh '''
                        echo 'Process Java .jar: ' $(pidof java | awk '{print $1}')
                        sleep 20
                        kill -9 $(pidof java | awk '{print $1}')
                    '''
                }
            }
        }
    }
    post {
        success {
            slackSend color: 'good', message: "[Grupo6][Pipeline IC/CD][Rama: ${JOB_NAME}][Stage: ${env.STAGE}][Resultado:Éxito/Success]", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack', channel: $CHANNEL
        }
        failure {
            slackSend color: 'danger', message: "[Grupo6][Pipeline IC/CD][Rama: ${JOB_NAME}][Stage: ${env.STAGE}][Resultado:Error/Fail]", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack', channel: $CHANNEL
        }
    }
}
