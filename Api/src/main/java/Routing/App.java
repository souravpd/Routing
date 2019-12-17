package Routing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Routing.Functions.AssignBusStops;
import Routing.Functions.BusStopList;
import Routing.Functions.EmployeeList;
import Routing.models.BusStop;
import Routing.models.Employee;


public class App {
    public static void main(String[] args) throws IOException {
        EmployeeList empList = new EmployeeList();
        empList.readEmployeeData(new File("employees.txt"), 45);
        ArrayList<Employee> employees = empList.getEmployees();
        BusStopList bStopList = new BusStopList();
        bStopList.readBusStopData(new File("busStops.txt"), 8);
        ArrayList<BusStop> busStops = bStopList.getBusStops();
        System.out.println();
        System.out.println("Employees Loaded And Geocoded");
        for (Employee e : employees) {
            System.out.println(e.name);
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Bus Stops Loaded and Geocoded");
        for (BusStop b : busStops) {
            System.out.println(b.name);
        }
        System.out.println();
        System.out.println();
        System.out.println();
        AssignBusStops assign = new AssignBusStops(employees, busStops);
        assign.constructQuadTree();
        assign.RangeQueries();
        assign.HandleEmployeesNotAssignedBusStops();
        for(BusStop b : busStops){
            System.out.println("Name : " + b.name);
            System.out.println("Lat :" + b.latitude);
            System.out.println("Long : " + b.longitude);
            System.out.println("Demand : " + b.demand);
            System.out.println("Employees before the Removal");
            for(Employee e : b.EmployeeList){
                System.out.println(e.name);
            }
            System.out.println();
            System.out.println();
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Removing Extra BusStops");
        assign.RemoveExtraBusStops();
        for (Employee e : employees) {
            e.print();
        }
        System.out.println();
        System.out.println();
        System.out.println();
        ArrayList<BusStop> newBusStopList = assign.newBusStopList;
        for(BusStop b : newBusStopList){
            System.out.println("Name : " + b.name);
            System.out.println("Lat :" + b.latitude);
            System.out.println("Long : " + b.longitude);
            System.out.println("Demand : " + b.demand);
            System.out.println("Employees after the Removal");
            for(Employee e : b.EmployeeList){
                System.out.println(e.name);
            }
            System.out.println();
            System.out.println();
            System.out.println();
        }
    }
}