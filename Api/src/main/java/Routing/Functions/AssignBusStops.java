package Routing.Functions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import Routing.models.BusStop;
import Routing.models.Employee;
import Routing.models.Point;
import Routing.models.QuadTree;
import Routing.models.Rectangle;
import Routing.models.Tuple;

public class AssignBusStops {
    public ArrayList<Employee> employees = new ArrayList<Employee>();
    public ArrayList<BusStop> busStops = new ArrayList<BusStop>();
    public QuadTree EmployeeQuadTree = new QuadTree(new Rectangle(0, 0, 1000, 1000), 4);

    public AssignBusStops(ArrayList<Employee> emp, ArrayList<BusStop> busStops) {
        this.employees = emp;
        this.busStops = busStops;
    }

    public void constructQuadTree() {
        for (Employee e : this.employees) {
            EmployeeQuadTree.insert(e, new Point(e.x , e.y));
        }
    }

    public void RangeQueries() {
        for (BusStop b : this.busStops) {
            HashMap<Employee , Point> hm = new HashMap<Employee , Point>();
            ArrayList<Tuple> result = new ArrayList<Tuple>();
            hm = this.EmployeeQuadTree.query(new Rectangle(b.x , b.y , 1 ,1));
            for(Employee e : hm.keySet()){
                result.add(new Tuple(e , hm.get(e)));
            }
            Collections.sort(result, Tuple.TupleComparator);
            for (Tuple t : result) {
                Employee e = t.e;
                if (e.isAssignedBusStop == false) {
                    if (b.demand < 0.5 * b.Capacity) {
                        e.BusStop = b;
                        b.isUsed = true;
                        e.isAssignedBusStop = true;
                        b.demand += 1;
                        b.EmployeeList.add(e);
                    }
                }
            }
        }
    }

    public void RemoveExtraBusStops() {
        ArrayList<BusStop> usedBusStops = new ArrayList<BusStop>();
        for (BusStop b : this.busStops) {
            if (b.isUsed) {
                usedBusStops.add(b);
            }
        }
        Collections.sort(usedBusStops, BusStop.BusStopComparator);
        for (int i = usedBusStops.size() - 1; i >= 1; i--) {
            BusStop b = usedBusStops.get(i);
            if (b.demand < 0.15 * b.Capacity) {
                BusStop b1 = usedBusStops.get(i - 1);
                if ((b1.demand + b.demand < 0.85 * b1.Capacity)) {
                    b.isUsed = false;
                    b.demand = 0;
                    b1.EmployeeList.addAll(b.EmployeeList);
                    for (Employee e : b.EmployeeList) {
                        e.BusStop = b1;
                    }
                    b.EmployeeList.clear();
                }
            }
        }
    }
}