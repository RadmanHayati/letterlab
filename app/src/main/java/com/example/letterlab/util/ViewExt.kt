package com.example.letterlab.util

import androidx.appcompat.widget.ForwardingListener
import androidx.appcompat.widget.SearchView

inline fun SearchView.onQueryTextChanged(crossinline listener:(String)-> Unit){
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            listener(newText.orEmpty())
            return true
        }

    })
}