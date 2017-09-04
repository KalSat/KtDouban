package io.ktdouban.data.entities

/**
 * Movie list Entity used in the data layer.
 */
internal class MovieCollectionPage : EntitiesPage<Movie>() {

    var title: String = ""

    fun toModel(): MovieCollection {
        val movieCollection = MovieCollection()
        movieCollection.title = title
        movieCollection.movies = subjects
        return movieCollection
    }
}
