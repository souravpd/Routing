# Routing

This is a Maven Project for the Pre processing of the Data Provided during the BOSCH's Route Optimization Project

## Implementation

We use the Quad Tree Data Structure to Query the Number of Employees Falling in the Region of the Bus Stop Rectangle and then assign them to the Bus Stops while paying attention to the constraints like
1. No Bus Stop Is Filled Fully
2. No Bus Stop is Used by very less number of people

Finally We Generate the Data.vrp File and Distance and Durations Matrix for feeding to the VRP Solver

# Dependencies

- Json-Simple
- JOpenCage Geocoder
- Open Routing Service
- Jersey

# Usage

- This is a Maven Project
- the code is in the API Folder
- In the Data Folder There are two files namely busStops.txt and employees.txt, Using that format create csv files and put them in the API directory of the project
- In the App.java change the name of the files and the locations (if changed) and the number of entries to be processed
- Run the Project 
- Finally 3 New Files will be Generated

###  :coffee: Please Feel Free to Contribute and suggest newer changes
