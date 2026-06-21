void call() {
    sh '''
        podman run --rm -v jenkins_home:/app -w /app/workspace/$JOB_NAME $MAVEN_IMG mvn build-helper:parse-version versions:set -q -DnewVersion='\$'{parsedVersion.majorVersion}.'\$'{parsedVersion.nextMinorVersion}.0-SNAPSHOT versions:commit
    '''
}
