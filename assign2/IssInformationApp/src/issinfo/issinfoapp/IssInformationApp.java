package issinfo.issinfoapp;

import issinfo.OpenNotifyService;
import issinfo.preview.Location;
import java.io.*;
import java.util.*;

public class IssInformationApp {
  public static void main(String[] args) throws Exception {
    OpenNotifyService openNotifyService = new OpenNotifyService();
    List<String> listOfLocationsAndTime = new ArrayList<>();
    File file = new File("input.txt");
    Scanner sc = new Scanner(file);

    while(sc.hasNext()) {
      String latitude = sc.next();
      latitude = latitude.substring(0, latitude.length() - 1);
      String longitude = sc.next();
      Location location = new Location(Double.parseDouble(latitude), Double.parseDouble(longitude));

      String time;
      try {
        time = openNotifyService.getFlyoverTime(location);
      }catch(Exception ex) {
        time = ex.getMessage();
      }
      listOfLocationsAndTime.add(latitude + ", " + longitude + ": " + time);
    }

    listOfLocationsAndTime.forEach(System.out::println);
  }
}