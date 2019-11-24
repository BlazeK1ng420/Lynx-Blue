package blue.net;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class dc implements OnClickListener {
    dc() {
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
    }
}
