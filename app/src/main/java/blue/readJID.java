package blue;

import android.util.Log;
import java.io.IOException;
import java.io.StringReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class readJID {
    public static void read(String xmpp) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(new StringReader(xmpp));
        for (int eventType = xpp.getEventType(); eventType != 1; eventType = xpp.next()) {
            if (eventType == 0) {
                Log.e("XMPP LOG START", "hello");
            } else if (eventType == 2) {
                Log.e("XMPP LOG Start tag ", xpp.getName());
            } else if (eventType == 3) {
                Log.e("XMPP LOG End tag ", xpp.getName());
            } else if (eventType == 4) {
                Log.e("XMPP LOG Text ", xpp.getText());
            }
        }
        String str = "Stanza ends here";
    }
}
