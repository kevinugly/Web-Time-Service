package issinfo;

import java.net.*;
import java.util.*;
import org.json.*;

public class OpenNotifyAstronautService {
  public String getRawResponseOfAstros() throws Exception {
    return new Scanner(new URL("http://api.open-notify.org/astros.json").openStream(), "UTF-8").useDelimiter("\\A").next();
  }

  public int getNumberOfAstronauts(String rawData){
    return new JSONObject(rawData).getInt("number");
  }

  public List<String> getAstronautNames(int numOfAstronauts) throws Exception {
    String rawData = getRawResponseOfAstros();
    List<String> nameOfAstronauts = new ArrayList<>();

    for(int x = 0; x < numOfAstronauts; x++) {
      nameOfAstronauts.add(new JSONObject(rawData).getJSONArray("people").getJSONObject(x).getString("name"));
    }

    return nameOfAstronauts;
  }
}