void call() {
    // Be sure calling this from a multibranch pipeline. With Multibranch ppieline the path "/app/workspace/$JOB_NAME_$GIT_BRANCH $MAVEN_IMG" 
    // becomes a valid path. Single pipeline checkouts to "/app/workspace/$JOB_NAME", no branch name!
    echo 'Initialing...'
    env.APP_VER = getAppVersion()
    // env.JOB_DIR = env.JOB_NAME.replace("/","_")

    // env.APP_VER = sh(script:"""podman run --rm -v jenkins_home:/app \
    // -w /app/workspace/${JOB_DIR} -v dot_m2_repository:/root/.m2/repository \
    // ${MAVEN_IMG} mvn help:evaluate -Dexpression=project.version -q -DforceStdout""", returnStdout: true).trim()

    echo "[__init__] The application version being worked on, env.APP_VER: ${env.APP_VER}. "
}



