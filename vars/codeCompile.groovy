void call() {
    echo "Compiling the code... for ${APP_VER}"
    sh """
        podman run --rm -v jenkins_home:/app -w /app/workspace/$JOB_NAME ${MAVEN_IMG} mvn clean package --quiet
    """
}
