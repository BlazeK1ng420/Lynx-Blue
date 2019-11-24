package blue.a;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class udshit extends AsyncTask<String, Void, String> {
    public static String quote(String str) throws IOException, JSONException {
        JSONArray jsonArray = readJsonFromUrl("http://api.urbandictionary.com/v0/define?term=" + str).getJSONArray("list");
        int n = 0;
        int fromIndex = 0;
        while (true) {
            int index = jsonArray.toString().indexOf("\"definition\":", fromIndex);
            if (index == -1) {
                break;
            }
            fromIndex = index + 1;
            n++;
        }
        if (n <= 0) {
            return URLDecoder.decode("Urban Dictionary lookup for " + str, "UTF-8") + " has no result";
        }
        int n2 = (int) System.currentTimeMillis();
        int n3 = n2;
        if (n2 < 0) {
            n3 = -n2;
        }
        return URLDecoder.decode("Result for: " + str, "UTF-8") + "\n\n" + jsonArray.getJSONObject(n3 % n).get("definition").toString().replace("[", "").replace("]", "");
    }

    private static String readAll(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            int read = reader.read();
            if (read == -1) {
                return sb.toString();
            }
            sb.append((char) read);
        }
    }

    @TargetApi(19)
    private static JSONObject readJsonFromUrl(String openStream) throws IOException, JSONException {
        InputStream newOpenstream = new URL(openStream).openStream();
        return new JSONObject(readAll(new BufferedReader(new InputStreamReader(newOpenstream, Charset.forName("UTF-8")))));
    }


    public String doInBackground(String... strings) {
        try {
            return quote(strings[0]);
        } catch (IOException e) {
            return strings[0];
        } catch (JSONException e2) {
            return strings[0];
        }
    }

    public static String parse(String input) throws InterruptedException, ExecutionException, TimeoutException {
        if (input.contains("\n")) {
            return input;
        }
        String input2 = input.substring(4).replace(" ", "%20");
        return new udshit().execute(new String[]{input2}).get(5000, TimeUnit.MILLISECONDS);
    }
}
