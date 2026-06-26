void call() {
    sh '''
        cd target
        JAR_FILE=$(ls *.jar)

        cat <<-EOF > Containerfile
        FROM $BUILD_IMG
        WORKDIR /app
        COPY $JAR_FILE .
        CMD java -jar $JAR_FILE
    '''

    sh """
        cd target
        ls -al
        cat Containerfile
        echo "=================================="
        echo "=================================="
        podman build -t $ECR_REPO/java-maven:${env.APP_VER} .
        mv Containerfile ../
    """
}