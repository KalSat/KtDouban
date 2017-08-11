package com.meizu.fetchmoviekt.data.entities

import java.util.*

/**
 * Created by chengbiao on 17/8/9.
 */
open class EntitiesPage<T> {

    var total: Int = 0

    var start: Int = 0

    var count: Int = 0

    var subjects: ArrayList<T> = ArrayList()

}
