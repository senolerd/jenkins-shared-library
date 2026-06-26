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

    def loverProjectName = AWS_PROJECT_NAME.toLowerCase()

    sh """
        cd target
        echo "DEST_CONTAINER_REPO: ${DEST_CONTAINER_REPO}"
        echo "loverProjectName: ${loverProjectName}"
        echo "APP_VER: ${APP_VER}"
        podman build -t ${DEST_CONTAINER_REGISTRY}/${loverProjectName}:${APP_VER} .
        mv Containerfile ../
    """
}