void call() {
    // Goes inside the project clone and runs "maven clean package" by using maven image
    sh """
        podman run --rm -v jenkins_home:/app -w /app/workspace/$JOB_NAME $MAVEN_IMG mvn clean package --quiet
    """
}
