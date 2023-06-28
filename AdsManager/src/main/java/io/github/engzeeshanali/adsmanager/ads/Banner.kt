package io.github.engzeeshanali.adsmanager.ads

import android.content.Context
import android.view.ViewGroup
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import io.github.engzeeshanali.adsmanager.AdsFlexManager
import java.lang.NullPointerException

class Banner(val context: Context) {


    //standard banner sizes
    companion object {
        const val BANNER = "BANNER"
        const val LARGE_BANNER = "LARGE_BANNER"
        const val MEDIUM_RECTANGLE = "MEDIUM_RECTANGLE"
        const val FULL_BANNER = "FULL_BANNER"
        const val LEADERBOARD = "LEADERBOARD"
    }

    //test banner ad unit for test mode
    private var testBannerAdUnit = "ca-app-pub-3940256099942544/6300978111"

    //live ad banner ad unit for live ads
    private var bannerAdUnit = ""

    // adsize
    private var adSize = AdSize.BANNER

    //Google adview
    private val adView = AdView(context)




    /**
     * this function helps to change adSize from default(BANNER) to other
     * @param adSize BANNER.SIZE
     * @return Banner
     */
    fun setAdSize(adSize: String): Banner {
        when (adSize) {
            BANNER -> this.adSize = AdSize.BANNER
            LARGE_BANNER -> this.adSize = AdSize.LARGE_BANNER
            MEDIUM_RECTANGLE -> this.adSize = AdSize.MEDIUM_RECTANGLE
            FULL_BANNER -> this.adSize = AdSize.FULL_BANNER
            LEADERBOARD -> this.adSize = AdSize.LEADERBOARD
            else -> this.adSize = AdSize.BANNER
        }
        return this
    }


    /**
     * this function helps to set the live adUnit id for realtime ads
     * @param adUnit string
     * @return Banner
     */
    fun setAdUnit(adUnit: String): Banner {
        this.bannerAdUnit = adUnit
        return this
    }

    /**
     * load ad request to adview
     * @return Banner
     */
    fun loadBannerAd(): Banner {
        adView.setAdSize(adSize)
        adView.adUnitId = getAdUnit()
        adView.loadAd(AdsFlexManager.adRequest)
        return this
    }

    /**
     * add adview to any viewGroup|container
     * @param container Any View Group
     */
    fun addBannerAdToContainer(container: ViewGroup) {
        container.addView(adView)
    }




    /**
     * checks if the test adUnit | real
     * @return adUnit:String
     * @throws NullPointerException
     */
    private fun getAdUnit(): String {
        return if (AdsFlexManager.testMode) {
            testBannerAdUnit
        } else {
            if (bannerAdUnit.isEmpty()) {
                throw NullPointerException("Use setAdUnit method for live ads. or enable test mode")
            } else {
                return bannerAdUnit
            }
        }
    }

}