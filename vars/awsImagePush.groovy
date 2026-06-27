void call() {
    // Logged in with secure concerns with single quote (or triple quote) to
    // prevent credentials leaks to logs at interpolation.
    sh 'env'
    // awsCli("ecr get-login-password --region us-east-1 | podman login --username AWS --password-stdin $DEST_CONTAINER_REGISTRY")
    // podman push $DEST_CONTAINER_REPO:$APP_VER





        withCredentials([usernamePassword(credentialsId: 'aws_devops-user-1_access_k_id_and_key', passwordVariable: 'AWS_KEY', usernameVariable: 'AWS_KID')]) {
            sh'''
                $(podman run --rm -e AWS_ACCESS_KEY_ID=$AWS_KID -e AWS_SECRET_ACCESS_KEY=$AWS_KEY \
                -e AWS_DEFAULT_REGION=$AWS_REGION docker.io/amazon/aws-cli ecr get-login-password --region us-east-1)| \
                podman login --username AWS --password-stdin $DEST_CONTAINER_REGISTRY podman push $DEST_CONTAINER_REPO:$APP_VER
            '''


        // }

}
