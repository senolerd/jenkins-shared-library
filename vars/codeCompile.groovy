void call() {
    sh """
        podman run --rm -v jenkins_home:/app -w /app/workspace/$JOB_NAME $MAVEN_IMG mvn clean package --quiet
    """
}
