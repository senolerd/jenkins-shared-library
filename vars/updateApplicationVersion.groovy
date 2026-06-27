void call() {
    // I preffer to add agent's ssh public key to github if the agent not ephemeral
    incrementVersion()
    sh """
        git config user.name "Jenkins Build Bot"
        git config user.email "jenkins@local"
        git add pom.xml
        git commit -m "[ci] Updating version to next minor (${APP_VER} => ${getProjectVersion()})"
    """

    withCredentials([string(credentialsId:'github_PAT', variable: 'GITHUB_TOKEN')]) {
        env.gitUrlNoProtocol = sh(script:''' echo $GIT_URL|awk -F'//' '{print $2}' ''', returnStdout: true).trim()

        sh '''
            git push https://$GITHUB_TOKEN@$gitUrlNoProtocol HEAD:main
        '''
    }
}
env.AWS_CLI_CRED_ID