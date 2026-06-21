void call() {
    echo 'JSL Initialing...'
    APP_VER = sh(script:"podman run --rm -v jenkins_home:/app -w /app/workspace/$JOB_NAME $MAVEN_IMG mvn help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
}
