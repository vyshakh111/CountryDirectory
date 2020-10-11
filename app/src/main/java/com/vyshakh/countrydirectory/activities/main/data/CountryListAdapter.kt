package com.vyshakh.countrydirectory.activities.main.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.vyshakh.countrydirectory.R
import kotlinx.android.synthetic.main.item_country_list.view.*
import java.util.*

class CountryListAdapter(private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>(), Filterable {

    private var countryList: List<CountryListItem>? = null
    private var filteredList: List<CountryListItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_country_list, parent, false)
        return CountryViewHolder(itemView)
    }

    override fun getItemCount(): Int = filteredList?.size ?: 0


    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        filteredList?.get(position)?.let {
            holder.bind(it)
        }
    }

    fun setData(countryList: List<CountryListItem>?) {
        this.countryList = countryList
        this.filteredList = countryList
        notifyDataSetChanged()
    }

    inner class CountryViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(item: CountryListItem) {
            itemView.tv_country_name.text = item.countryName
            itemView.tv_country_desc.text = item.description
            itemView.setOnClickListener { itemClickListener.onItemClick(item, adapterPosition) }

        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: CountryListItem?, position: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filteredList = if (charSearch.isEmpty()) {
                    countryList
                } else {
                    val resultList = ArrayList<CountryListItem>()

                    countryList?.forEach { row ->
                        if (row.countryName.toLowerCase(Locale.getDefault())
                                .contains(charSearch.toLowerCase(Locale.getDefault()))
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                results?.values?.apply {
                    filteredList = this as ArrayList<CountryListItem>
                    notifyDataSetChanged()
                }
            }

        }
    }


}