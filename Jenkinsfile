import groovy.json.JsonSlurperClassic

def jsonParse(def json) {
    new groovy.json.JsonSlurperClassic().parseText(json)
}
pipeline {
    agent any
    environment {
        NEXUS_USER         = credentials('nexus-user')
        NEXUS_PASSWORD     = credentials('nexus-password')
    }
    stages {
        stage('Analyse code') {
            steps {
                script {
                    env.STAGE='Analyse code'
                }
                withSonarQubeEnv('sonarqube') {
                    sh './mvnw clean verify sonar:sonar -Dsonar.projectKey=ms-iclab'
                }
            }
        }
        stage("Build"){
            steps {
                script {
                    env.STAGE='Build'
                    sh "./mvnw clean compile -e"
                }
            }
        }
        stage("Testing"){
            steps {
                script {
                    env.STAGE='Testing'
                    sh "./mvnw clean test -e"
                }
            }
        }
        stage("Package artifact"){
            steps {
                script {
                    env.STAGE='Generating Artifact'
                    sh "./mvnw clean package -e"
                }
            }
        }
        stage("Subir Nexus"){
                        nexusPublisher nexusInstanceId: 'nexus3',
                        nexusRepositoryId: 'devops-usach-nexus',
                        packages: [
                            [$class: 'MavenPackage',
                                mavenAssetList: [
                                    [classifier: '',
                                    extension: '.jar',
                                    filePath: 'build/libs/DevOpsUsach2020-0.0.1.jar'
                                ]
                            ],
                                mavenCoordinate: [
                                    artifactId: 'DevOpsUsach2020',
                                    groupId: 'com.devopsusach2020',
                                    packaging: 'jar',
                                    version: '0.0.1'
                                ]
                            ]
                        ]
                    }
                    stage("Descargar Nexus"){
                        sh ' curl -X GET -u $NEXUS_USER:$NEXUS_PASSWORD "http://nexus3:8081/repository/devops-usach-nexus/com/devopsusach2020/DevOpsUsach2020/0.0.1/DevOpsUsach2020-0.0.1.jar" -O'
                    }
                    stage("Levantar Artefacto Jar"){
                        sh 'nohup bash java -jar DevOpsUsach2020-0.0.1.jar & >/dev/null'
                    }
                    stage("Testear Artefacto - Dormir(Esperar 20sg) "){
                       sh "sleep 20 && curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'"
                    }

    }
    post {
        success {
			slackSend color: 'good', message: "[Mentor Devops] [${JOB_NAME}] [${BUILD_TAG}] Ejecucion Exitosa", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack', channel: 'C045V4XEHHN'
		}
		failure {
            slackSend color: 'danger', message: "[Mentor Devops] [${env.JOB_NAME}] [${BUILD_TAG}] Ejecucion fallida en stage [${env.STAGE}]", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack', channel: 'C045V4XEHHN'
        }
    }
}
