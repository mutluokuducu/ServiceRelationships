# Service Relationships
 
## Running it locally from Intellij

## Local csv file path
```
csvfile/ServiceModel.csv
```

## AWS S3 csv file path
```
https://servicemodelcsv.s3.eu-west-2.amazonaws.com/ServiceModel.csv
```

## Clean build whole project from command line
```
./gradlew clean build
```

## Check that it is running local
```
http://localhost:8080/swagger-ui.html
```

## Check that it is running google cloud GKE
```
http://35.188.214.219/swagger-ui.html
```

## Project Structure

### Testing
JUnit 5 and JUnit 4 configured. To test the service the following command is used from the root project.

## Running unit tests from command line
```
./sbuild.sh
```
## Running component tests from command line
```
./cbuild.sh
```
## Running all tests from command line
```
./buildAll.sh
```


