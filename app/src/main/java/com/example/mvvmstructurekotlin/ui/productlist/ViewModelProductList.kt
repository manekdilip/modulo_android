package com.example.mvvmstructurekotlin.ui.productlist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmstructurekotlin.common.ProgressState
import com.example.mvvmstructurekotlin.repository.rest.response.productlist.ProductListResponse
import com.mobi.mobidrivers.repository.RepoModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewModelProductList(val repo: RepoModel) : ViewModel(), ProductListAdapter.OnSelect {

    var _progressDialog: MutableLiveData<ProgressState> = MutableLiveData()

    var _productlistResponse: MutableLiveData<ProductListResponse> = MutableLiveData()
    var adapter: ProductListAdapter = ProductListAdapter(mutableListOf(), this)

    fun search(query: String) {

        // filter recycler view when query submitted
        adapter.search(query)

    }

    //TODO API Calling
    fun hitProductListAPI() {
        _progressDialog.value = ProgressState.SHOW

        repo.api.ProductList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    _progressDialog.value = ProgressState.HIDE
                    if (it.devices.size > 0) {
                        _productlistResponse.value = it
                        insertdatainadapter(it.devices)
                        CoroutineScope(Dispatchers.IO).launch {
                            repo.appDatabase.insertEmployee(it.devices)
                        }


                    }

                    println(it)
                },

                onComplete = {
                    _progressDialog.value = ProgressState.HIDE

                    println("Completed")
                },

                onError = {
                    _progressDialog.value = ProgressState.HIDE
                    //TODO Set Error Response

                }
            )
    }

     fun insertdatainadapter(it: MutableList<ProductListResponse.Device>) {
        adapter.insert(it)
    }

    override fun onItemSelect(model: ProductListResponse.Device, type: String) {

    }


}

//TODO ViewModel Factory Using passing Data like constructor
class ViewModelProductListFactory(var repo: RepoModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = ViewModelProductList(
        repo
    ) as T

}