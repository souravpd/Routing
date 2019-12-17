package Routing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Routing.Functions.AssignBusStops;
import Routing.Functions.BusStopList;
import Routing.Functions.EmployeeList;
import Routing.models.BusStop;
import Routing.models.Employee;
import Routing.models.Point;
import Routing.models.Rectangle;
import Routing.models.Tuple;

public class App {
    public static void main(String[] args) throws IOException {
        EmployeeList empList = new EmployeeList();
        empList.readEmployeeData(new File("employees.txt"), 9);
        ArrayList<Employee> employees = empList.getEmployees();
        BusStopList bStopList = new BusStopList();
        bStopList.readBusStopData(new File("busStops.txt"), 4);
        ArrayList<BusStop> busStops = bStopList.getBusStops();
        System.out.println("Employees");
        for (Employee e : employees) {
            System.out.println(e.name);
        }
        System.out.println("Bus Stops");
        for (BusStop b : busStops) {
            System.out.println(b.name);
        }
        AssignBusStops assign = new AssignBusStops(employees, busStops);
        assign.constructQuadTree();
        assign.RangeQueries();
        System.out.println("Range Queries");
        for (Employee e : employees) {
            e.print();
        }
        System.out.println("Removing Extra BusStops");
        assign.RemoveExtraBusStops();
        for (Employee e : employees) {
            e.print();
        }
        HashMap<Employee , Point> hm = new HashMap<Employee , Point>();
        ArrayList<Tuple> tuple = new ArrayList<Tuple>();
        hm = assign.EmployeeQuadTree.query(new Rectangle(0, 0, 1000, 1000));
        for(Employee e : hm.keySet()){
            tuple.add(new Tuple(e , hm.get(e)));
        }
        for (Tuple t : tuple) {
            System.out.println(t.e.name);
        }
    }
}