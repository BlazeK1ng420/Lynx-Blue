package blue.net;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import blue.Tools;

class cMedia implements OnClickListener {
    public void onClick(DialogInterface dialogInterface, int i) {
        Tools.execSQL(Tools.buildCoreID("kikDatabase.db"), "DELETE FROM KIKContentTable;");
        Tools.execSQL(Tools.buildCoreID("kikDatabase.db"), "DELETE FROM messagesTable WHERE app_id LIKE 'com.kik.ext.%';");
        Tools.execSQL(Tools.buildCoreID("kikDatabase.db"), "DELETE FROM KIKContentURITable;");
        dialogInterface.dismiss();
        Tools.close();
    }

    cMedia() {
    }
}
