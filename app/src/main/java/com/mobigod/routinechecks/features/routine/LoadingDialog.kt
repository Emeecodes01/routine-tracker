package com.mobigod.routinechecks.features.routine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mobigod.routinechecks.databinding.LoadingLayoutBinding

class LoadingDialog: BottomSheetDialogFragment() {

    lateinit var loadingLayoutBinding: LoadingLayoutBinding

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        loadingLayoutBinding = LoadingLayoutBinding.inflate(inflater, container, false)
        return loadingLayoutBinding.root
    }
}