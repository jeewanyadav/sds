# test-battery
it is a task to review code readability.

Project Overview The Spring Boot application implements a REST API with two main endpoints:

Create Batteries: This endpoint allows the client to send a list of batteries with properties including name, postcode, and watt capacity. The data is persisted in an in-memory database.

Find Batteries in Postcode Range: This endpoint accepts a postcode range in the request parameters. It returns a list of battery names that fall within the specified range, sorted alphabetically. Additionally, statistics such as total and average watt capacity are included in the response.

The application uses Java streams to process and filter the data efficiently.

Endpoints: Create Batteries

URL: /batteries

Method: POST

Request Body:

JSON array of batteries with properties: name, postcode, and wattCapacity. Response:

HTTP 201 Created if successful, HTTP 500 Internal Server Error if an error occurs.

Find Batteries in Postcode Range

URL: /batteries/search

Method: GET

Request Parameters:

startPostcode: The starting postcode of the range.

endPostcode: The ending postcode of the range.

page (optional, default: 0): Page number for paginated results.

size (optional, default: 10): Number of results per page.

Response: JSON containing a list of battery names within the specified range, sorted alphabetically. The response also includes statistics such as total and average watt capacity.

Project Structure The project structure follows typical Spring Boot conventions:

src/main/java: Contains the main application code.

src/test/java: Contains unit tests for the application.

src/main/resources: Contains application configuration files.

build.gradle: Gradle project configuration file.

Prerequisites:
Before running the application, ensure that you have the following prerequisites installed:

Java 17 or higher Gradle (for building and running the project)

Running the Application
Follow these steps to run the Spring Boot application:

Clone the repository to your local machine:

git clone https://github.com/jeewanyadav/BatteryTestApp
Navigate to the project directory:

shell

cd batteries

Build and run the application using Gradle:

shell

./gradlew bootRun

The application should now be running locally on http://localhost:8080.

Testing:

The application includes unit tests to ensure its functionality. You can run the tests using the following command:

shell

./gradlew test

The test results will be displayed in the console.
