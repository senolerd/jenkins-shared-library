void call() {
    // Logged in with secure concerns with single quote (or triple quote) to
    // prevent credentials leaks to logs at interpolation.

    withCredentials([usernamePassword(credentialsId: env.DOCKER_CREDENTIAL_ID,
                                      usernameVariable: 'USER', passwordVariable: 'PASS')]) {
        sh 'podman login -u $USER -p $PASS $DEST_REGISTER' // Sec Sec Sec
        sh "podman push $ECR_REPO/java-maven:$APP_VER"
        }
    // Affer the image pushed successfully update codebase version to next minor with -SNAPSHOT
    updateApplicationVersion()
}
