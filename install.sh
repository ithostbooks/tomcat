cd /tmp
aws s3api list-objects --bucket codepipeline-us-east-1-128035029160 --prefix tomcat-pipeline/BuildArtif/ --output json | jq '.Contents | sort_by(.LastModified) | reverse | .[0].Key | split("/") | last | @text'>codepipeline
MY_VAR=$(head -n 1 codepipeline | awk '{gsub(/"/, ""); print}')
export MY_VAR
aws s3 cp s3://codepipeline-us-east-1-128035029160/tomcat-pipeline/BuildArtif/$MY_VAR .
unzip -o -d /tmp/testing/ $MY_VAR
mv /tmp/testing/target/HB-neobank-IND-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps/hb-neobank.war
rm -rf /tmp/testing/*
rm -rf /tmp/$MY_VAR
