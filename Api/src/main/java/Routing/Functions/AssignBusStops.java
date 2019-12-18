package Routing.Functions;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import Routing.models.BusStop;
import Routing.models.BusStopQuadTree;
import Routing.models.BusStopTuple;
import Routing.models.Dictionary;
import Routing.models.Employee;
import Routing.models.Point;
import Routing.models.QuadTree;
import Routing.models.Rectangle;
import Routing.models.Tuple;

import Routing.Functions.DistanceCalculator;

public class AssignBusStops {
    public ArrayList<Employee> employees = new ArrayList<Employee>();
    public ArrayList<BusStop> busStops = new ArrayList<BusStop>();
    public QuadTree EmployeeQuadTree = new QuadTree(new Rectangle(0, 0, 1000, 1000), 4);
    public BusStopQuadTree bQuadTree = new BusStopQuadTree(new Rectangle(0, 0, 1000, 1000), 4);
    public ArrayList<BusStop> newBusStopList = new ArrayList<BusStop>();

    public AssignBusStops(ArrayList<Employee> emp, ArrayList<BusStop> busStops)
            throws ParseException, org.json.simple.parser.ParseException {
        this.employees = emp;
        this.busStops = busStops;
        calculateEmployeeDistances(this.employees, this.busStops);
        calculateBusStopDistances(this.busStops);
    }

    public static void calculateEmployeeDistances(ArrayList<Employee> emp , ArrayList<BusStop> bStop )
            throws ParseException, org.json.simple.parser.ParseException {
        for(Employee e : emp){
            for(BusStop b : bStop){
                e.distanceToBusStops.put(b.name , calculateDistance(e, b));
            }
        }
    }

    public static void calculateBusStopDistances(ArrayList<BusStop> bStopList)
            throws org.json.simple.parser.ParseException {
        for(BusStop b : bStopList){
           for(BusStop bStop : bStopList){
               b.distancesToBusStops.put(bStop.name , calculateDistanceBetweenBusStops(b, bStop));
           }
        }
    }

    public void constructQuadTree() {
        for (Employee e : this.employees) {
            EmployeeQuadTree.insert(e, new Point(e.x, e.y));
        }
        for (BusStop b : this.busStops) {
            bQuadTree.insert(b, new Point(b.x, b.y));
        }
    }

    public void RangeQueries() throws ParseException, org.json.simple.parser.ParseException {
        for (BusStop b : this.busStops) {
            HashMap<Employee, Point> hm = new HashMap<Employee, Point>();
            ArrayList<Tuple> result = new ArrayList<Tuple>();
            hm = this.EmployeeQuadTree.query(new Rectangle(b.x, b.y, 2, 2));
            System.out.println();
            System.out
                    .println("Found These Employees for This Bus Stop in Sorted Order based on Distances : " + b.name);
            for (Employee e : hm.keySet()) {
                result.add(new Tuple(e, hm.get(e), e.distanceToBusStops.get(b.name)));
            }
            Collections.sort(result, Tuple.TupleComparator);
            for (Tuple t : result) {
                Employee e = t.e;
                System.out.println(e.name);
                if (e.isAssignedBusStop == false) {
                    if (b.demand < Math.ceil(0.5 * b.Capacity)) {
                        e.BusStop = b;
                        b.isUsed = true;
                        e.isAssignedBusStop = true;
                        b.demand += 1;
                        b.EmployeeList.add(e);
                        e.distanceToNearestBusStop = e.distanceToBusStops.get(b.name);
                    }
                } else if (e.isAssignedBusStop == true && e.distanceToNearestBusStop > e.distanceToBusStops.get(b.name)
                        && b.demand < Math.ceil(0.5 * b.Capacity)) {
                    BusStop previousBusStop = e.BusStop;
                    previousBusStop.demand -= 1;
                    previousBusStop.EmployeeList.remove(e);
                    e.BusStop = b;
                    b.isUsed = true;
                    e.isAssignedBusStop = true;
                    b.demand += 1;
                    b.EmployeeList.add(e);
                    e.distanceToNearestBusStop = e.distanceToBusStops.get(b.name);
                }
            }
        }
    }

