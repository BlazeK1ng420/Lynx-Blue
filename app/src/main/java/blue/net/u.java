package blue.net;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class u extends AsyncTask<String, Void, String> {
    public static final String ERROR_MESSAGE = "Error";

    /* access modifiers changed from: protected */
    public String doInBackground(String[] strArr) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(strArr[0]).openConnection().getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    stringBuilder.append(readLine).append("\n");
                } else {
                    bufferedReader.close();
                    return stringBuilder.toString().trim();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public static String c(String str) {
        try {
            return (String) new u().execute(new String[]{str}).get(5000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
