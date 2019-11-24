package blue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import blue.luminosity.database;
import blue.luminosity.executables;
import lynx.blue.chat.activity.IntroActivity;

public class PasswordActivity extends AppCompatActivity {
    String password;
    EditText passwordInput;
    Button submitButton;

    /* access modifiers changed from: private */
    public void runApp() {
        startActivity(new Intent(this, IntroActivity.class));
    }

    /* access modifiers changed from: private */
    public void toast() {
        Toast.makeText(this, "Password Invalid", 1).show();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!database.getBoolean("blue.password", false) || "".equals(executables.getSplashPassword())) {
            runApp();
            return;
        }
        setContentView(2131427758);
        this.passwordInput = (EditText) findViewById(2131297460);
        this.submitButton = (Button) findViewById(2131297461);
        this.submitButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PasswordActivity passwordActivity = PasswordActivity.this;
                passwordActivity.password = passwordActivity.passwordInput.getText().toString();
                if ((PasswordActivity.this.password != null) && PasswordActivity.this.password.equals(executables.getSplashPassword())) {
                    PasswordActivity.this.runApp();
                } else {
                    PasswordActivity.this.toast();
                }
            }
        });
    }
}
