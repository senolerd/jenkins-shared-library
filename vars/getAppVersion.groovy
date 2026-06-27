String call() {
    // Get version via build-helper plugin which parses version into parts and allows to easily increment them if needed

    String jobDir = env.JOB_NAME.replace('/','_')

    return sh(script:"""podman run --rm -v jenkins_home:/app \
    -w /app/workspace/${jobDir} -v dot_m2_repository:/root/.m2/repository \
    ${MAVEN_IMG} mvn help:evaluate -Dexpression=project.version -q -DforceStdout""", returnStdout: true).trim()

}
