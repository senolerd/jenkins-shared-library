void call() {
    // Be sure calling this from a multibranch pipeline. With Multibranch ppieline the path "/app/workspace/$JOB_NAME_$GIT_BRANCH $MAVEN_IMG" 
    // becomes a valid path. Single pipeline checkouts to "/app/workspace/$JOB_NAME", no branch name!
    echo 'Initialing...'

    env.APP_VER = getAppVersion()

    echo "env.JOB_NAME: ${env.JOB_NAME}"

    echo "env.APP_VER: ${env.APP_VER}"
}



