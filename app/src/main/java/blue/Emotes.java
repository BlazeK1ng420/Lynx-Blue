package blue;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import blue.net.getEmotes;
import blue.net.p;
import lynx.blue.widget.preferences.KikModalPreference;

public class Emotes extends KikModalPreference {
    public Emotes(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, null);
    }

    public boolean onPreferenceClick(Preference preference) {
        if (!Tools.emoteCheck()) {
            Tools.execSQL(Tools.buildCoreID("smileyTable"), "INSERT INTO smileyTable (smiley_id, smiley_text, smiley_category, smiley_install_date) VALUES ('pie', ':)', ':)', '0'), ('pie2', ';)', ';)', '0'), ('pie3', ':(', ':(', '0'), ('pie4', ':D', ':D', '0'), ('pie5', ':P', ':P', '0'), ('pie6', ':|', ':|', '0'), ('pie7', ':/', ':/', '0'), ('pie8', '>:(', '>:(', '0'), ('pie9', ':X', ':X', '0'), ('pie10', '<3', '<3', '0'), ('pie11', '</3', '</3', '0'), ('pie12', 'B)', 'B)', '0'), ('pie13', ':3', ':3', '0'), ('pie14', ':''(', ':''(', '0'), ('pie15', ':O', ':O', '0'), ('pie16', ':S', ':S', '0'), ('pie17', ':$', ':$', '0'), ('pie18', ':*', ':*', '0'), ('pie19', '>:)', '>:)', '0'), ('pie20', ':E', ':E', '0');");
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/hGMK9AEC"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/iMdTirua"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/YNTS3dVW"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/R7z95rpn"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/Twe56V7h"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/amT4wPcb"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/DhAjmJ15"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/KBY23zzK"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/kzcqFtUa"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/SCyZEWc2"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/GLmWApyY"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/BP4vCgxH"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/v1D0i41t"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/XYSnYQi1"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/mH4tip1b"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/bGaGY1m2"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/yunPQsNk"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/yZpanCGE"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/RuW9yLvD"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/Q5nM5fMY"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/gTxahfDW"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/2B0q9vyD"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/dUGhKqw5"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/1PNFdVHf"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/dKN5m8rt"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/07yqY7hp"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("http://pastebin.com/raw/hGMK9AEC"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("https://pastebin.com/raw/2KkrVK7A"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), getEmotes.LinkIsSoHot("https://pastebin.com/raw/iRCyfBFm"));
            Tools.execSQL(Tools.buildCoreID("smileyTable"), "INSERT INTO smileyTable (smiley_id, smiley_text, smiley_category, smiley_install_date) VALUES ('aede338e', '</3', '</3', '1480559920000');");
            p.emote(getContext());
        } else {
            p.emoteAlreadySet(getContext());
        }
        return true;
    }
}
