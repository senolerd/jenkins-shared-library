void call() {
    // Be sure calling this from a multibranch pipeline. With Multibranch ppieline the path "/app/workspace/$JOB_NAME_$GIT_BRANCH $MAVEN_IMG" 
    // becomes a valid path. Single pipeline checkouts to "/app/workspace/$JOB_NAME", no branch name!
    echo 'JSL Initialing...'
    // env.APP_VER = sh(script:"podman run --rm -v jenkins_home:/app -w /app/workspace/${JOB_NAME} ${MAVEN_IMG} mvn help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
    echo "JOB_NAME: ${JOB_NAME}"
    echo "MAVEN_IMG: ${MAVEN_IMG}"
    sh "echo Env:"
    sh 'printenv'
    env.APP_VER = sh(script:"podman run --rm -it -v jenkins_home:/app -w /app/workspace/${JOB_NAME} ${MAVEN_IMG} ls -al", returnStdout: true)

    // sh 'echo "[__init__] APP version is (after): ${env.APPVER}"'
}
