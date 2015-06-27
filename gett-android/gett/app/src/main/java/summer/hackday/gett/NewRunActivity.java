package summer.hackday.gett;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NewRunActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                        pagination.setImageResource(R.drawable.pagination_1);
                        break;
                    case 1:
                        desc.setText("Who can get a spot?");
                        button.setText("Select friends");
                        pagination.setImageResource(R.drawable.pagination_2);
                        break;
                    default:
                        desc.setText("3rd desc");
                        button.setText("Send");
                        pagination.setImageResource(R.drawable.pagination_3);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
