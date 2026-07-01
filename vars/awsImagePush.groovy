void call() {

    String lovercaseAppName = APP_NAME.toLowerCase()

    withEnv(["lovercaseAppName=${lovercaseAppName}"]){
        withCredentials([usernamePassword(credentialsId: env.AWS_CLI_CRED_ID, passwordVariable: 'AWS_KEY', usernameVariable: 'AWS_KID')]) {
            sh'''
                echo "Getting AWS_ACCOUNT_ID"

                AWS_ACCOUNT_ID=$(podman run --rm -e AWS_ACCESS_KEY_ID=$AWS_KID -e AWS_SECRET_ACCESS_KEY=$AWS_KEY \
                -e AWS_DEFAULT_REGION=$AWS_REGION docker.io/amazon/aws-cli sts get-caller-identity --query Account --output text)

                echo "AWS_ACCOUNT_ID: ${AWS_ACCOUNT_ID}"
                DEST_CONTAINER_REGISTRY="https://$AWS_ACCOUNT_ID.dkr.ecr.${AWS_REGION}.amazonaws.com"
                
                echo "DEST_CONTAINER_REGISTRY: ${DEST_CONTAINER_REGISTRY}"

                echo "Login to ECR"
                (podman run --rm -e AWS_ACCESS_KEY_ID=$AWS_KID -e AWS_SECRET_ACCESS_KEY=$AWS_KEY \
                -e AWS_DEFAULT_REGION=$AWS_REGION docker.io/amazon/aws-cli ecr get-login-password --region ${AWS_REGION})| \
                (podman login --username AWS --password-stdin $DEST_CONTAINER_REGISTRY)


                echo "Tag image for AWS ECR"
                podman tag ${lovercaseAppName}:${APP_VER} $DEST_CONTAINER_REGISTRY/${lovercaseAppName}:${APP_VER}

                echo "Pushing image to ECR: $DEST_CONTAINER_REGISTRY/${lovercaseAppName}:${APP_VER}"
                podman push $DEST_CONTAINER_REGISTRY/${lovercaseAppName}:${APP_VER}
            '''
        }
    }
}