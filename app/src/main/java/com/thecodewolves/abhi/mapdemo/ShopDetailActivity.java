package com.thecodewolves.abhi.mapdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thecodewolves.abhi.mapdemo.Model.ShopDetails;
import com.thecodewolves.abhi.mapdemo.Model.ShopDetailsResponse;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShopDetailActivity extends AppCompatActivity {

    @Inject
    Retrofit retrofit;

    private String placeId;

    ShopDetails shopDetails;

    @BindView(R.id.shop_name) TextView shopName;

    @BindView(R.id.shop_address) TextView shopAddress;

    @BindView(R.id.shopImage) ImageView shopImage;

    @BindView(R.id.shop_contact) TextView shopContact;

    @BindView(R.id.shop_website) TextView shopWebsite;

    @BindView(R.id.shop_current_status) TextView shopCurrentStatus;

    @BindView(R.id.shop_rating)
    RatingBar shopRating;

    @BindView(R.id.timingtv) TextView timingTV;
    @BindView(R.id.monday_timing) TextView mondayTiming;
    @BindView(R.id.tuesday_timing) TextView tuesdayTiming;
    @BindView(R.id.wednesday_timing) TextView wednesdayTiming;
    @BindView(R.id.thrusday_timing) TextView thrusdayTiming;
    @BindView(R.id.friday_timing) TextView fridayTiming;
    @BindView(R.id.saturday_timing) TextView saturdayTiming;
    @BindView(R.id.sunday_timing) TextView sundayTiming;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        ((MyApplication)getApplication()).getNetComponent().inject(this);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            placeId = extras.getString("placeIdKey");
        }

        try{
            getDetails(placeId);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void getDetails(String placeId){
        ApiInterface apiService =
                retrofit.create(ApiInterface.class);
        String Api_Key = getResources().getString(R.string.API_KEY);
        Call<ShopDetailsResponse> call = apiService.getShopDetails(placeId,Api_Key);
        call.enqueue(new Callback<ShopDetailsResponse>() {

            @Override
            public void onResponse(Call<ShopDetailsResponse> call, Response<ShopDetailsResponse> response) {

                shopDetails=response.body().getResult();
                shopName.setText(shopDetails.getName());

                shopAddress.setText(shopDetails.getVicinity());
                try{
                    shopContact.setText(shopDetails.getFormattedPhoneNumber());
                }catch (NullPointerException ex){
                    shopContact.setText("Unknown");
                }

                try{
                    shopWebsite.setText(shopDetails.getWebsite());
                }catch (NullPointerException ex){
                    shopContact.setText("Unknown");
                }
                try{
                    if (shopDetails.getOpeningHours().getOpenNow())
                        shopCurrentStatus.setText("Open Now");
                    else
                        shopCurrentStatus.setText("Closed Now");
                }catch (NullPointerException ex){
                    shopCurrentStatus.setText("Unknown");
                }
                try{
                    shopRating.setRating(shopDetails.getRating().floatValue());
                    shopRating.setEnabled(false);
                }catch (NullPointerException ex){
                    shopRating.setRating(0);
                    shopRating.setEnabled(false);
                }
                if(shopDetails.getOpeningHours()!=null){
                    if(shopDetails.getOpeningHours().getWeekdayText()!=null){
                        mondayTiming.setText(shopDetails.getOpeningHours().getWeekdayText().get(0));
                        tuesdayTiming.setText(shopDetails.getOpeningHours().getWeekdayText().get(1));
                        wednesdayTiming.setText(shopDetails.getOpeningHours().getWeekdayText().get(2));
                        thrusdayTiming.setText(shopDetails.getOpeningHours().getWeekdayText().get(3));
                        fridayTiming.setText(shopDetails.getOpeningHours().getWeekdayText().get(4));
                        saturdayTiming.setText(shopDetails.getOpeningHours().getWeekdayText().get(5));
                        sundayTiming.setText(shopDetails.getOpeningHours().getWeekdayText().get(6));
                    }
                }else {
                    timingTV.setText("Timing Unknown");
                }
                Picasso.with(ShopDetailActivity.this).load(shopDetails.getIcon()).into(shopImage);
            }

            @Override
            public void onFailure(Call<ShopDetailsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
