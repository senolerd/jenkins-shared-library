void call() {
    withCredentials([usernamePassword(credentialsId: env.AWS_CLI_CRED_ID, passwordVariable: 'AWS_KEY', usernameVariable: 'AWS_KID')]) {
        sh'''
            (podman run --rm -e AWS_ACCESS_KEY_ID=$AWS_KID -e AWS_SECRET_ACCESS_KEY=$AWS_KEY \
            -e AWS_DEFAULT_REGION=$AWS_REGION docker.io/amazon/aws-cli ecr get-login-password --region us-east-1)| \
            (podman login --username AWS --password-stdin $DEST_CONTAINER_REGISTRY)

            podman push $DEST_CONTAINER_REPO:$APP_VER
        '''
    }
}
