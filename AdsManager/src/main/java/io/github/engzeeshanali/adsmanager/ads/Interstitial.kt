package io.github.engzeeshanali.adsmanager.ads

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import io.github.engzeeshanali.adsmanager.AdsFlexManager
import java.lang.NullPointerException


class Interstitial(val context: Context) {

    private var mInterstitialAd: InterstitialAd? = null
    private var onAdClose: (() -> Unit)? = null

    //test banner ad unit for test mode
    private var testInterstitialAdUnit = "ca-app-pub-3940256099942544/1033173712"

    //live ad banner ad unit for live ads
    private var interstitialAdUnit = ""



    /**
     * checks if the test adUnit | real
     * @return adUnit:String
     * @throws NullPointerException
     */
    private fun getAdUnit(): String {
        return if (AdsFlexManager.testMode) {
            testInterstitialAdUnit
        } else {
            if (interstitialAdUnit.isEmpty()) {
                throw NullPointerException("Use setAdUnit method for live ads. or enable test mode")
            } else {
                return interstitialAdUnit
            }
        }
    }

    /**
     * this function helps to set the live adUnit id for realtime ads
     * @param adUnit string
     */
    fun setAdUnit(adUnit: String) {
        this.interstitialAdUnit = adUnit
    }


    /**
     * load interstitial ad
     */
    fun loadInterstitialAd() {
        InterstitialAd.load(
            context,
            getAdUnit(),
            AdsFlexManager.adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    mInterstitialAd?.fullScreenContentCallback = adCallbacks
                }
            })
    }


    /**
     *  showInterstitialAd if ad is not loaded it will call the callback that response the ad is closed
     */
    fun showInterstitialAd(activity: Activity, callback: (() -> Unit)? = null) {
        if (callback != null) {
            this.onAdClose = callback
        }

        if (mInterstitialAd != null) {
            mInterstitialAd?.show(activity)
        } else {
            onAdClose?.let { it() }
        }

    }


    private val adCallbacks = object : FullScreenContentCallback() {

        override fun onAdDismissedFullScreenContent() {
            // Called when ad is dismissed.
            onAdClose?.let { it() }
            mInterstitialAd = null
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            onAdClose?.let { it() }
            mInterstitialAd = null
        }

    }

}