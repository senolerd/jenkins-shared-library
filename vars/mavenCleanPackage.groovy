void call() {
    // Goes inside the project clone and runs "maven clean package" by using maven image
    sh """
        podman run --rm -v dot_m2_repository:/root/.m2/repository -v jenkins_home:/app -w /app/workspace/${env.JOB_NAME} ${MAVEN_IMG} mvn clean package --quiet
    """
}
