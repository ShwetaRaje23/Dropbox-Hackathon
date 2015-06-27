package summer.hackday.gett;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.facebook.FacebookSdk;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main);

        FrameLayout viewPagerContainer = (FrameLayout) findViewById(R.id.cool_view_pager_container);
        final ViewPager viewPager = new ViewPager(this);
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
                View item = getLayoutInflater().inflate(R.layout.cool_page_item, null);
                ImageView itemImage = (ImageView) item.findViewById(R.id.page_item_image);
                itemImage.setImageResource(R.drawable.placeholder_pickaplace);
                viewPager.addView(item);
                return item;
            }

            @Override
            public void destroyItem (ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        viewPagerContainer.addView(viewPager);

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
