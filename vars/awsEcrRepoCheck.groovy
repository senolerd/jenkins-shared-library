void call(repoName) {


    withEnv(["repoName=$repoName"]){
        withCredentials([usernamePassword(credentialsId: 'aws_devops-user-1_access_k_id_and_key', passwordVariable: 'AWS_KEY', usernameVariable: 'AWS_KID')]) {
            env.DEST_CONTAINER_REPO = sh(script: '''
                podman run --rm \
                -e AWS_ACCESS_KEY_ID=$AWS_KID \
                -e AWS_SECRET_ACCESS_KEY=$AWS_KEY \
                -e AWS_DEFAULT_REGION=$AWS_REGION \
                docker.io/amazon/aws-cli ecr describe-repositories --repository-name $repoName --query repositories[].repositoryUri --output text
                ''', returnStdout: true).trim()
        }
            env.DEST_CONTAINER_REGISTRY = env.DEST_CONTAINER_REPO.split("/")[0]
            echo "DEST_CONTAINER_REGISTRY: ${env.DEST_CONTAINER_REGISTRY}"
    }
}
