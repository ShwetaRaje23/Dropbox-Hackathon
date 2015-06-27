package summer.hackday.gett;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

public class FontUtils {

    public static void makeThisTextBetter(Context context, TextView textView) {
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Regular.ttf");
        textView.setTypeface(face);
    }

    public static void makeThisTextBold(Context context, Button button) {
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Bold.ttf");
        button.setTypeface(face);
    }

    public static void makeThisTextBold(Context context, TextView textView) {
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Bold.ttf");
        textView.setTypeface(face);
    }
}
