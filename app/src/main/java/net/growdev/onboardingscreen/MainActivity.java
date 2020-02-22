package net.growdev.onboardingscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private OnBoardingAdapter onBoardingAdapter;
    private LinearLayout layoutOnBoardingIndicator;
    private MaterialButton buttonOnBoardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutOnBoardingIndicator = findViewById(R.id.layoutOnBoardingIndicator);
        buttonOnBoardingAction = findViewById(R.id.buttonOnBoardingAction);

        setupOnBoardingItems();

        final ViewPager2 onBoardingViewPager = findViewById(R.id.onBoardingViewPager);
        onBoardingViewPager.setAdapter(onBoardingAdapter);

        setupOnBoardingIndicator();
        setCurrentOnBoardingIndicator(0);

        onBoardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnBoardingIndicator(position);
            }
        });

        buttonOnBoardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBoardingViewPager.getCurrentItem() + 1 < onBoardingAdapter.getItemCount()){
                    onBoardingViewPager.setCurrentItem(onBoardingViewPager.getCurrentItem() + 1);
                } else {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
            }
        });
    }

    private void setupOnBoardingItems(){
        List<OnBoardingItem> onBoardingItems = new ArrayList<>();

        OnBoardingItem itemPayOnline = new OnBoardingItem();
        itemPayOnline.setTitle("Pay your bill online");
        itemPayOnline.setDescription("Electric bill payment is a feature of online, mobile and telephone banking");
        itemPayOnline.setImage(R.drawable.pay_online);

        OnBoardingItem itemOnTheWay = new OnBoardingItem();
        itemOnTheWay.setTitle("Pay your bill online");
        itemOnTheWay.setDescription("Electric bill payment is a feature of online, mobile and telephone banking");
        itemOnTheWay.setImage(R.drawable.on_the_way);

        OnBoardingItem itemEatTogether = new OnBoardingItem();
        itemEatTogether.setTitle("Pay your bill online");
        itemEatTogether.setDescription("Electric bill payment is a feature of online, mobile and telephone banking");
        itemEatTogether.setImage(R.drawable.eat_together);

        onBoardingItems.add(itemPayOnline);
        onBoardingItems.add(itemOnTheWay);
        onBoardingItems.add(itemEatTogether);

        onBoardingAdapter = new OnBoardingAdapter(onBoardingItems);
    }

    private void setupOnBoardingIndicator(){
        ImageView[] indicator = new ImageView[onBoardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicator.length; i++){
            indicator[i] = new ImageView(getApplicationContext());
            indicator[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicator[i].setLayoutParams(layoutParams);
            layoutOnBoardingIndicator.addView(indicator[i]);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentOnBoardingIndicator(int index){
        int childCount = layoutOnBoardingIndicator.getChildCount();
        for (int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView) layoutOnBoardingIndicator.getChildAt(i);
            if (i == index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive)
                );
            }
        }
        if (index == onBoardingAdapter.getItemCount() - 1){
            buttonOnBoardingAction.setText("START");
        } else {
            buttonOnBoardingAction.setText("NEXT");
        }
    }
}
