node {
   stage('Checkout') {
        checkout scm
   }

   stage('Build') {  
        if (isUnix()) {
          sh './gradlew clean build' 
        } 
	else {
          bat './gradlew.bat clean build'
        }
   }
   
   stage('Code analysis') {
	if (isUnix()) {
          sh './gradlew sonarqube -Dsonar.host.url=http://localhost:9000'
        } 
	else {
          bat './gradlew sonarqube -Dsonar.host.url=http://localhost:9000'
        }
   }
   
   stage('Nexus Upload') {
        nexusArtifactUploader(
          nexusVersion: 'nexus3',
          protocol: 'http',
          nexusUrl: 'localhost:8081',
          groupId: 'hello',
          version: '0.0.1-SNAPSHOT',
          repository: 'gradle-springboot-repo',
          credentialsId: 'nexus-admin-creds',
          artifacts: [
            [artifactId: 'springbootapp',
            classifier: '',
            file: 'build/libs/springbootapp-0.0.1-SNAPSHOT.jar',
            type: 'jar']
          ]  
        )  
   }
   
   stage('Docker Build') {
        if (isUnix()) {
          sh 'docker build -t sravanimadireddy/springboot-restapi:v1.0 .'
        } 
        else {
          bat 'docker build -t sravanimadireddy/springboot-restapi:v1.0 .'
        }
   }
   
   stage('Publish') {
        withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) 
	{
	  bat "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
          bat 'docker push sravanimadireddy/springboot-restapi:v1.0'
        }
   }
   
   stage('Deploy Application') {
        bat "set KUBECONFIG=\"C:\\Users\\VenkataSatyaSravaniM\\.kube\\config\" & kubectl config set-context docker-for-desktop-cluster & kubectl create -f kub-deploy-files/mongo-service.yaml"
	bat "set KUBECONFIG=\"C:\\Users/VenkataSatyaSravaniM\\.kube\\config\" & kubectl config set-context docker-for-desktop-cluster & kubectl create -f kub-deploy-files/mongo-controller.yaml"
	bat "set KUBECONFIG=\"C:\\Users\\VenkataSatyaSravaniM\\.kube\\config\" & kubectl config set-context docker-for-desktop-cluster & kubectl create -f kub-deploy-files/deployment.yaml"
	bat "set KUBECONFIG=\"C:\\Users\\VenkataSatyaSravaniM\\.kube\\config\" & kubectl config set-context docker-for-desktop-cluster & kubectl create -f kub-deploy-files/service.yaml"
	bat "echo Application is Running on: http://localhost:30036"
	bat "echo Application REST-API URI : http://localhost:30036/products"
   }
}
