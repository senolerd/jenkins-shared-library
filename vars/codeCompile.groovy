void call() {
    echo "APP_VER: ${env.APP_VER}"
    echo "env.APP_VER: ${env.APP_VER}"
    echo "JOB_NAME: $JOB_NAME"
    echo "MAVEN_IMG: $MAVEN_IMG"



    // sh """
    //     podman run --rm -v jenkins_home:/app -w /app/workspace/$JOB_NAME ${MAVEN_IMG} mvn clean package --quiet
    // """
}
