cd /tmp
aws s3api list-objects --bucket cfn-ap-south-1-test-pyl-02-artifactstorebucket-gsrqbz6axwzl --prefix cpl-ap-south-1-test-/MyAppBuilt/ --output json | jq '.Contents | sort_by(.LastModified) | reverse | .[0].Key | split("/") | last | @text'>codepipeline
MY_VAR=$(head -n 1 codepipeline | awk '{gsub(/"/, ""); print}')
export MY_VAR
aws s3 cp s3://cfn-ap-south-1-test-pyl-02-artifactstorebucket-gsrqbz6axwzl/cpl-ap-south-1-test-/MyAppBuilt/$MY_VAR .
unzip -o -d /tmp/testing/ $MY_VAR
mv /tmp/testing/target/payroll-0.0.1-SNAPSHOT.war /opt/tomcat/apache-tomcat-10.1.17/webapps/payroll-kt.war
rm -rf /tmp/testing/*
rm -rf /tmp/$MY_VAR