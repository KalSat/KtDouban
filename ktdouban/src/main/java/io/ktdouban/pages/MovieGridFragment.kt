package io.ktdouban.pages

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import io.ktdouban.BR
import io.ktdouban.R
import io.ktdouban.data.DataStore
import io.ktdouban.data.entities.Movie
import io.ktdouban.databinding.FragmentMovieGridBinding
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.subscribeBy
import rx.schedulers.Schedulers

/**
 * A simple [Fragment] subclass.
 * Use the [MovieGridFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieGridFragment : Fragment() {
    // binding data
    var movieList: DiffObservableList<Movie> = DiffObservableList(object : DiffObservableList.Callback<Movie> {
        override fun areItemsTheSame(oldItem: Movie?, newItem: Movie?): Boolean {
            return oldItem?.id == newItem?.id
        }

        override fun areContentsTheSame(oldItem: Movie?, newItem: Movie?): Boolean {
            return oldItem == newItem
        }
    })
    var itemBinding = ItemBinding.of<Movie>(BR.item, R.layout.item_movie_grid)
            .bindExtra(BR.gson, Gson())
    // other field
    private lateinit var subscription: Subscription

    companion object {
        fun newInstance(): MovieGridFragment {
            val fragment = MovieGridFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentMovieGridBinding>(inflater,
                R.layout.fragment_movie_grid, container, false)
        binding.vm = this

        loadData()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (!subscription.isUnsubscribed) {
            subscription.unsubscribe()
        }
    }

    private fun loadData() {
        subscription = DataStore.getMoviesInTheater("北京")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .filter {
                    it != null && it.subjects.count() > 0
                }
                .map {
                    Pair(it.subjects, movieList.calculateDiff(it.subjects))
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            movieList.update(it.first, it.second)
                        },
                        onError = {
                            it.printStackTrace()
                            Toast.makeText(context, "can't get movies",
                                    Toast.LENGTH_SHORT).show()
                        }
                )
    }

}// Required empty public constructor
