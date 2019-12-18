package Routing.Functions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import Routing.models.BusStop;
import Routing.models.Employee;

public class GenerateVRPFile {

    public ArrayList<BusStop> busStopList = new ArrayList<BusStop>();

    public GenerateVRPFile(ArrayList<BusStop> busStopList) {
        this.busStopList = busStopList;
    }

    public void writeToFile() throws IOException {

        File fout = new File("data.vrp");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        String Line1 = "DIMENSION : " + this.busStopList.size();
        bw.write(Line1);
        bw.newLine();
        String Line2 = "CAPACITY : " + this.busStopList.get(0).Capacity;
        bw.write(Line2);
        bw.newLine();
        String Line3 = "NODE_COORD_SECTION";
        bw.write(Line3);
        bw.newLine();
        String Line4 = "1 77.4238889 12.79694444 ";
        bw.write(Line4);
        bw.newLine();

        for (int i = 0; i < this.busStopList.size(); i++) {
            bw.write(i + 1 + " " + this.busStopList.get(i).longitude + " " + this.busStopList.get(i).latitude + " ");
            bw.newLine();
        }

        String Line5 = "DEMAND_SECTION";
        bw.write(Line5);
        bw.newLine();
        String Line6 = "1 0";
        bw.write(Line6);
        bw.newLine();

        for (int i = 0; i < this.busStopList.size(); i++) {
            bw.write(i + 2 + " " + this.busStopList.get(i).demand + " ");
            bw.newLine();
        }

        bw.close();
    }

    public void GenerateBusStopData() throws IOException {

        File fout = new File("BusStopData.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        for (int i = 0; i < this.busStopList.size(); i++) {
            String data = "Name: " + this.busStopList.get(i).name + "," + "Longitude: "
                    + this.busStopList.get(i).longitude + "," + "Latitude: " + this.busStopList.get(i).latitude
                    + ",Address: " + this.busStopList.get(i).address;
            bw.write(data);
            bw.newLine();
            for(Employee e : this.busStopList.get(i).EmployeeList){
                bw.write("Name: "+e.name+",Longitude: "+e.longitude+",Latitude: "+e.latitude+",Address: "+e.address);
                bw.newLine();
            }
            bw.newLine();
        }
        bw.close();
    }
}