package blue.net;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import blue.KikTools;

class cc implements OnClickListener {
    public void onClick(DialogInterface dialogInterface, int i) {
        KikTools.clearContacts();
        dialogInterface.dismiss();
    }

    cc() {
    }
}
