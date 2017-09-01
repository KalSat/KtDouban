package io.ktdouban.data.processor

import io.ktdouban.data.DataRepository
import io.ktdouban.data.entities.MovieCollection
import io.ktdouban.data.entities.MovieCollectionPage
import rx.Observable
import rx.schedulers.Schedulers

/**
 * Created by steve on 17/8/26.
 */
class MovieCollectionsProcessor(dataRepo: DataRepository) {

    private val mDataRepo = dataRepo

    private var mMovieCollectionPage = MovieCollectionPage()

    fun getInTheaterMovieCollection(): Observable<MovieCollection> {
        return mDataRepo.getMoviesInTheater("北京")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .filter {
                    mMovieCollectionPage = it
                    it != null && it.subjects.count() > 0
                }
                .map {
                    val movieCollection = MovieCollection()
                    movieCollection.title = it.title
                    movieCollection.movies = it.subjects
                    movieCollection
                }
    }

}