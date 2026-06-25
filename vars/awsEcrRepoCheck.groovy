void call(repoName) {
    // Checks projects AWS ECR repo. If doens't exist, creates it.
    // String repoName = "${AWS_PROJECT_NAME.toLowerCase()}"

    if ( awsCli("ecr describe-repositories --repository-name ${repoName}") != 0) {
        awsCli("ecr create-repository --repository-name ${repoName}")
        echo "Repo ${repoName} is created."
    } else {
        echo "Repo ${repoName} is exist."
    }
}
