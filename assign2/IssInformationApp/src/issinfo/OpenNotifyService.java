package issinfo;

import issinfo.preview.Location;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.*;
import java.util.*;
import org.json.*;

public class OpenNotifyService {
  public String getRawResponse(Location location) throws Exception {
    return new Scanner(new URL("http://api.open-notify.org/iss-pass.json?lat=" + location.latitude() + "&lon=" + location.longitude() +"&n=1").openStream(), StandardCharsets.UTF_8).useDelimiter("\\A").next();
  }

  public long parseJSON(String sampleData) {
    return new JSONObject(sampleData).getJSONArray("response").getJSONObject(0).getInt("risetime");
  }

  public String getFlyoverTime(Location location) {
    DateFormat df = new SimpleDateFormat("hh:mma");
    df.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));

    try {
      long unix_seconds = parseJSON(getRawResponse(location));
      Date date = new Date(unix_seconds * 1000L);
      return df.format(date);
    }catch(Exception ex) {
      throw new RuntimeException(ex.getClass().getSimpleName() + ": " + ex.getMessage());
    }
  }
}