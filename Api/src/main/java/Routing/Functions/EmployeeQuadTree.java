package Routing.Functions;

import java.util.ArrayList;
import com.esri.core.geometry.Envelope2D;
import com.esri.core.geometry.QuadTree;

import Routing.models.Employee;

public class EmployeeQuadTree {

    Envelope2D map = new Envelope2D(77.0018, 12.6859, 78.3215, 13.4591);
    QuadTree quadTree;
    ArrayList<Employee> emps;

    public EmployeeQuadTree(ArrayList<Employee> emps) {
        this.quadTree = new QuadTree(map, emps.size() + 10);
        this.emps = emps;
    }

    public void constructTree() {
        for (Employee e : this.emps) {
            this.quadTree.insert(e.id,
                    new Envelope2D(e.location.getX(), e.location.getY(), e.location.getX() + 1, e.location.getY() + 1));
        }
    }

    public QuadTree getEmployeeQuadTree(){
        return this.quadTree;
    }
}