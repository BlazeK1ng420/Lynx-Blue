package blue.net;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import blue.Tools;

class close implements OnClickListener {
    close() {
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        Tools.close();
    }
}
