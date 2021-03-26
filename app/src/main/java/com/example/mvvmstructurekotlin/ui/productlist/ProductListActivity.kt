package com.example.mvvmstructurekotlin.ui.productlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmstructurekotlin.R
import com.example.mvvmstructurekotlin.base.BaseActivity
import com.example.mvvmstructurekotlin.common.ProgressState
import com.example.mvvmstructurekotlin.databinding.ActivityProductListBinding
import java.util.*
import kotlin.collections.ArrayList

class ProductListActivity : BaseActivity() {
    lateinit var binding: ActivityProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_product_list
        )
        setSupportActionBar(binding.toolbar)
        //TODO Create View ModelFactory
        val vm: ViewModelProductList by lazy {
            ViewModelProviders.of(this, ViewModelProductListFactory(repo))
                .get(ViewModelProductList::class.java)
        }

        binding.productlistVM = vm
        binding.lifecycleOwner = this
        setRecycleView()


        if (repo.appDatabase.getuserdata().size > 0) {
            binding.productlistVM!!.insertdatainadapter(repo.appDatabase.getuserdata())
        } else {
            if (checkInternet()) {
                binding.productlistVM!!.hitProductListAPI()
            }
        }

        vm._progressDialog.observe(this, androidx.lifecycle.Observer {
            when (it) {
                ProgressState.SHOW -> showProgressDialog()
                else -> dismissProgressDialog()
            }
        })

    }

    private fun setRecycleView() {
        binding.rvProductlist.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvProductlist.adapter = binding.productlistVM!!.adapter
        //credit_rvList.adapter=CreditAdapter()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                filterdialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun filterdialog() {

        val items = arrayOf(
            this.resources.getString(R.string.light),
            this.resources.getString(R.string.rollershutter),
            this.resources.getString(R.string.heater)
        )
        val selectedList = ArrayList<Int>()
        val builder = AlertDialog.Builder(this)



        builder.setTitle(this.resources.getString(R.string.this_is_list_of_product))
        builder.setMultiChoiceItems(
            items, null
        ) { dialog, which, isChecked ->
            if (isChecked) {
                selectedList.add(which)
            } else if (selectedList.contains(which)) {
                selectedList.remove(Integer.valueOf(which))
            }
        }

        builder.setPositiveButton("DONE") { dialogInterface, i ->
            val selectedStrings = ArrayList<String>()

            for (j in selectedList.indices) {
                selectedStrings.add(items[selectedList[j]])
            }

            binding.productlistVM!!.search(Arrays.toString(selectedStrings.toTypedArray()))

        }

        builder.show()

    }


}