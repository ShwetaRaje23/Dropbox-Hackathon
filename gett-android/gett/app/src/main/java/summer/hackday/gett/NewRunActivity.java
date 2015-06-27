package summer.hackday.gett;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.parse.ParsePush;

public class NewRunActivity extends Activity {
    private GoogleApiClient mGoogleApiClient;
    public static int REQUEST_PLACE_PICKER = 1;

    private View.OnClickListener onPickAPlaceClickListener;
    private View.OnClickListener onSelectFriendsClickListener;
    private View.OnClickListener onSendClickListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                // Construct an intent for the place picker
                try {
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(context);
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
                Toast.makeText(context, "Selected all your friends!", Toast.LENGTH_SHORT).show();
            }
        };
        onSendClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /** Add this to the send button to send all the details to specific friends
                 * SEND!
                 */

                ParsePush push = new ParsePush();
                push.setChannel("");
                push.setMessage("Going to Boba Talk at 6:30 ");
                push.sendInBackground();


            }
        };

        setContentView(R.layout.activity_new_run);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.cool_view_pager);
        final TextView desc = (TextView) findViewById(R.id.cool_page_description);
        final Button button = (Button) findViewById(R.id.cool_button);
        final ImageView pagination = (ImageView) findViewById(R.id.cool_pagination);
        FontUtils.makeThisTextBetter(this, desc);
        FontUtils.makeThisTextBold(this, button);

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

        if (requestCode == REQUEST_PLACE_PICKER
                && resultCode == Activity.RESULT_OK) {

            // The user has selected a place. Extract the name and address.
            final Place place = PlacePicker.getPlace(data, this);

            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
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
    }
}
