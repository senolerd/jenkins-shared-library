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
        podman build -t ${lovercaseAppName}:${APP_VER} .
        podman image prune -f
        mv Containerfile ../
    """
}
