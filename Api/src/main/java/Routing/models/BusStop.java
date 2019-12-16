package Routing.models;

import java.util.ArrayList;

import com.esri.core.geometry.Point;

public class BusStop {
    int id;
    String address;
    String name;
    Point location;
    boolean isUsed = false;
    int Capacity;
    int demand;
    ArrayList<Employee> EmployeeList = new ArrayList<Employee>();

    public BusStop(int id , Point p , String name , String address ){
        this.id = id;
        this.name= name;
        this.address = address;
        this.location = p;
    }
}