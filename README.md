# Car Park Assignment

### Overview
Application keeps track of number of available parking slot. Return appropriate message when a car 
tries to acquire a parking slot depending upon availability. It calculate the parking charges when 
car is leaving.

### Requirements & Assumptions
1. Initial parking capacity is considered as 100. It will be set through config in variable.
2. 

### Libraries & Versions
1. JDK 16
2. Spring boot 2.7
3. json-20230618.jar
4. junit-jupiter-5.8.2.jar

### Design
#### Entities:
![img_1.png](img_1.png)

#### APIs and REST endpoints

1. _**bookParkingSlot(registration)**_: This REST endpoint will book a parking slot if available. 
This method first check if the parking is full or not and return appropriate message. 
To handle multiple requests it will use lock api while checking the slot availability. 
Reduce the number of available space by 1 and add the car with parking details like time.
The entire request is carried out in the same transaction to handle rollback.
2. _**calaculteParkingCharges(registration)**_: This REST endpoint will calculate charges based on 
per hour charges and duration calculated based on current date/time and date/time at the time
of booking parking slot. It will increase the available parking slot by 1. Everything will be 
executed in the same transaction. 


### Guidelines
Application Run:
1. Clone the repo. which already has jar file created which can be run separately.
2. To run application execute below command from target folder:
   1. java -jar JPWordCountAssignment-1.0-SNAPSHOT-jar-with-dependencies.jar <FULLY_QUALIFIED_FILE_PATH>
3. There are tests written which can be run to validate the different scenarios including invalid ones.
4. While running the application the argument needs to be passed which should be the path of file which
needs to be processed.
5. The application accept file path and produces outputs as below:
   1. If the path sent as argument is empty or invalid appropriate message is displayed.
   2. If path is valid, Application loads the file and processes it line by line. Filters out characters apart from
   lower and upper letters and numbers, stores in map and prints out as json.
6. Parallel stream api is used to achieve parallel processing and Concurrent hashmap is used to avoid race condition.

### Testing
Test cases covers below scenarios:
1. File path is empty or invalid. Asserts if appropriate message is printed.
2. Asserts if the correct number of words are extracted from the file.
3. Tests if specific words with count are captured and special characters are not present in printed output.

### Result Screenshots & Evidences 
1.Running application with valid input (https://www.gnu.org/licenses/gpl-3.0.txt)
