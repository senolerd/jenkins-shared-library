void call(Map args) {
    // Expecets args to be a map with the following keys:
    // args = [
    //     containerRegisttryCredId: 'dockerhub-cred',
    //     registryAddr: 'docker.io',
    // ]

    withCredentials([usernamePassword(credentialsId: args.dockerCredID,
                    usernameVariable: 'USER', passwordVariable: 'PASS')]) {
            sh 'podman login -u $USER -p $PASS ${args.registryAddr}' // Sec Sec Sec
        }
}
