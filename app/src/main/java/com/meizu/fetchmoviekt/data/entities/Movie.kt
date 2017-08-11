package com.meizu.fetchmoviekt.data.entities

import java.io.Serializable
import java.util.*

/**
 * Movie Entity used in the data layer.
 */
class Movie {

    var alt: String? = null
    var id: String? = null
    var title: String? = null
    var original_title: String? = null
    var summary: String? = null
    var images: ImageEntity? = null
    var rating: RatingEntity? = null
    var ratings_count: Int = 0
    var year: String? = null
    var directors: Array<CastEntity>? = null
    var writers: Array<CastEntity>? = null
    var casts: Array<CastEntity>? = null
    var genres: Array<String>? = null
    var countries: Array<String>? = null
    var languages: Array<String>? = null
    var pubdates: Array<String>? = null
    var durations: Array<String>? = null
    var aka: Array<String>? = null

    inner class CastEntity : Serializable {
        var alt: String? = null
        var id: String? = null
        var name: String? = null
        var name_en: String? = null
        var avatars: ImageEntity? = null
    }

    inner class RatingEntity : Serializable {
        var max: Int = 0
        var average: Float = 0.toFloat()
        var min: Int = 0
        var details: HashMap<String, Int>? = null
        var stars: String? = null
    }

    inner class ImageEntity : Serializable {
        var small: String? = null
        var large: String? = null
        var medium: String? = null
    }
}
