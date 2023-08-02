node
{
   stage('checkout code'){
       git branch: 'master', url: 'https://github.com/sahilmahale11/kubernetes.git'
   }
   
    stage('mysql deployment'){
       sshagent(credentials: ['local1']) {
       sh '''kubectl apply -f wordpre-phpmysql-mysql-deployments/mysql-user-pass.yaml
	    kubectl apply -f wordpre-phpmysql-mysql-deployments/mysql-db-url.yaml
                kubectl apply -f wordpre-phpmysql-mysql-deployments/mysql-root-pass.yaml
                kubectl apply -f wordpre-phpmysql-mysql-deployments/mysql-pv.yaml
                kubectl apply -f wordpre-phpmysql-mysql-deployments/mysql-pvc.yaml
                kubectl apply -f wordpre-phpmysql-mysql-deployments/mysql-deployment.yaml
                kubectl apply -f wordpre-phpmysql-mysql-deployments/mysql-service.yaml'''
}
}
    stage('php-myadmin-deployment') {
     	sshagent(credentials: ['local1']) {			     
            sh '''kubectl apply -f wordpre-phpmysql-mysql-deployments/phpmyadmin-deploy.yaml
	              kubectl apply -f wordpre-phpmysql-mysql-deployments/phpmyadmin-service.yaml'''         
	                }
	}    
    stage('wordpress-deployment') {
    	sshagent(credentials: ['local1']) {
	sh '''kubectl apply -f wordpre-phpmysql-mysql-deployments/wordpress-deploy.yaml
	              kubectl apply -f wordpre-phpmysql-mysql-deployments/wordpress-service.yaml'''         
	                 
			    }
           }
}
