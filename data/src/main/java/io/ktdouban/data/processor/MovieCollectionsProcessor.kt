package io.ktdouban.data.processor

import io.ktdouban.data.DataRepository
import io.ktdouban.data.entities.MovieCollection
import io.reactivex.Observable
import io.reactivex.functions.Function4

/**
 * Created by steve on 17/8/26.
 */
class MovieCollectionsProcessor(dataRepo: DataRepository) {

    private val mDataRepo = dataRepo

    fun getMovieCollections(): Observable<ArrayList<MovieCollection>> {
        return Observable.zip(
                mDataRepo.getMoviesWeekly().map { it.toModel() },
                mDataRepo.getMoviesNew().map { it.toModel() },
                mDataRepo.getMoviesInTheater().map { it.toModel() },
                mDataRepo.getMoviesComingSoon().map { it.toModel() },
                Function4 { s1, s2, s3, s4 ->
                    val movieCollections = ArrayList<MovieCollection>()
                    if (s1.movies.isNotEmpty()) {
                        movieCollections.add(s1)
                    }
                    if (s2.movies.isNotEmpty()) {
                        movieCollections.add(s2)
                    }
                    if (s3.movies.isNotEmpty()) {
                        movieCollections.add(s3)
                    }
                    if (s4.movies.isNotEmpty()) {
                        movieCollections.add(s4)
                    }
                    movieCollections
                }
        )
    }

}