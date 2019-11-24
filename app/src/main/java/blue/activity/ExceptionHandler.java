package blue.activity;

import android.os.Build.VERSION;
import blue.luminosity.database;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExceptionHandler implements UncaughtExceptionHandler {

    /* renamed from: default reason: not valid java name */
    private UncaughtExceptionHandler f1default = Thread.getDefaultUncaughtExceptionHandler();

    public void uncaughtException(Thread thread, Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        String sStackTrace = sw.toString();
        if (sStackTrace.length() > 1800) {
            StringBuilder sb = new StringBuilder();
            sb.append(sStackTrace.substring(0, Math.min(sStackTrace.length(), 1800)));
            sb.append("... TRUNCATED");
            sStackTrace = sb.toString();
        }
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        Date today = Calendar.getInstance().getTime();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(df.format(today));
        String str = "\n\n";
        sb2.append(str);
        String todayAsString = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Android Version: ");
        sb3.append(VERSION.RELEASE);
        sb3.append(str);
        String myVersion = sb3.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append("Blue Kik Crash Log\nVersion 2.5\n\n");
        sb4.append(myVersion);
        sb4.append(todayAsString);
        sb4.append(sStackTrace);
        String logThis = sb4.toString();
        database.setBoolean("check.target", true);
        database.setString("crash.log", logThis);
        this.f1default.uncaughtException(thread, throwable);
    }
}
