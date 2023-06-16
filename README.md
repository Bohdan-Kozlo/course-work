# Interactive-Advertising-Panel-API

The Interactive Advertising Panel API is a REST API that provides the ability to receive and transmit information about interactive advertising panels in supermarkets, information about the supermarket and about advertising videos.

## Installation

To install and set up the project from GitHub, follow these steps:
1.Clone the repository to your local machine using the following command:
git clone https://github.com/Bohdan-Kozlo/course-work.git
2.Open project in your IDE
3.Build project with command : mvn clean install
4.To run the code, run the CourseworkApplication class
5.The application will start on this host `http://localhost:8080`

## Usage

The following HTTP operations are available in this application:
1.GET - returns all models
2.GET/id - returns object by id
3.POST - creates a new object
4.PUT/id - updates an already existing object
5.DELETE/id - deletes an existing object

## Data storage
By default, the application stores all CSV files in the path `src/main/resourcesCSVFiles`, to change the file storage path change the PATH_TO_FILES change in the classes.
