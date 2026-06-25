void call(appName) {
    // Checks projects AWS ECR repo. If doens't exist, creates it.
    // String repoName = "${AWS_PROJECT_NAME.toLowerCase()}"

    if ( awsCli("ec2 describe-vpcs --filter Name=tag:Name,Values=${appName}") != 0) {
        // awsCli("ec2 create-vpc --cidr-block 10.0.0.0/16 --tag-specifications='ResourceType=vpc,Tags=[{Key=Name,Value=${appName}}]' ")
        echo "VPC ${appName} is created."
    } else {
        def vpcCount = awsCli("ec2 describe-vpcs --filter Name=tag:Name,Values=${appName}").size()
        echo "Length of the list: ${vpcCount} "
        echo "VPC ${appName} is exist."
    }
}
