package Routing.Functions;

import java.util.ArrayList;
import com.esri.core.geometry.Envelope2D;
import com.esri.core.geometry.QuadTree;

import Routing.models.BusStop;

public class BusStopQuadTree {

    Envelope2D map = new Envelope2D(77.0018, 12.6859, 78.3215, 13.4591);
    QuadTree quadTree;
    ArrayList<BusStop> busStops;

    public BusStopQuadTree(ArrayList<BusStop> bStops) {
        this.quadTree = new QuadTree(map, bStops.size() + 10);
        this.busStops = bStops;
    }

    public void constructTree() {
        for (BusStop b : this.busStops) {
            this.quadTree.insert(b.id,
                    new Envelope2D(b.location.getX(), b.location.getY(), b.location.getX() + 10, b.location.getY() + 10));
        }
    }

    public QuadTree getBusStopQuadTree(){
        return this.quadTree;
    }
}