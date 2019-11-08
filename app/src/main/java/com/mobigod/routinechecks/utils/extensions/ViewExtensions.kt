package com.mobigod.routinechecks.utils.extensions

import android.os.Handler
import android.view.View


fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

/**
 * @author Emmanuel Ozibo
 * @param timemilli time to wait before we execute the code
 * */
fun View.hideWithTime(timemilli: Long){
    Handler().postDelayed({
        visibility = View.GONE
    }, timemilli)
}


fun View.isShowing() = visibility == View.VISIBLE

