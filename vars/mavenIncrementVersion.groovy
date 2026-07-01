void call() {
    // Bump up current release to upper minor with -SNAPSHOT suffix
    sh '''
        podman run --rm -v jenkins_home:/app -w /app/workspace/$JOB_NAME \
        -v dot_m2_repository:/root/.m2/repository:Z $MAVEN_IMG \
        mvn build-helper:parse-version versions:set -q -DnewVersion='\$'{parsedVersion.majorVersion}.'\$'{parsedVersion.nextMinorVersion}.0-SNAPSHOT versions:commit
    '''
}
