package googlead.com.heran.googlead;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class MainActivity extends AppCompatActivity implements OnClickListener, RewardedVideoAdListener {

    private Button banner, interstitial, video;
    private AdView adView;
    private InterstitialAd interstitialAd;
    private RewardedVideoAd rewardedVideoAd;
    private int bannerCheck = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adView = findViewById(R.id.adView);
        banner = findViewById(R.id.banner);
        video = findViewById(R.id.video);
        interstitial = findViewById(R.id.interstitial);

        banner.setOnClickListener(this);
        interstitial.setOnClickListener(this);
        video.setOnClickListener(this);

        interstitial.setEnabled(false);
        video.setEnabled(false);

        /** 註冊靜態廣告 **/
        //MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        MobileAds.initialize(this, "ca-app-pub-3999467475567165~1537589986");

        /** 橫幅式廣告初始化 **/
        adView.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
        adView.setVisibility(View.VISIBLE);

        /** 插頁式廣告初始化 **/
        interstitialAd = new InterstitialAd(this);
        //使用Google測試ID
        //interstitialAd.setAdUnitId("ca-app-pub-3999467475567165/7456210628");
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.setAdListener(new AdListener(){
            public void onAdLoaded() {
                Toast.makeText(getApplicationContext(), "InterstitialAd is loaded!", Toast.LENGTH_SHORT).show();
                interstitial.setEnabled(true);
            }

            public void onAdClosed() {
                interstitial.setText("已撥放");
//                interstitial.setEnabled(false);
            }
        });
        interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());

        /** 獎勵式影片廣告初始化 **/
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
        /**
        rewardedVideoAd.loadAd("ca-app-pub-3999467475567165/7051341354",
                new AdRequest.Builder().build());
         **/
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.banner:
                bannerCheck += 1;
                if(bannerCheck > 6)
                    bannerCheck = 0;

                switch(bannerCheck){
                    case 0:
                        adView.setVisibility(View.INVISIBLE);
                        banner.setText("橫幅式廣告:OFF");
                        break;
                    case 1:
                        adView.setAdSize(AdSize.BANNER);
                        adView.setVisibility(View.VISIBLE);
                        banner.setText("橫幅式廣告:標準");
                        break;
                    case 2:
                        adView.setAdSize(AdSize.LARGE_BANNER);
                        adView.setVisibility(View.VISIBLE);
                        banner.setText("橫幅式廣告:大型");
                        break;
                    case 3:
                        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
                        adView.setVisibility(View.VISIBLE);
                        banner.setText("橫幅式廣告:IAB中矩形");
                        break;
                    case 4:
                        adView.setAdSize(AdSize.FULL_BANNER);
                        adView.setVisibility(View.VISIBLE);
                        banner.setText("橫幅式廣告:IAB完整");
                        break;
                    case 5:
                        adView.setAdSize(AdSize.LEADERBOARD);
                        adView.setVisibility(View.VISIBLE);
                        banner.setText("橫幅式廣告:IAB超級");
                        break;
                    case 6:
                        adView.setAdSize(AdSize.SMART_BANNER);
                        adView.setVisibility(View.VISIBLE);
                        banner.setText("橫幅式廣告:智慧");
                        break;
                }
                break;
            case R.id.interstitial:
                if(interstitialAd.isLoaded()) {
                    interstitialAd.show();
                    Toast.makeText(this, "Interstitial AD ON", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this, "AD is not load yet!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.video:
                if(rewardedVideoAd.isLoaded()){
                    rewardedVideoAd.show();
                    Toast.makeText(this, "Video AD played!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
    }

    /** RewardedVideoAdListener **/
    @Override
    public void onRewardedVideoAdLoaded() {
        Toast.makeText(this, "Video Ad is loaded!", Toast.LENGTH_SHORT).show();
        video.setEnabled(true);
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        video.setText("已撥放");
        video.setEnabled(false);
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}
