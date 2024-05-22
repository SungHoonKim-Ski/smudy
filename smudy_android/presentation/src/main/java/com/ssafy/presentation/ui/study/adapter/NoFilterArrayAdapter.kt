package com.ssafy.presentation.ui.study.adapter

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

class NoFilterArrayAdapter<T>(context: Context, layout: Int, var items: List<T>) :
    ArrayAdapter<T>(context, layout, items) {
    private var noFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            return FilterResults().apply {
                values = items
                count = items.size
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results != null && results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }

        override fun convertResultToString(resultValue: Any?): CharSequence {
            return resultValue.toString()
        }
    }

    override fun getFilter(): Filter {
        return noFilter
    }
}