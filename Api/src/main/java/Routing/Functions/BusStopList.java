package Routing.Functions;

import java.io.*;
import java.util.*;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.esri.core.geometry.Point;

import Routing.models.BusStop;

public class BusStopList {
    ArrayList<BusStop> busStops = new ArrayList<BusStop>();
    JOpenCageGeocoder jOpenCageGeocoder;

    BusStopList() {
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
            request.setBounds(18.367, -34.109, 18.770, -33.704);
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
}