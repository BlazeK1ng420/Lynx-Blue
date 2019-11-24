package blue.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import blue.KikTools;
import blue.Toast;
import blue.a.Builders;
import blue.a.commands;
import blue.luminosity.database;

@SuppressLint("ResourceType")
public class BotSystem {
    public static Activity activity;
    public static String jid;

    public static void init() {
        if (!isGroup()) {
            Toast.toast("Bot system only works in group chats!");
            return;
        }
        Builder builder = new Builder(activity);
        builder.setTitle("Bot System");
        builder.setIcon(2131231827);
        builder.setItems(new String[]{"Rage Bot Options", "Welcome / Leave Messages", "Admin Options", "Mass Moderation", "Reset Bot"}, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    BotSystem.rageSettings();
                } else if (i == 1) {
                    BotSystem.welcomeLeave();
                } else if (i == 2) {
                    BotSystem.adminSettings();
                } else if (i == 3) {
                    BotSystem.massModeration();
                } else if (i == 4) {
                    BotSystem.resetBot();
                }
            }
        });
        builder.setPositiveButton("Exit", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private static boolean isGroup() {
        return KikTools.chatJID.endsWith("@groups.kik.com");
    }

    private static void checkRage() {
        String str = "blue.rage";
        if (!database.getBoolean(str, false)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(KikTools.chatJID);
            if (!database.getBoolean(sb.toString(), false)) {
                Toast.toast("These settings will not go into effect until you enable Rage Bot");
            }
        }
    }

    /* access modifiers changed from: private */
    public static void rageSettings() {
        checkRage();
        Builder builder = new Builder(activity);
        builder.setTitle("Rage Bot");
        builder.setIcon(2131231827);
        builder.setItems(new String[]{"Customize Triggers", "Customize Censor List"}, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    BotSystem.addDelTrigger();
                } else if (i == 1) {
                    BotSystem.addDelCensor();
                }
            }
        });
        builder.setPositiveButton("Back", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                BotSystem.init();
            }
        });
        builder.show();
    }

    /* access modifiers changed from: private */
    public static void welcomeLeave() {
        Builder builder = new Builder(activity);
        View view = activity.getLayoutInflater().inflate(2131493305, null);
        builder.setView(view);
        builder.setTitle("Status Messages");
        builder.setIcon(2131231827);
        StringBuilder sb = new StringBuilder();
        sb.append("welcome_");
        sb.append(KikTools.chatJID);
        boolean welcomeEnabled = database.getBoolean(sb.toString(), false);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("leave_");
        sb2.append(KikTools.chatJID);
        boolean leaveEnabled = database.getBoolean(sb2.toString(), false);
        StringBuilder sb3 = new StringBuilder();
        sb3.append("welcome.");
        sb3.append(KikTools.chatJID);
        String str = "not set!";
        String welcomeText = database.getString(sb3.toString(), str);
        StringBuilder sb4 = new StringBuilder();
        sb4.append("leave.");
        sb4.append(KikTools.chatJID);
        String leaveText = database.getString(sb4.toString(), str);
        final EditText welcomeEditText = (EditText) view.findViewById(2131297492);
        final EditText leaveEditText = (EditText) view.findViewById(2131297493);
        if (!welcomeText.equals(str)) {
            welcomeEditText.setText(welcomeText);
        }
        if (!leaveText.equals(str)) {
            leaveEditText.setText(leaveText);
        }
        CheckBox welcome = (CheckBox) view.findViewById(2131297495);
        welcome.setChecked(welcomeEnabled);
        welcome.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                StringBuilder sb = new StringBuilder();
                sb.append("welcome_");
                sb.append(KikTools.chatJID);
                database.setBoolean(sb.toString(), b);
            }
        });
        CheckBox leave = (CheckBox) view.findViewById(2131297496);
        leave.setChecked(leaveEnabled);
        leave.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                StringBuilder sb = new StringBuilder();
                sb.append("leave_");
                sb.append(KikTools.chatJID);
                database.setBoolean(sb.toString(), b);
            }
        });
        builder.setPositiveButton("Save", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                StringBuilder sb = new StringBuilder();
                sb.append("welcome.");
                sb.append(KikTools.chatJID);
                database.setString(sb.toString(), welcomeEditText.getText().toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append("leave.");
                sb2.append(KikTools.chatJID);
                database.setString(sb2.toString(), leaveEditText.getText().toString());
                Toast.toast("Changes saved");
                BotSystem.init();
            }
        });
        builder.setNegativeButton("Cancel", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.toast("Changes discarded");
                BotSystem.init();
            }
        });
        builder.show();
    }

    /* access modifiers changed from: private */
    public static void addDelTrigger() {
        Builder builder = new Builder(activity);
        View view = activity.getLayoutInflater().inflate(2131493304, null);
        final EditText triggerInput = (EditText) view.findViewById(2131297494);
        ((TextView) view.findViewById(2131297497)).setText(commands.getTriggerList());
        triggerInput.setHint("Format: Trigger > Response");
        builder.setView(view);
        builder.setTitle("Customize Triggers");
        builder.setIcon(2131231827);
        builder.setPositiveButton("Add", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Builders.addTriggerCommand(triggerInput.getText().toString());
                dialog.dismiss();
                BotSystem.addDelTrigger();
            }
        });
        builder.setNegativeButton("Delete", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Builders.deleteTriggerCommand(triggerInput.getText().toString());
                dialog.dismiss();
                BotSystem.addDelTrigger();
            }
        });
        builder.setNeutralButton("Delete All", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                BotSystem.deleteAllTriggers();
            }
        });
        builder.show();
    }

    /* access modifiers changed from: private */
    public static void addDelCensor() {
        Builder builder = new Builder(activity);
        View view = activity.getLayoutInflater().inflate(2131493304, null);
        final EditText triggerInput = (EditText) view.findViewById(2131297494);
        TextView triggerList = (TextView) view.findViewById(2131297497);
        triggerInput.setHint("Enter censor");
        triggerList.setText(commands.getTriggerList());
        builder.setView(view);
        builder.setTitle("Customize Censors");
        builder.setIcon(2131231827);
        builder.setPositiveButton("Add", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Builders.addCensorRageCommand(triggerInput.getText().toString());
                dialog.dismiss();
                BotSystem.addDelCensor();
            }
        });
        builder.setNegativeButton("Delete", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Builders.deleteCensorCommand(triggerInput.getText().toString());
                dialog.dismiss();
                BotSystem.addDelCensor();
            }
        });
        builder.setNeutralButton("Delete All", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                BotSystem.deleteAllCensors();
            }
        });
        builder.show();
    }

    /* access modifiers changed from: private */
    public static void deleteAllCensors() {
        Builder builder = new Builder(activity);
        builder.setTitle("Delete All Censors");
        builder.setIcon(2131231827);
        builder.setMessage("Are you sure you want to delete all censors?\n\nNOTE: It will only reset for the current chat");
        builder.setPositiveButton("Yes", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Builders.clearCensorListCommand();
                Toast.toast("All Censors Deleted");
            }
        });
        builder.setNegativeButton("No", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                BotSystem.rageSettings();
            }
        });
        builder.show();
    }

    /* access modifiers changed from: private */
    public static void deleteAllTriggers() {
        Builder builder = new Builder(activity);
        builder.setTitle("Delete All Triggers");
        builder.setIcon(2131231827);
        builder.setMessage("Are you sure you want to delete all bot triggers?\n\nNOTE: It will only reset for the current chat");
        builder.setPositiveButton("Yes", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Builders.clearTriggerListCommand();
                Toast.toast("All Triggers Deleted");
            }
        });
        builder.setNegativeButton("No", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                BotSystem.rageSettings();
            }
        });
        builder.show();
    }

    /* access modifiers changed from: private */
    public static void adminSettings() {
        Builder builder = new Builder(activity);
        builder.setTitle("Admin Options");
        builder.setIcon(2131231827);
        builder.setItems(new String[]{"Lock Group", "Lock Group (Ban)", "Lock Group Name", "Unlock Group"}, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    commands.lockRemove();
                    BotSystem.adminSettings();
                } else if (i == 1) {
                    commands.lockBan();
                    BotSystem.adminSettings();
                } else if (i == 2) {
                    BotSystem.lockGroupName();
                } else if (i == 3) {
                    commands.unlock();
                    BotSystem.adminSettings();
                }
            }
        });
        builder.setPositiveButton("Back", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                BotSystem.init();
            }
        });
        builder.show();
    }

    /* access modifiers changed from: private */
    public static void lockGroupName() {
        Builder builder = new Builder(activity);
        View view = activity.getLayoutInflater().inflate(2131493304, null);
        final EditText gnInput = (EditText) view.findViewById(2131297494);
        TextView triggerList = (TextView) view.findViewById(2131297497);
        builder.setView(view);
        builder.setTitle("Lock Group Name");
        builder.setIcon(2131231827);
        StringBuilder sb = new StringBuilder();
        sb.append("gn.");
        sb.append(KikTools.chatJID);
        String str = "off";
        String lockNameText = database.getString(sb.toString(), str);
        String str2 = "Enter group name";
        if (lockNameText.equals(str)) {
            gnInput.setHint(str2);
            triggerList.setText("Group name not set");
            triggerList.setTextColor(Color.parseColor("#b53737"));
        } else {
            gnInput.setHint(str2);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Group name currently locked to:\n\n'");
            sb2.append(lockNameText);
            sb2.append("'");
            triggerList.setText(sb2.toString());
            triggerList.setTextColor(Color.parseColor("#4c9a2a"));
        }
        builder.setPositiveButton("Save", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                StringBuilder sb = new StringBuilder();
                sb.append("gn.");
                sb.append(KikTools.chatJID);
                database.setString(sb.toString(), gnInput.getText().toString());
                Toast.toast("Changes saved.");
                dialog.dismiss();
                BotSystem.adminSettings();
            }
        });
        builder.setNegativeButton("Discard", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.toast("Changes discarded");
                dialog.dismiss();
                BotSystem.adminSettings();
            }
        });
        builder.setNeutralButton("Unlock", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                StringBuilder sb = new StringBuilder();
                sb.append("gn.");
                sb.append(KikTools.chatJID);
                database.setString(sb.toString(), "off");
                Toast.toast("Group name unlocked");
                dialog.dismiss();
                BotSystem.adminSettings();
            }
        });
        builder.show();
    }

    /* access modifiers changed from: private */
    public static void massModeration() {
        Builder builder = new Builder(activity);
        builder.setTitle("Mass Moderation");
        builder.setIcon(2131231827);
        builder.setItems(new String[]{"Purge Group", "Promote All", "Demote All", "Unban All"}, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    BotSystem.confirmMassModeration("kick everyone", i);
                    dialogInterface.dismiss();
                } else if (i == 1) {
                    BotSystem.confirmMassModeration("promote everyone", i);
                    dialogInterface.dismiss();
                } else if (i == 2) {
                    BotSystem.confirmMassModeration("demote everyone", i);
                    dialogInterface.dismiss();
                } else if (i == 3) {
                    BotSystem.confirmMassModeration("unban everyone", i);
                    dialogInterface.dismiss();
                }
            }
        });
        builder.setPositiveButton("Back", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                BotSystem.init();
            }
        });
        builder.show();
    }

    /* access modifiers changed from: private */
    public static void confirmMassModeration(String action, final int choice) {
        Builder builder = new Builder(activity);
        builder.setTitle("Are you sure?");
        builder.setIcon(2131231827);
        StringBuilder sb = new StringBuilder();
        sb.append("Are you sure you want to ");
        sb.append(action);
        sb.append("?\n\nYou cannot undo this action!");
        builder.setMessage(sb.toString());
        builder.setPositiveButton("Yes", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                int i = choice;
                if (i == 0) {
                    KikTools.purge();
                } else if (i == 1) {
                    KikTools.promoteAll();
                } else if (i == 2) {
                    KikTools.demoteAll();
                } else {
                    if (i == 3) {
                        KikTools.unbanAll();
                    }
                }
            }
        });
        builder.setNegativeButton("No", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                BotSystem.massModeration();
            }
        });
        builder.show();
    }

    /* access modifiers changed from: private */
    public static void resetBot() {
        Builder builder = new Builder(activity);
        builder.setTitle("Reset Bot");
        builder.setIcon(2131231827);
        builder.setMessage("Are you sure you want to reset your bot settings?\n\nNOTE: It will only reset for the current chat\n\nNOTE: This will NOT clear your rage bot settings");
        builder.setPositiveButton("Yes", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                commands.resetBot();
                Toast.toast("Bot settings reset for this chat!");
            }
        });
        builder.setNegativeButton("No", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                BotSystem.init();
            }
        });
        builder.show();
    }
}
