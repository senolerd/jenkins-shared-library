void call() {
    withCredentials([usernamePassword(credentialsId: env.AWS_CLI_CRED_ID, passwordVariable: 'AWS_KEY', usernameVariable: 'AWS_KID')]) {
        sh'''
            echo "Getting AWS_ACCOUNT_ID"

            AWS_ACCOUNT_ID=(podman run --rm -e AWS_ACCESS_KEY_ID=$AWS_KID -e AWS_SECRET_ACCESS_KEY=$AWS_KEY \
            -e AWS_DEFAULT_REGION=$AWS_REGION docker.io/amazon/aws-cli sts get-caller-identity --query Account --output text)
            
            echo "AWS_ACCOUNT_ID: ${AWS_ACCOUNT_ID}"


            echo "Login to ECR"

            (podman run --rm -e AWS_ACCESS_KEY_ID=$AWS_KID -e AWS_SECRET_ACCESS_KEY=$AWS_KEY \
            -e AWS_DEFAULT_REGION=$AWS_REGION docker.io/amazon/aws-cli ecr get-login-password --region ${AWS_REGION})| \
            (podman login --username AWS --password-stdin $DEST_CONTAINER_REGISTRY)

            echo "Pushing to ECR"

            echo "Pushing image to ECR: https://$AWS_ACCOUNT_ID.dkr.ecr.${AWS_REGION}.amazonaws.com:${APP_VER}"
            podman push https://$AWS_ACCOUNT_ID.dkr.ecr.${AWS_REGION}.amazonaws.com:${APP_VER}
        '''
    }
}
