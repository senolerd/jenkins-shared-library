void call() {
    // Logged in with secure concerns with single quote (or triple quote) to
    // prevent credentials leaks to logs at interpolation.

    awsCli('ecr get-login-password --region us-east-1 | podman login --username AWS --password-stdin ${DEST_CONTAINER_REGISTRY} podman push ${DEST_CONTAINER_REPO}:${APP_VER}')
}