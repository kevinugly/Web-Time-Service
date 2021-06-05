package issinfo;

import issinfo.preview.Location;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class OpenNotifyServiceTest {
  private OpenNotifyService openNotifyService;

  @BeforeEach
  public void init() {
    openNotifyService = new OpenNotifyService();
  }

  @Test
  void getResponseFromURLThatContainsPassesLatitudeAndLongitude() throws Exception {
    var location = new Location(30.2672, -97.7431);
    var response = openNotifyService.getRawResponse(location);

      assertTrue(response.contains("passes"));
      assertTrue(response.contains("latitude"));
      assertTrue(response.contains("longitude"));
  }

  @Test
    void getTimeFromSampleData() {
      var sampleData = """
        {
          "message": "success",
          "request": {
              "altitude": 100,
            "datetime": 1602545966,
            "latitude": 30.2672,
            "longitude": -97.7431,
            "passes": 1
          },
          "response": [
            {
              "duration": 550,
              "risetime": 1602549480
            }
          ]
        }
      """;
      var riseTime = 1602549480;

      assertEquals(riseTime, openNotifyService.parseJSON(sampleData));
    }

  @Test
  void getTimeFromAnotherSampleData() {
    var sampleData = """
      {
        "message": "success",
        "request": {
          "altitude": 100,
          "datetime": 1602546640,
          "latitude": 29.7216,
          "longitude": -95.3436,
          "passes": 1
        },
        "response": [
          {
            "duration": 519,
                "risetime": 1602549522
          }
        ]
      }
    """;
    var riseTime = 1602549522;

    assertEquals(riseTime, openNotifyService.parseJSON(sampleData));
  }

  @Test
  void responseFromParseJSONFromGetRawResponse() throws Exception {
    var location = new Location(30.2672, -97.7431);
    var riseTime = 1602549480;
    var readableHumanDateTime = "12:38AM";
    var sampleData = "blah";

    OpenNotifyService spy = spy(new OpenNotifyService());

    doReturn(sampleData).when(spy).getRawResponse(location);
    doReturn((long) riseTime).when(spy).parseJSON(sampleData);

    var result = spy.getFlyoverTime(location);

    verify(spy).getRawResponse(location);
    verify(spy).parseJSON(sampleData);
    assertEquals(readableHumanDateTime, result);
  }

  @Test
  void getFlyoverTimeThrowsTheExceptionThrownByGetRawResponse() throws Exception {
    var location = new Location(30.2672, -97.7431);
    var exceptionError = "Invalid Location";

    OpenNotifyService spy = spy(new OpenNotifyService());

    doThrow(new Exception(exceptionError)).when(spy).getRawResponse(location);
    Exception exception = assertThrows(RuntimeException.class, () -> spy.getFlyoverTime(location));

    assertEquals("Exception: " + exceptionError, exception.getMessage());
  }

  @Test
  void getFlyoverTimeThrowsTheExceptionThrownByGetParseJSON() throws Exception {
    var location = new Location(30.2672, -97.7431);
    var exceptionError = "Invalid Location";

    OpenNotifyService spy = spy(new OpenNotifyService());

    var sampleData = spy.getRawResponse(location);
    doThrow(new RuntimeException(exceptionError)).when(spy).parseJSON(sampleData);
    Exception exception = assertThrows(RuntimeException.class, () -> spy.getFlyoverTime(location));

    assertEquals("RuntimeException: " + exceptionError, exception.getMessage());
  }
}