package blue.net;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import blue.Tools;

class cMessages implements OnClickListener {
    public void onClick(DialogInterface dialogInterface, int i) {
        Tools.execSQL(Tools.buildCoreID("kikDatabase.db"), "DROP TABLE IF EXISTS messagesTable;");
        dialogInterface.dismiss();
        Tools.close();
    }

    cMessages() {
    }
}
