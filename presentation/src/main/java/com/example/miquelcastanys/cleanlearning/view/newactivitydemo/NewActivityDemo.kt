package com.example.miquelcastanys.cleanlearning.view.newactivitydemo

import android.os.Bundle
import android.widget.Toast
import com.example.miquelcastanys.cleanlearning.view.base.BaseActivity
import com.example.miquelcastanys.cleanlearning.view.newactivitydemo.ui.NewActivityDemoFragment

class NewActivityDemo : BaseActivity()  {


    override fun createFragmentAndSettingTAG() {
        currentFragment = NewActivityDemoFragment.newInstance()
        currentTag = NewActivityDemoFragment.TAG
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("CACA DE LA VACA")
    }


    override fun onConnectivityChanges(isConnected: Boolean) {
        super.onConnectivityChanges(isConnected)
        Toast.makeText(this, "NewActivityDemo INTERNET: $isConnected", Toast.LENGTH_SHORT).show()
    }

}
