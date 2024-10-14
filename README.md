# Full Stack Currency Converter Application
This full-stack application scrapes forex conversion rates daily, stores the data, and provides a REST API to retrieve various forex data. It is built using Spring Boot (Java) for the backend, React for the frontend, and is hosted using AWS services (S3 and EC2).

* Link to the live website: http://scrapingapplication.s3-website.ap-south-1.amazonaws.com/
* Link to the frontend repository: https://github.com/arora-kapil/currency-converter-frontend

## Features
### 1. Data Scraping
Scrapes forex rates daily at 9 AM from oanda.com (fxds-public-exchange-rates-api)
Supports the scraping of additional currency pairs via API.
### 2. Data Storage
Stores historical forex data in an H2 database.
Easily extendable to other SQL databases (MySQL, PostgreSQL, etc.).
Maintains historical data for all currency pairs.
### 3. REST API Endpoints
Get All Currency Pairs: Retrieve a list of all available currency pairs.
Get Average Conversion Rate: Retrieve the average rate for a currency pair between specified dates.
Get Closing Conversion Rate: Get the closing rate for a currency pair on a specific date.
Add a New Currency Pair: Add a new currency pair to the system for tracking.
Start Tracking a Currency Pair: Enable tracking for a specific currency pair.
Get Tracked Currency Pairs: Retrieve all currency pairs being tracked by the system.
Get Last 90 Days of Data: Retrieve historical data for the past 90 days for a selected currency pair.
### 4. Error Handling and Logging
Implements robust error-handling mechanisms to capture and log important events and exceptions.
### 5. Performance
API response times are optimized to remain under 100ms, ensuring fast and efficient data delivery.
Tech Stack
Backend: Spring Boot (Java)
Frontend: React
Database: H2 (in-memory), extendable to MySQL or PostgreSQL
Hosting: AWS S3 (Frontend) and EC2 (Backend)
## How to Run the Application
### Prerequisites
Java: Version 21+
Node.js: For running the React frontend
SQL Database: H2 (in memory)
AWS: An AWS account with S3 and EC2 setup
Backend (Spring Boot)

### Clone the repository:
git clone https:/github.com/arora-kapil/currency-converter.git
cd forex-scraper-api

### Set up the database:
Modify the application.yaml file to configure your SQL database credentials.
### Build and run the application:
./mvnw spring-boot:run
The backend will be available on http://localhost:8080.
Swagger URL: http://localhost:8080/swagger-ui/index.html

### Frontend (React)
Navigate to the frontend directory:
cd frontend
#### Install dependencies and start the React app:
npm install
npm start
The frontend will be available on http://localhost:3000.

### AWS Deployment
#### Frontend
Hosted on AWS S3 as a static website.
Follow AWS documentation for deploying a React app on S3.
#### Backend
Hosted on AWS EC2.
Set up an EC2 instance, ensuring that port 8080 is open for backend access.
