package Routing.models;

import java.util.ArrayList;

public class BusStop {
    final private static double R_MAJOR = 6378137.0;
    final private static double R_MINOR = 6356752.3142;
    public int id;
    public String address;
    public String name;
    public double latitude;
    public double longitude;
    public boolean isUsed = false;
    public int Capacity = 32;
    public int demand = 0;
    public boolean isDepot = false;
    public double x;
    public double y;
    public double referenceX = 8618787.888;
    public double referenceY = 1436543.088;
    public ArrayList<Employee> EmployeeList = new ArrayList<Employee>();

    public BusStop(int id, double longitude, double latitude, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.calculateX();
        this.calculateY();
    }
    public void calculateX() {
        this.x = (R_MAJOR * Math.toRadians(this.longitude) - referenceX) / Math.pow(10, 3);
    }

    public void calculateY() {
        if (this.latitude > 89.5) {
            this.latitude = 89.5;
        }
        if (this.latitude < -89.5) {
            this.latitude = -89.5;
        }
        double temp = R_MINOR / R_MAJOR;
        double es = 1.0 - (temp * temp);
        double eccent = Math.sqrt(es);
        double phi = Math.toRadians(this.latitude);
        double sinphi = Math.sin(phi);
        double con = eccent * sinphi;
        double com = 0.5 * eccent;
        con = Math.pow(((1.0 - con) / (1.0 + con)), com);
        double ts = Math.tan(0.5 * ((Math.PI * 0.5) - phi)) / con;
        double y = 0 - R_MAJOR * Math.log(ts);
        this.y = (y - referenceY) / Math.pow(10, 3);
    }
}