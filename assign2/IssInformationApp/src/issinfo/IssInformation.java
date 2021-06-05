package issinfo;

import issinfo.preview.Location;
import java.util.*;
import java.util.function.Function;
import static java.util.stream.Collectors.*;

public class IssInformation {
  private IssTimeService issTimeService;

  public void setIssTimeService(IssTimeService service) {
    issTimeService = service;
  }

  public Map<Location, String> getFlyOverTimeForLocations(List<Location> locations) {
    return locations.stream()
      .collect(toMap(Function.identity(), this::getFlyOverTimeConvertingException));
  }

  private String getFlyOverTimeConvertingException(Location location) {
    try {
      return issTimeService.getTimeForLocation(location);
    }catch(Exception e) {
      return e.getMessage();
    }
  }
}