package summer.hackday.gett;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import com.parse.Parse;
import com.parse.ParseInstallation;

import com.facebook.FacebookSdk;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Parse.initialize(this, "RF0OW20w6uLc8K6J1jpg4IZ2AEr5SlQde1ph4WRl", "PvAgMhXiE9cTXt8rxZns5rdJxcMuy0fRAsZRW4SV");
        ParseInstallation.getCurrentInstallation().saveInBackground();



        FacebookSdk.sdkInitialize(this);

        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void onLoginButtonClick(View view) {
        // Launch Facebook login stuff
    }

}
