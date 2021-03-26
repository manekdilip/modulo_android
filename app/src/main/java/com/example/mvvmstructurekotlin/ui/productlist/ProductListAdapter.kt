package com.example.mvvmstructurekotlin.ui.productlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener
import com.crystal.crystalrangeseekbar.widgets.BubbleThumbSeekbar
import com.example.mvvmstructurekotlin.R
import com.example.mvvmstructurekotlin.repository.rest.response.productlist.ProductListResponse
import com.github.angads25.toggle.widget.LabeledSwitch
import kotlinx.android.synthetic.main.item_productlist.view.*


// Search for restaurants nearby

class ProductListAdapter(
    val productlist: MutableList<ProductListResponse.Device>,
    var listener: OnSelect
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    var templist: MutableList<ProductListResponse.Device> =
        mutableListOf<ProductListResponse.Device>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_productlist,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind(position)

    override fun getItemCount() = productlist.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvid = view.tv_id
        var tvdevicename = view.tv_devicename
        var tvproducttype = view.tv_product_type
        var textMinvalue = view.tv_Minvalue
        var imgdelete = view.img_delete
        var seekbar = view.seekbar as BubbleThumbSeekbar
        var switchon = view.switch_on as LabeledSwitch

        fun onBind(pos: Int) {
            var mModel: ProductListResponse.Device = productlist[adapterPosition]
            tvid.text = "Id No : " + mModel.id
            tvdevicename.text = mModel.deviceName
            tvproducttype.text = "Product Type : "+mModel.productType

            if (mModel.intensity != 0) {
                seekbar.setMinStartValue(mModel.intensity.toFloat()).apply();
                textMinvalue.setText(
                    mModel.intensity.toString()
                )
            } else if (mModel.position != 0) {
                seekbar.setMinStartValue(mModel.position.toFloat()).apply();
                textMinvalue.setText(
                    mModel.position.toString()
                )
            } else if (mModel.temperature != 0) {
                seekbar.setMinStartValue(mModel.temperature.toFloat()).apply();
                textMinvalue.setText(
                    mModel.temperature.toString()
                )
            }
            if (!mModel.mode.isNullOrBlank()) {
                switchon.visibility = View.VISIBLE

                if (mModel.mode == "OFF") {
                    switchon.isOn = false
                } else {
                    switchon.isOn = true

                }
            }
            imgdelete.setOnClickListener {
                productlist.removeAt(pos)
                notifyDataSetChanged()
            }

            seekbar.setOnSeekbarChangeListener(OnSeekbarChangeListener { minValue ->
                textMinvalue.setText(
                    minValue.toString()
                )
            })
        }
    }

    interface OnSelect {
        fun onItemSelect(model: ProductListResponse.Device, type: String)
    }

    fun insert(item: List<ProductListResponse.Device>) {
        productlist.clear()
        if (templist.size > 0) {
            templist.clear()
        }
        productlist.addAll(item)
        templist.addAll(item)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun search(newText: String) {
        var newText = newText
        newText = newText.replace("[", "");
        newText = newText.replace("]", "");
        newText = newText.replace(" ", "");

        val filteredModelList: MutableList<ProductListResponse.Device> = ArrayList()
        val strings = newText.split(",").toTypedArray()
        for (str in strings) {
            for (i in 0 until templist.size) {
                if (templist.get(i).productType.contains(str)) {
                    filteredModelList.add(templist[i])
                }
            }
        }
        if (filteredModelList.size > 0) {
            productlist.clear()
            productlist.addAll(filteredModelList)
        }

        notifyDataSetChanged()
    }

}



