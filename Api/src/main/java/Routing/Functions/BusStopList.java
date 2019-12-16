package Routing.Functions;

import java.io.*;
import java.util.*;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;

import Routing.models.BusStop;
import Routing.models.Point;

public class BusStopList {
    public ArrayList<BusStop> busStops = new ArrayList<BusStop>();
    public JOpenCageGeocoder jOpenCageGeocoder;

    public BusStopList() {
        jOpenCageGeocoder = new JOpenCageGeocoder("0631208b38114c70866b848b022b7e65");
    }

    public void readBusStopData(File file, int n) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        int index = 1;
        while (n-- > 0) {
            String vals[] = br.readLine().trim().split(",");
            String name = vals[0];
            String address = vals[1];
            JOpenCageForwardRequest request = new JOpenCageForwardRequest(address);
            request.setRestrictToCountryCode("IN");
            request.setBounds(77.0018, 12.6859, 78.3215, 13.4591);
            request.setLimit(1);
            request.setNoAnnotations(true);

            JOpenCageResponse response = this.jOpenCageGeocoder.forward(request);

            if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
                JOpenCageLatLng coordinates = response.getResults().get(0).getGeometry();
                this.busStops.add(
                        new BusStop(index++, new Point(coordinates.getLng(), coordinates.getLat()), name, address));

            } else {
                System.out.println("Unable to geocode input address: " + address);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<BusStop> getBusStops() {
        return this.busStops;
    }
}