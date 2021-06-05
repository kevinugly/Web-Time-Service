package issinfo;

import issinfo.preview.Location;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;


public class IssInformationTest {
  private IssInformation issInformation;
  private IssTimeService issTimeService;

  @BeforeEach
  public void init() {
    issInformation = new IssInformation();
    issTimeService = mock(IssTimeService.class);
    issInformation.setIssTimeService(issTimeService);
  }

  @Test
  void canary() {
    assert(true);
  }

  @Test
  void getFlyOverTimeForLocationsWhenNoLocationGiven() {
    assertEquals(Map.of(), issInformation.getFlyOverTimeForLocations(List.of()));
  }

  @Test
  void getFlyOverTimeForLocationsWhenOneLocationGiven() {
    var location = new Location(30.2672, -97.7431);
    var expected = Map.of(location, "11:15PM");

    when(issTimeService.getTimeForLocation(location)).thenReturn("11:15PM");

    assertEquals(expected, issInformation.getFlyOverTimeForLocations(List.of(location)));
  }

  @Test
  void getFlyOverTimeForLocationsWhenTwoLocationsGiven() {
    var location1 = new Location(30.2672, -97.7431);
    var location2 = new Location(29.7216, -95.3436);
    var expected = Map.of(location1, "11:15PM", location2, "11:16PM");

    when(issTimeService.getTimeForLocation(location1)).thenReturn("11:15PM");
    when(issTimeService.getTimeForLocation(location2)).thenReturn("11:16PM");

    assertEquals(expected, issInformation.getFlyOverTimeForLocations(List.of(location1, location2)));
  }

  @Test
  void getFlyOverTimeForLocationsWhenThreeLocationsGiven() {
    var location1 = new Location(30.2672, -97.7431);
    var location2 = new Location(29.7216, -95.3436);
    var location3 = new Location(35.2220, -101.831);
    var expected = Map.of(location1, "11:59PM", location2, "11:16PM", location3, "11:17PM");

    when(issTimeService.getTimeForLocation(location1)).thenReturn("11:59PM");
    when(issTimeService.getTimeForLocation(location2)).thenReturn("11:16PM");
    when(issTimeService.getTimeForLocation(location3)).thenReturn("11:17PM");

    assertEquals(expected, issInformation.getFlyOverTimeForLocations(List.of(location1, location2, location3)));
  }

  @Test
  void getFlyOverTimeForLocationsWhenThreeLocationsGivenButWithInvalidLocationException() {
    var location1 = new Location(30.2672, -97.7431);
    var location2 = new Location(29.7216, -95.3436);
    var location3 = new Location(35.2220, -101.831);
    var expected = Map.of(location1, "11:59PM", location2, "Invalid Location", location3, "11:17PM");

    when(issTimeService.getTimeForLocation(location1)).thenReturn("11:59PM");
    when(issTimeService.getTimeForLocation(location2)).thenThrow(new RuntimeException("Invalid Location"));
    when(issTimeService.getTimeForLocation(location3)).thenReturn("11:17PM");

    assertEquals(expected, issInformation.getFlyOverTimeForLocations(List.of(location1, location2, location3)));
  }

  @Test
  void getFlyOverTimeForLocationsWhenThreeLocationsGivenButWithNetworkErrorException() {
    var location1 = new Location(30.2672, -97.7431);
    var location2 = new Location(29.7216, -95.3436);
    var location3 = new Location(35.2220, -101.831);
    var expected = Map.of(location1, "11:59PM", location2, "11:16PM", location3, "Network Error");

    when(issTimeService.getTimeForLocation(location1)).thenReturn("11:59PM");
    when(issTimeService.getTimeForLocation(location2)).thenReturn("11:16PM");
    when(issTimeService.getTimeForLocation(location3)).thenThrow(new RuntimeException("Network Error"));

    assertEquals(expected, issInformation.getFlyOverTimeForLocations(List.of(location1, location2, location3)));
  }
}