void call() {
    // Be sure calling this from a multibranch pipeline. With Multibranch ppieline the path "/app/workspace/$JOB_NAME_$GIT_BRANCH $MAVEN_IMG" 
    // becomes a valid path. Single pipeline checkouts to "/app/workspace/$JOB_NAME", no branch name!
    echo 'JSL Initialing...'
    env.APP_VER = sh(script:"podman run --rm -v jenkins_home:/app -w /app/workspace/${JOB_NAME}_${GIT_BRANCH} ${MAVEN_IMG} mvn help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
}
