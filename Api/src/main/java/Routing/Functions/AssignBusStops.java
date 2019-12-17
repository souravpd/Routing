package Routing.Functions;

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

public class AssignBusStops {
    public ArrayList<Employee> employees = new ArrayList<Employee>();
    public ArrayList<BusStop> busStops = new ArrayList<BusStop>();
    public QuadTree EmployeeQuadTree = new QuadTree(new Rectangle(0, 0, 1000, 1000), 4);
    public BusStopQuadTree bQuadTree = new BusStopQuadTree(new Rectangle(0, 0, 1000, 1000), 4);
    public ArrayList<BusStop> newBusStopList = new ArrayList<BusStop>();

    public AssignBusStops(ArrayList<Employee> emp, ArrayList<BusStop> busStops) {
        this.employees = emp;
        this.busStops = busStops;
    }

    public void constructQuadTree() {
        for (Employee e : this.employees) {
            EmployeeQuadTree.insert(e, new Point(e.x, e.y));
        }
        for (BusStop b : this.busStops) {
            bQuadTree.insert(b, new Point(b.x, b.y));
        }
    }

    public void RangeQueries() {
        for (BusStop b : this.busStops) {
            HashMap<Employee, Point> hm = new HashMap<Employee, Point>();
            ArrayList<Tuple> result = new ArrayList<Tuple>();
            hm = this.EmployeeQuadTree.query(new Rectangle(b.x, b.y, 2, 2));
            System.out.println();
            System.out
                    .println("Found These Employees for This Bus Stop in Sorted Order based on Distances : " + b.name);
            for (Employee e : hm.keySet()) {
                result.add(new Tuple(e, hm.get(e), calculateDistance(e, b)));
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
                        e.distanceToNearestBusStop = calculateDistance(e, b);
                    }
                } else if (e.isAssignedBusStop == true && e.distanceToNearestBusStop < calculateDistance(e, b)
                        && b.demand < Math.ceil(0.5 * b.Capacity)) {
                    BusStop previousBusStop = e.BusStop;
                    previousBusStop.demand -= 1;
                    previousBusStop.EmployeeList.remove(e);
                    e.BusStop = b;
                    b.isUsed = true;
                    e.isAssignedBusStop = true;
                    b.demand += 1;
                    b.EmployeeList.add(e);
                    e.distanceToNearestBusStop = calculateDistance(e, b);
                }
            }
        }
    }

    public void HandleEmployeesNotAssignedBusStops() {
        for (Employee e : this.employees) {
            if (e.isAssignedBusStop == false) {
                HashMap<BusStop, Point> hm = new HashMap<BusStop, Point>();
                ArrayList<BusStopTuple> result = new ArrayList<BusStopTuple>();
                hm = this.bQuadTree.query(new Rectangle(e.x, e.y, 5, 5));
                for (BusStop b : hm.keySet()) {
                    result.add(new BusStopTuple(b, hm.get(b), calculateDistance(e, b)));
                }
                Collections.sort(result, BusStopTuple.BusStopTupleComparator);
                for (BusStopTuple t : result) {
                    BusStop b = t.b;
                    e.BusStop = b;
                    b.isUsed = true;
                    e.isAssignedBusStop = true;
                    b.demand += 1;
                    b.EmployeeList.add(e);
                    e.distanceToNearestBusStop = calculateDistance(e, b);
                    break;
                }
            }
        }
    }

    public static double calculateDistance(Employee e, BusStop b) {
        double xBusStop = b.x;
        double yBusStop = b.y;
        double xE = e.x;
        double yE = e.y;
        return Math.sqrt((xBusStop - xE) * (xBusStop - xE) + (yBusStop - yE) * (yBusStop - yE));
    }

    public void RemoveExtraBusStops() {
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
                    d.add(new Dictionary(busStop, hm.get(busStop), calculateDistanceBetweenBusStops(b, busStop)));
                }
                Collections.sort(d, Dictionary.DictionaryComparator);
                for (Dictionary dict : d) {
                    BusStop BUSSTOP = dict.b;
                    if (BUSSTOP.isUsed == true && BUSSTOP.demand < Math.ceil(0.625 * BUSSTOP.Capacity)) {
                        for (int j = 0 ; j < emps.size() ; j++){
                            Employee e = emps.get(j);
                            if (calculateDistance(e, BUSSTOP) < 2 * e.distanceToNearestBusStop
                                    && BUSSTOP.demand < Math.ceil(0.625 * BUSSTOP.Capacity)) {
                                b.demand -= 1;
                                b.EmployeeList.remove(e);
                                e.BusStop = BUSSTOP;
                                e.distanceToNearestBusStop = calculateDistance(e, BUSSTOP);
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

    public static double calculateDistanceBetweenBusStops(BusStop b1, BusStop b2) {
        double xBusStop = b1.x;
        double yBusStop = b1.y;
        double xE = b2.x;
        double yE = b2.y;
        return Math.sqrt((xBusStop - xE) * (xBusStop - xE) + (yBusStop - yE) * (yBusStop - yE));

    }
}
