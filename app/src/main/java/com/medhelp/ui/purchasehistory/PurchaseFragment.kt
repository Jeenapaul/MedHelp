package com.medhelp.ui.purchasehistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.medhelp.R
import com.medhelp.ui.purchasehistory.adapter.PurchaseHistoryAdapter

class PurchaseFragment : Fragment() {

lateinit var recyclerView: RecyclerView
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_purchase, container, false)
        recyclerView=root.findViewById(R.id.rvPurchaseHistory)
        recyclerView.adapter=PurchaseHistoryAdapter()
        return root
    }
}