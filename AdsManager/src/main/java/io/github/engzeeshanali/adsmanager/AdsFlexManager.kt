package io.github.engzeeshanali.adsmanager

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import io.github.engzeeshanali.adsmanager.ads.Banner
import io.github.engzeeshanali.adsmanager.ads.Interstitial
import io.github.engzeeshanali.adsmanager.ads.RewardedVideo


object AdsFlexManager {

    //to enable test ads for development
    var testMode = false
    val adRequest = AdRequest.Builder().build()

    fun init(context: Context) {
        MobileAds.initialize(context) {}
    }

    fun getBannerInstance(context: Context): Banner {
        return Banner(context)
    }


    fun getInterstitialInstance(context: Context): Interstitial {
        return Interstitial(context)
    }

    fun getRewardedVideoInstance(context: Context): RewardedVideo {
        return RewardedVideo(context)
    }

}