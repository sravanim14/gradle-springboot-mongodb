node {
   stage ('Checkout') {
    checkout scm
   }

   stage ('Build') {  
    if (isUnix()) {
      sh './gradlew clean build' 
    } 
	else {
      bat './gradlew.bat clean build'
    }
   }

   stage ('Test') {
    if (isUnix()) {
     sh './gradlew test'
    } 
	else {
     bat './gradlew.bat test'
    }
   }
   
   stage ('Nexus Upload') {
    nexusArtifactUploader(
      nexusVersion: 'nexus3',
      protocol: 'http',
      nexusUrl: 'localhost:8081',
      groupId: 'hello',
      version: '0.0.1-SNAPSHOT',
      repository: 'gradle-springboot-repo',
      credentialsId: 'nexus-admin-creds',
      artifacts: [
        [artifactId: 'springboot-mongodb-restapi-2017',
         classifier: '',
         file: 'build/libs/springboot-mongodb-restapi-2017-0.0.1-SNAPSHOT.jar',
         type: 'jar']
      ]
      )  
   }
   
   stage ('Docker Build') {
    if (isUnix()) {
      sh 'docker build -t sravani/springboot-mongo:latest .'
    } 
    else {
      bat 'docker build -t sravani/springboot-mongo:latest .'
    }
   }
}
