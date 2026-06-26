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
        podman build -t $ECR_REPO/java-maven:${APP_VER} .
        mv Containerfile ../
    """
}