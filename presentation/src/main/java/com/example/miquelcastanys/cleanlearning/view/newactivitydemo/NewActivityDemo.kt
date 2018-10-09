package com.example.miquelcastanys.cleanlearning.view.newactivitydemo

import android.os.Bundle
import android.widget.Toast
import com.example.miquelcastanys.cleanlearning.view.base.BaseActivity

class NewActivityDemo : BaseActivity()  {


    override fun createFragmentAndSettingTAG() {
        currentFragment = NewActivityDemoFragment.newInstance()
        currentTag = NewActivityDemoFragment.TAG
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Movie detail")
    }


    override fun onConnectivityChanges(isConnected: Boolean) {
        super.onConnectivityChanges(isConnected)
        Toast.makeText(this, "NewActivityDemo INTERNET: $isConnected", Toast.LENGTH_SHORT).show()
    }

}
