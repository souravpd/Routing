package Routing.models;

import java.util.Comparator;

public class Dictionary {
    public BusStop b;
    public Point p;
    public double distFromBusStop;

    public Dictionary(BusStop b, Point p, double distFromBusStop) {
        this.b = b;
        this.p = p;
        this.distFromBusStop = distFromBusStop;
    }

    public static Comparator<Dictionary> DictionaryComparator = new Comparator<Dictionary>() {

        public int compare(Dictionary t1, Dictionary t2) {
            double dist1 = t1.distFromBusStop;
            double dist2 = t2.distFromBusStop;
            if (dist1 > dist2)
                return 1;
            if (dist2 > dist1)
                return -1;
            else
                return 0;
        }
    };
}