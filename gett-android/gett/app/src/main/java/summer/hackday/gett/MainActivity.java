package summer.hackday.gett;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class MainActivity extends Activity {

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Parse.initialize(getApplicationContext(), "RF0OW20w6uLc8K6J1jpg4IZ2AEr5SlQde1ph4WRl", "PvAgMhXiE9cTXt8rxZns5rdJxcMuy0fRAsZRW4SV");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        /** SR: This is where you can add a string to segment the people who you want to send the push notification to.
         *
         */
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

        FacebookSdk.sdkInitialize(getApplicationContext());

        setView();
    }

    public void setView() {
        setContentView(R.layout.activity_main);
        FontUtils.makeThisTextBold(this, (Button) findViewById(R.id.login_button));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void onLoginButtonClick(View view) {
        final Activity mainActivity = this;
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject json, GraphResponse response) {
                                Log.d("gett", "1");
                                if (response.getError() != null) {
                                    Log.e("Gett", "Error logging in: " + response.getError().toString());
                                } else {
                                    try {
                                        Log.d("gett", "2");
                                        String user_id = json.getString("id");
                                        Log.d("Gett", "Login successful! User id: " + user_id);

                                        // Start new food run activity
                                        Intent intent = new Intent(mainActivity, NewRunActivity.class);
                                        mainActivity.startActivity(intent);
                                    } catch (JSONException e) {
                                        Log.e("Gett", "Exception: " + e.getMessage());
                                    }
                                }
                            }
                        }
                ).executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d("Gett", "Cancelled!");
            }

            @Override
            public void onError(FacebookException e) {
                Log.d("Gett", "Error! " + e.toString());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
