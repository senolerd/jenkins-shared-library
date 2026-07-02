void call(String img) {
    // This function assumes image is already built, tagged and logged in to the registry.
        sh "podman push ${img}"
}
