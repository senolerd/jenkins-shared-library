void call(String cmdStr) {
    // $AWS_KID is the Username and $AWS_KEY is the password of "Username with Password" type of credential.
    // While creating this credential, chosing "Treat username as secret" at the Username will mask the username as well, which is good.
    
    echo "AWS CLI will run with: $cmdStr"

    withCredentials([usernamePassword(credentialsId: 'aws_devops-user-1_access_k_id_and_key', passwordVariable: 'AWS_KEY', usernameVariable: 'AWS_KID')]) {
        sh '''
            podman run -i --rm  \
            -e AWS_ACCESS_KEY_ID=$AWS_KID \
            -e AWS_SECRET_ACCESS_KEY=$AWS_KEY \
            -e AWS_DEFAULT_REGION=$AWS_REGION \
            docker.io/amazon/aws-cli $cmdStr
        '''
    }
}
