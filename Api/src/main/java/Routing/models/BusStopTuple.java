package Routing.models;

import java.util.Comparator;

public class BusStopTuple {
    public BusStop b;
    public Point p;
    public double distFromEmployee;

    public BusStopTuple(BusStop b, Point p, double distFromEmployee) {
        this.b = b;
        this.p = p;
        this.distFromEmployee = distFromEmployee;
    }

    public static Comparator<BusStopTuple> BusStopTupleComparator = new Comparator<BusStopTuple>() {

        public int compare(BusStopTuple t1, BusStopTuple t2) {
            double dist1 = t1.distFromEmployee;
            double dist2 = t2.distFromEmployee;
            if (dist1 > dist2)
                return 1;
            if (dist2 > dist1)
                return -1;
            else
                return 0;
        }
    };
}