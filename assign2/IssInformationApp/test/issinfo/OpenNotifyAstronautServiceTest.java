package issinfo;

import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class OpenNotifyAstronautServiceTest {
  private OpenNotifyAstronautService openNotifyAstronautService;

  @BeforeEach
  public void init() {
    openNotifyAstronautService = new OpenNotifyAstronautService();
  }

  @Test
  void RawResponseOfAstros() throws Exception {
    var response = openNotifyAstronautService.getRawResponseOfAstros();

    assertTrue(response.contains("number"));
    assertTrue(response.contains("people"));
  }

  @Test
  void getNumberOfAstronauts() {
    var rawData = "{\"number\": 6, \"message\": \"success\", \"people\": [{\"name\": \"Chris Cassidy\", \"craft\": \"ISS\"}, {\"name\": \"Anatoly Ivanishin\", \"craft\": \"ISS\"}, {\"name\": \"Ivan Vagner\", \"craft\": \"ISS\"}, {\"name\": \"Sergey Ryzhikov\", \"craft\": \"ISS\"}, {\"name\": \"Kate Rubins\", \"craft\": \"ISS\"}, {\"name\": \"Sergey Kud-Sverchkov\", \"craft\": \"ISS\"}]}";
    var expected = 6;

    assertEquals(expected, openNotifyAstronautService.getNumberOfAstronauts(rawData));
  }

  @Test
  void getNamesOfAstronautsInSpace() throws Exception {
    String rawData = "{\"number\": 6, \"message\": \"success\", \"people\": [{\"name\": \"Chris Cassidy\", \"craft\": \"ISS\"}, {\"name\": \"Anatoly Ivanishin\", \"craft\": \"ISS\"}, {\"name\": \"Ivan Vagner\", \"craft\": \"ISS\"}, {\"name\": \"Sergey Ryzhikov\", \"craft\": \"ISS\"}, {\"name\": \"Kate Rubins\", \"craft\": \"ISS\"}, {\"name\": \"Sergey Kud-Sverchkov\", \"craft\": \"ISS\"}]}";
    int numOfAstronauts = openNotifyAstronautService.getNumberOfAstronauts(rawData);
    List<String> namesOfAstronauts = List.of(
      "Chris Cassidy",
      "Anatoly Ivanishin",
      "Ivan Vagner",
      "Sergey Ryzhikov",
      "Kate Rubins",
      "Sergey Kud-Sverchkov"
    );

    assertEquals(namesOfAstronauts, openNotifyAstronautService.getAstronautNames(numOfAstronauts));
  }
}