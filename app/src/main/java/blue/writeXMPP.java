package blue;

import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import lynx.blue.chat.KikApplication;


public class writeXMPP {
    public static String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + ": ";
    }

    public static void incoming(String xmpp) {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.separator);
        sb.append("XMPP for ");
        sb.append(KikApplication.getStringFromResource(2131689592));
        File folder = new File(sb.toString());
        folder.mkdirs();
        try {
            FileOutputStream fOut = new FileOutputStream(new File(folder, "receive.txt"), true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            Writer append = myOutWriter.append(getTime());
            myOutWriter.append(xmpp).append("\r\n\n");
            myOutWriter.close();
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("File write failed (receive): ");
            sb2.append(e.toString());
            Log.e("Blue Exception", sb2.toString());
        }
    }

    public static void outgoing(String xmpp) {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.separator);
        sb.append("XMPP for ");
        sb.append(KikApplication.getStringFromResource(2131689592));
        File folder = new File(sb.toString());
        folder.mkdirs();
        try {
            FileOutputStream fOut = new FileOutputStream(new File(folder, "send.txt"), true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            Writer append = myOutWriter.append(getTime());
            myOutWriter.append(xmpp).append("\r\n\n");
            myOutWriter.close();
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("File write failed (send): ");
            sb2.append(e.toString());
            Log.e("Blue Exception", sb2.toString());
        }
    }
}
