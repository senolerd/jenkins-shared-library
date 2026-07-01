void call() {

    String lovercaseAppName = APP_NAME.toLowerCase()

    withEnv(["lovercaseAppName=${lovercaseAppName}"]){
        withCredentials([usernamePassword(credentialsId: env.AWS_CLI_CRED_ID, passwordVariable: 'AWS_KEY', usernameVariable: 'AWS_KID')]) {

            echo "Getting AWS_ACCOUNT_ID"

            env.AWS_ACCOUNT_ID = sh(script: '''
                podman run --rm -e AWS_ACCESS_KEY_ID=$AWS_KID -e AWS_SECRET_ACCESS_KEY=$AWS_KEY \
                -e AWS_DEFAULT_REGION=$AWS_REGION docker.io/amazon/aws-cli sts get-caller-identity --query Account --output text
                ''', returnStdout: true).trim()

            echo "AWS_ACCOUNT_ID: ${AWS_ACCOUNT_ID}"
            env.ECR_REGISTRY = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
            echo "ECR_REGISTRY: ${ECR_REGISTRY}"

            awsCli("ecr create-repository --repository-name ${lovercaseAppName} --region ${AWS_REGION}")
            // ecr create-repository --repository-name your-repo-name 

            sh'''

                echo "Login to ECR"
                (podman run --rm -e AWS_ACCESS_KEY_ID=$AWS_KID -e AWS_SECRET_ACCESS_KEY=$AWS_KEY \
                -e AWS_DEFAULT_REGION=$AWS_REGION docker.io/amazon/aws-cli ecr get-login-password --region ${AWS_REGION})| \
                (podman login --username AWS --password-stdin $ECR_REGISTRY)


                echo "Tag image for AWS ECR"
                podman tag ${lovercaseAppName}:${APP_VER} $ECR_REGISTRY/${lovercaseAppName}:${APP_VER}

                echo "Pushing image to ECR: $ECR_REGISTRY/${lovercaseAppName}:${APP_VER}"
                podman push $ECR_REGISTRY/${lovercaseAppName}:${APP_VER}
            '''
        }
    }
}