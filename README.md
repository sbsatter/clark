How to compile and run this project
===================================

Execute the following commands:
```sh
./mvnw clean package
```
This may take a while to download maven if not already installed.

When the above process is completed, execute the following to run the application:
```sh
java -jar ./target/event-processor-0.0.1-SNAPSHOT.jar --db.user=<username> --db.password=<password> --db.url=<url if necessary>
```

You may alter the choice of database by replacing the db.url flag above. In the code, it is assumed to be mysql.

That is it. For any issues, please feel free to reach out to me!
