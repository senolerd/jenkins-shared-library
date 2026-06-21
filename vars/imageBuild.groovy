void call() {

    // ToDo: Change hard coded image name to variable
    sh '''
        cd target
        JAR_FILE=$(ls *.jar)

        cat <<-EOF > Containerfile
        FROM docker.io/library/eclipse-temurin:17-jre-jammy
        WORKDIR /app
        COPY $JAR_FILE .
        CMD java -jar $JAR_FILE
    '''

    sh """
        cd target
        podman build -t $DEST_REPO/java-maven:${APP_VER} .
        mv Containerfile ../
    """
}
