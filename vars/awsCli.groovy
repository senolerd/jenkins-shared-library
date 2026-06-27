void call(String cmdStr) {
    // $AWS_KID is the Username and $AWS_KEY is the password of "Username with Password" type of credential.
    // While creating this credential, chosing "Treat username as secret" at the Username will mask the username as well, which is good.

    withEnv(["CMD=${cmdStr}"]) {
        withCredentials([usernamePassword(credentialsId: env.AWS_CLI_CRED_ID, passwordVariable: 'AWS_KEY', usernameVariable: 'AWS_KID')]) {
            int result = sh(script:  '''
                podman run --rm \
                -e AWS_ACCESS_KEY_ID=$AWS_KID \
                -e AWS_SECRET_ACCESS_KEY=$AWS_KEY \
                -e AWS_DEFAULT_REGION=$AWS_REGION \
                docker.io/amazon/aws-cli $CMD

                # docker.io/amazon/aws-cli $CMD > /dev/null 2>&1
                ''', returnStatus: true )

            if (result == 0) {
                echo "[ OK ] '${cmdStr}' is run."
            } else if (result == 252) {
                echo "[ Error${result} ] '${cmdStr}' command is broken."
            } else if (result == 254) {
                echo "[ Error${result} ] '${cmdStr}' command is run, but resource has problem."
            } else {
                echo "[ Error${result} ] '${cmdStr}' something went wrong with this command."
            }

            return result
        }
    }
}
