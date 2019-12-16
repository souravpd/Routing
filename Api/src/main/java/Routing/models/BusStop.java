package Routing.models;

import java.util.ArrayList;
import Routing.models.Point;

public class BusStop {
    public int id;
    public String address;
    public String name;
    public Point location;
    public boolean isUsed = false;
    public int Capacity = 32;
    public int demand = 0;
    public ArrayList<Employee> EmployeeList = new ArrayList<Employee>();

    public BusStop(int id, Point p, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.location = p;
    }
}