    public void HandleEmployeesNotAssignedBusStops() throws ParseException, org.json.simple.parser.ParseException {
        for (Employee e : this.employees) {
            if (e.isAssignedBusStop == false) {
                HashMap<BusStop, Point> hm = new HashMap<BusStop, Point>();
                ArrayList<BusStopTuple> result = new ArrayList<BusStopTuple>();
                hm = this.bQuadTree.query(new Rectangle(e.x, e.y, 5, 5));
                for (BusStop b : hm.keySet()) {
                    result.add(new BusStopTuple(b, hm.get(b), e.distanceToBusStops.get(b.name)));
                }
                Collections.sort(result, BusStopTuple.BusStopTupleComparator);
                for (BusStopTuple t : result) {
                    BusStop b = t.b;
                    e.BusStop = b;
                    b.isUsed = true;
                    e.isAssignedBusStop = true;
                    b.demand += 1;
                    b.EmployeeList.add(e);
                    e.distanceToNearestBusStop = e.distanceToBusStops.get(b.name);
                    break;
                }
            }
        }
    }

    public static double calculateDistance(Employee e, BusStop b)
            throws ParseException, org.json.simple.parser.ParseException {
        return DistanceCalculator.getRouteDistance(e, b);
    }

    public void RemoveExtraBusStops() throws ParseException, org.json.simple.parser.ParseException {
        for (int i = 0; i < this.busStops.size(); i++) {
            BusStop bStop = this.busStops.get(i);
            newBusStopList.add(bStop);
            BusStop b = newBusStopList.get(i);
            if (b.isUsed == true && b.demand < Math.ceil(0.156 * b.Capacity)) {
                ArrayList<Employee> emps = b.EmployeeList;
                ArrayList<Dictionary> d = new ArrayList<Dictionary>();
                HashMap<BusStop, Point> hm = new HashMap<BusStop, Point>();
                hm = this.bQuadTree.query(new Rectangle(b.x, b.y, 5, 5));
                for (BusStop busStop : hm.keySet()) {
                    d.add(new Dictionary(busStop, hm.get(busStop), b.distancesToBusStops.get(busStop.name)));
                }
                Collections.sort(d, Dictionary.DictionaryComparator);
                for (Dictionary dict : d) {
                    BusStop BUSSTOP = dict.b;
                    if (BUSSTOP.isUsed == true && BUSSTOP.demand < Math.ceil(0.625 * BUSSTOP.Capacity)) {
                        for (int j = 0; j < emps.size(); j++) {
                            Employee e = emps.get(j);
                            if (e.distanceToBusStops.get(BUSSTOP.name) < 2 * e.distanceToNearestBusStop
                                    && BUSSTOP.demand < Math.ceil(0.625 * BUSSTOP.Capacity)) {
                                b.demand -= 1;
                                b.EmployeeList.remove(e);
                                e.BusStop = BUSSTOP;
                                e.distanceToNearestBusStop = e.distanceToBusStops.get(BUSSTOP.name);
                                BUSSTOP.demand += 1;
                                BUSSTOP.EmployeeList.add(e);
                                if (b.demand == 0) {
                                    b.isUsed = false;
                                    break;
                                }
                                if (BUSSTOP.demand > Math.ceil(0.625 * BUSSTOP.Capacity)) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public ArrayList<BusStop> getFinalBusStopList() {
        ArrayList<BusStop> finalBusStopList = new ArrayList<BusStop>();
        for (BusStop b : this.newBusStopList) {
            if (b.isUsed == true)
                finalBusStopList.add(b);
        }
        return finalBusStopList;
    }

    public static double calculateDistanceBetweenBusStops(BusStop b1, BusStop b2)
            throws org.json.simple.parser.ParseException {
        return DistanceCalculator.getRouteDistance(b1, b2);

    }
}
