package com.example.mvvmstructurekotlin.base

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmstructurekotlin.R
import com.example.mvvmstructurekotlin.constants.Constants
import com.mobi.mobidrivers.repository.RepoModel
import com.example.mvvmstructurekotlin.extensions.isInternetAvailable
import org.koin.android.ext.android.inject

open class BaseActivity : AppCompatActivity() {

    private lateinit var mDialog: Dialog
    val repo: RepoModel by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDialog = Dialog(this)
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog.setContentView(R.layout.progress_loader)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
    }



    //TODO Check InternetConnectivity
    fun checkInternet(): Boolean {

        val available = isInternetAvailable()
        if (!available) {
            showInternetDialog()
        }
        return available
    }

    override fun onPause() {
        super.onPause()
        mDialog.let { if (it.isShowing) it.cancel() }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDialog.let { if (it.isShowing) it.cancel() }
    }

    fun showProgressDialog() {
        if (!isFinishing() && !mDialog.isShowing) {
            mDialog.show()
        }
    }

    //TODO Dismiss Dialog
    fun dismissProgressDialog() {
        if (mDialog.isShowing) {
            mDialog.dismiss()
        }
    }



    //TODO Show Internet Dialog
    fun showInternetDialog() {
        val msgDialog = MessageDialog.getInstance()
        val bundle = Bundle()
        bundle.putString(Constants.TEXT_MESSAGES, "Please Check Internet Connection")
        bundle.putString(Constants.OK, "OK")
        msgDialog.setArguments(bundle)
        msgDialog.show(supportFragmentManager, "")
        msgDialog.setListener(object : MessageDialog.OnClick {
            override fun set(ok: Boolean) {
                msgDialog.dismiss()
            }
        })
    }
}