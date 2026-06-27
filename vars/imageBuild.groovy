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

    String lovercaseAppName = APP_NAME.toLowerCase()

    sh """
        cd target
        podman build -t ${DEST_CONTAINER_REGISTRY}/${lovercaseAppName}:${APP_VER} -v dot_m2_repository:/root/.m2/repository .
        podman image prune -f
        mv Containerfile ../
    """
}
