package com.mobigod.routinechecks.data

import com.mobigod.routinechecks.data.local.PreferenceManager
import com.mobigod.routinechecks.data.local.RoutineDb
import javax.inject.Inject

class Repository @Inject constructor(var preferenceManager: PreferenceManager, database: RoutineDb?) {


}