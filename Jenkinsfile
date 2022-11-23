import groovy.json.JsonSlurperClassic

def jsonParse(def json) {
    new groovy.json.JsonSlurperClassic().parseText(json)
}
pipeline {
    agent any
    stages {
        stage('Analyse code') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh './mvnw clean verify sonar:sonar -Dsonar.projectKey=ms-iclab'
                }
            }
        }
        stage("Build"){
            steps {
                script {
                    sh "./mvn clean compile -e'"
                }
            }
        }
        stage("Testing"){
            steps {
                script {
                    sh "./mvn clean test -e'"
                }
            }
        }
        stage("Package artifact"){
            steps {
                script {
                    sh "./mvnw clean package -e"
                }
            }
        }
    }
    post {
        success {
			slackSend color: 'good', message: "[Mentor Devops] [${JOB_NAME}] [${BUILD_TAG}] Ejecucion Exitosa", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'slack-secret', channel: 'C045V4XEHHN'
		}
		failure {
            slackSend color: 'danger', message: "[Mentor Devops] [${env.JOB_NAME}] [${BUILD_TAG}] Ejecucion fallida en stage [${env.STAGE}]", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'slack-secret', channel: 'C045V4XEHHN'
        }
    }
}
