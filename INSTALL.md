### Setup

- Make you have installed Java 21 or higher.
- PostGreSQL
- Gradle
- RabbitMQ Server
- MQTT Plugin for RabbitMQ

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/attendance-system-api.git
2. Navigate to the project directory:
   ```
   cd attendance-system-api
   ```
3. Import the database configuration at `src > main > resources > db > import.sql`
4. **Optionally**, you can import mock data at `src > main > resources > db > mock_data`
5. Edit spring boot configuration (application.properties) at **src > main > resources > application.properties**
    - Edit connection string, username, password to yours.
    - **Recommended:** Edit jwt.secret with a secure SHA512 hash and the JWT Secret should not be less than 512 random characters and/or symbols.
    - **NOTE**: If you change the api.root, also change springdoc.paths-to-match to be the same as the root.
    - You can edit the time for being late and flag ceremony.
6. Now build the application with this command:
   ```
   ./gradlew build -x test
   // The built JAR file can be found at build/libs/attendance-system-api-springboot-VERSION.jar
   ```
7. Navigate to the folder where the jar file is created
   ```
   cd build/libs
   ```
8. Run the application with:
   ```
   java -jar attendace-system-api-springboot-VERSION.jar
   ```
   Replace a version with the version of the application you have cloned or downloaded.
9. Access the API at
   ```
   http://localhost:8080
   ```
   in my case, it is configured to listen on Port 8080  
   Additionally, you will need to install **RabbitMQ Server** with the enabled **MQTT** plugin.
10. For installing RabbitMQ Server, you can follow the instructions provided in the official documentation: [RabbitMQ Installation Guide](https://www.rabbitmq.com/docs/download)
11. After installing RabbitMQ Server, enable the MQTT plugin by running the following command:
    ```
    rabbitmq-plugins enable rabbitmq_mqtt
    ```
12. Start the RabbitMQ Server by running the following command:
    ```
    rabbitmq-server
    // or by enabling the RabbitMQ service
    systemctl start rabbitmq-server
    ```
13. The default credentials for RabbitMQ are:
    ```
    Default username: guest
    Default password: guest
    ```
14. You can only run the application after setting the Environment Variables required of the server to restart:
    ```
    DB_HOST=localhost
    DB_PASSWORD=<POSTGRES_PASSWORD>
    DB_PORT=<POSTGRES_PORT DEFAULT 5432>
    DB_USERNAME=<POSTGRES_USERNAME>
    RABBITMQ_PASSWORD=guest
    RABBITMQ_USERNAME=guest
    ```