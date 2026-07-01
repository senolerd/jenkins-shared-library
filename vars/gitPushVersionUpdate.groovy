void call() {
    // Pushes version updated pom.xml to repo
    sh """
        git config user.name "Jenkins Build Bot"
        git config user.email "jenkins@local"
        git add pom.xml
        # APP_NEXT_VER is created by mavenIncrementVersion()
        git commit -m "[ci] Updating version to next minor (${APP_VER} => ${APP_NEXT_VER})". 
    """

    withCredentials([string(credentialsId: env.GITHUB_CRED_ID, variable: 'GITHUB_TOKEN')]) {
        env.gitUrlNoProtocol = sh(script:''' echo $GIT_URL|awk -F'//' '{print $2}' ''', returnStdout: true).trim()

        sh '''
            git push https://$GITHUB_TOKEN@$gitUrlNoProtocol HEAD:main
        '''
    }
}