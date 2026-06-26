void call(repoName) {
    // Checks projects AWS ECR repo. If doens't exist, creates it.
    // String repoName = "${AWS_PROJECT_NAME.toLowerCase()}"

    if ( awsCli("ecr describe-repositories --repository-name ${repoName}") != 0) {
        awsCli("ecr create-repository --repository-name ${repoName}")
        echo "Repo ${repoName} is created."
    } else {
        echo "Repo ${repoName} is exist."
    }

    withEnv(["repoName=$repoName"]){
        withCredentials([usernamePassword(credentialsId: 'aws_devops-user-1_access_k_id_and_key', passwordVariable: 'AWS_KEY', usernameVariable: 'AWS_KID')]) {
            env.ECR_REPO = sh(script: '''
                podman run --rm \
                -e AWS_ACCESS_KEY_ID=$AWS_KID \
                -e AWS_SECRET_ACCESS_KEY=$AWS_KEY \
                -e AWS_DEFAULT_REGION=$AWS_REGION \
                docker.io/amazon/aws-cli ecr describe-repositories --repository-name $repoName --query repositories[].repositoryUri --output text
                ''', returnStdout: true).trim()
        }
    }
}
