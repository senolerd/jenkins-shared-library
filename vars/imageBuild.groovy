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

    def lovercaseAppName = APP_NAME.toLowerCase()

    sh """
        cd target
        echo "DEST_CONTAINER_REPO: ${DEST_CONTAINER_REPO}"
        echo "DEST_CONTAINER_REGISTRY: ${DEST_CONTAINER_REGISTRY}"
        echo "lovercaseAppName: ${lovercaseAppName}"
        echo "APP_VER: ${APP_VER}"
        podman build -t ${DEST_CONTAINER_REGISTRY}/${lovercaseAppName}:${APP_VER} .
        mv Containerfile ../
    """
}