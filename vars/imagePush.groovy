void call() {
    // Logged in with secure concerns with single quote (or triple quote) to
    // prevent credentials leaks to logs at interpolation.

    // withCredentials([usernamePassword(credentialsId: env.DOCKER_CREDENTIAL_ID,
    //                                   usernameVariable: 'USER', passwordVariable: 'PASS')]) {
    //     sh 'podman login -u $USER -p $PASS $DEST_REGISTER' // Sec Sec Sec
    //     sh "podman push $ECR_REPO/java-maven:$APP_VER"
    //     }




    // Affer the image pushed successfully update codebase version to next minor with -SNAPSHOT
    // updateApplicationVersion()

    echo "[[[     REGISTRY: ${DEST_CONTAINER_REGISTRY}  ]]]"
    echo "[[[     REGISTRY endsWith amazom.com' ${DEST_CONTAINER_REGISTRY.endsWith('amazon.com')}  ]]]"

    if (DEST_CONTAINER_REGISTRY.endsWith("amazon.com")){
        echo "IMAGE WILL BE PUSHED TO ECR"
    } else if (DEST_CONTAINER_REGISTRY.endsWith("docker.io")){
        echo "IMAGE WILL BE PUSHED TO DOCKER"
    } else {
        echo "IMAGE PUSHER DOESN'T KNOW HOW TO PUSH TO THIS REGISTRY"
    }
}
