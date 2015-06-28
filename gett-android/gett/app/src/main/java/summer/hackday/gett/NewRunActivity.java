package summer.hackday.gett;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.parse.ParsePush;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewRunActivity extends Activity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private GoogleApiClient mGoogleApiClient;
    public static int REQUEST_PLACE_PICKER = 1;

    private View.OnClickListener onPickAPlaceClickListener;
    private View.OnClickListener onSelectFriendsClickListener;
    private View.OnClickListener onSendClickListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setElevation(0);

        final Context context = this;
        final String place;

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                        // this may be really really wrong
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks)this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener)this)
                .build();

        onPickAPlaceClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Gett", "onPickAPlaceClick");
                // Construct an intent for the place picker
                try {
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(getApplicationContext());
                    // Start the intent by requesting a result,
                    // identified by a request code.
                    startActivityForResult(intent, REQUEST_PLACE_PICKER);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }        // Construct an intent for the place picker
            }
        };
        onSelectFriendsClickListener = new View.OnClickListener() {
            @Override


            public void onClick(View view) {
                findViewById(R.id.friend_image).setVisibility(View.VISIBLE);
                Toast.makeText(context, "Selected Rebecca !", Toast.LENGTH_SHORT).show();
            }
        };
        onSendClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Gett", "onSendClick");
                /** Add this to the send button to send all the details to specific friends
                 * SEND!
                 */

//                ParsePush push = new ParsePush();
//                push.setChannel("");
//                push.setMessage("Going to Boba Talk at 6:30 ");
//                push.sendInBackground();

                Intent intent = new Intent(context, ReceiverActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);


                String time = new SimpleDateFormat("hh:mm").format(new Date());
                Notification notification = new Notification.Builder(context)
                        .setSmallIcon(R.drawable.push_notification_icon_24x24)
                        .setContentTitle("Spot available.")
                        .setContentText("Heidi says: Boba Guys at " + time)
                        .setContentIntent(pendingIntent)
                        .setColor(Color.argb(0, 250, 81, 30))
                        .build();
                NotificationManager notificationManager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, notification);
            }
        };

        setContentView(R.layout.activity_new_run);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.cool_view_pager);
        final TextView desc = (TextView) findViewById(R.id.cool_page_description);
        final Button button = (Button) findViewById(R.id.cool_button);
        final ImageView pagination = (ImageView) findViewById(R.id.cool_pagination);
        FontUtils.makeThisTextBetter(this, desc);
        FontUtils.makeThisTextBold(this, button);
        button.setOnClickListener(onPickAPlaceClickListener);

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView itemImage = (ImageView) getLayoutInflater().inflate(R.layout.cool_page_item, null);
                switch (position) {
                    case 0:
                        itemImage.setImageResource(R.drawable.placeholder_pickaplace);
                        break;
                    case 1:
                        itemImage.setImageResource(R.drawable.placeholder_selectfriends);
                        break;
                    case 3:
                    default:
                        itemImage.setImageResource(R.drawable.placeholder_chooseatime);
                        break;
                }
                viewPager.addView(itemImage);
                return itemImage;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public float getPageWidth(int position) {
                return 1.0f;
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        desc.setText("What's on the menu?");
                        button.setText("Pick a place");
                        button.setOnClickListener(onPickAPlaceClickListener);
                        pagination.setImageResource(R.drawable.pagination_1);
                        break;
                    case 1:
                        desc.setText("Who can get a spot?");
                        button.setText("Select friends");
                        button.setOnClickListener(onSelectFriendsClickListener);
                        pagination.setImageResource(R.drawable.pagination_2);
                        break;
                    default:
                        desc.setText("That's it!");
                        button.setText("Send");
                        button.setOnClickListener(onSendClickListener);
                        pagination.setImageResource(R.drawable.pagination_3);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        Log.v("Gett", "onActivityResult");
        if (requestCode == REQUEST_PLACE_PICKER
                && resultCode == Activity.RESULT_OK) {

            // The user has selected a place. Extract the name and address.
            final Place place = PlacePicker.getPlace(data, this);

            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            Log.v("Gett", "Picked place: " + name + " : " + address);
            String attributions = PlacePicker.getAttributions(data);
            if (attributions == null) {
                attributions = "";
            }

            /*mViewName.setText(name);
            mViewAddress.setText(address);
            mViewAttributions.setText(Html.fromHtml(attributions));*/
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

        Toast.makeText(this, "Picked Boba Guys!", Toast.LENGTH_SHORT).show();
    }

    @Override
         public void onConnectionSuspended(int n) {
        Log.v("Gett", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult r) {
        Log.v("Gett", "onConnectionFailed");
    }

    @Override
    public void onConnected(Bundle b) {
        Log.v("Gett", "onConnected");
    }
}
