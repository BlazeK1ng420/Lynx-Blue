package blue.net;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class cancel implements OnClickListener {
    public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
    }

    cancel() {
    }
}
