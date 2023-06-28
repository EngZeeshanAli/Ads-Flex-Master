package io.github.engzeeshanali.adsmanager.ads

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener

import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import io.github.engzeeshanali.adsmanager.models.RewardItem
import io.github.engzeeshanali.adsmanager.AdsFlexManager
import java.lang.NullPointerException

class RewardedVideo(val context: Context) {

    private var rewardedAd: RewardedAd? = null

    //test banner ad unit for test mode
    private var testRewardedVideoAdUnit = "ca-app-pub-3940256099942544/5224354917"

    //live ad banner ad unit for live ads
    private var rewardedVideoAdUnit = ""

    private var onRewaded: ((rewardItem: RewardItem) -> Unit)? = null
    private var onAdClose: (() -> Unit)? = null

    /**
     * checks if the test adUnit | real
     * @return adUnit:String
     * @throws NullPointerException
     */
    private fun getAdUnit(): String {
        return if (AdsFlexManager.testMode) {
            testRewardedVideoAdUnit
        } else {
            if (rewardedVideoAdUnit.isEmpty()) {
                throw NullPointerException("Use setAdUnit method for live ads. or enable test mode")
            } else {
                return rewardedVideoAdUnit
            }
        }
    }

    /**
     * this function helps to set the live adUnit id for realtime ads
     * @param adUnit string
     */
    fun setAdUnit(adUnit: String) {
        this.rewardedVideoAdUnit = adUnit
    }


    /**
     * load rewarded ad
     */
    fun loadRewaededAd() {
        RewardedAd.load(
            context,
            getAdUnit(),
            AdsFlexManager.adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                    rewardedAd?.fullScreenContentCallback = fullScreenContentCallback
                }

            })
    }


    /**
     *  showRewardedAd if ad is not loaded it will call the callback that response the ad is closed
     */
    fun showRewardedAd(activity: Activity, onAdClose: (() -> Unit)? = null, onRewarded: ((rewardItem: RewardItem) -> Unit)? = null) {
        if (onAdClose != null) {
            this.onAdClose = onAdClose
        }
        if (onRewarded != null) {
            this.onRewaded = onRewarded
        }

        rewardedAd?.let { ad ->
            ad.show(activity, OnUserEarnedRewardListener { rewardItem ->
                if (onRewarded != null) {
                    val reward = RewardItem(rewardItem.amount, rewardItem.type)
                    onRewarded(reward)
                }
            })
        } ?: run {
            if (onAdClose != null) {
                onAdClose()
            }
        }

    }

    val fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
            rewardedAd = null
            onAdClose?.let { it() }
        }
    }


}