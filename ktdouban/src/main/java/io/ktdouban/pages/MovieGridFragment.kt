package io.ktdouban.pages

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
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
    var movieList: ObservableArrayList<Movie> = ObservableArrayList()
    var movieStringList: ObservableArrayList<String> = ObservableArrayList()
    var itemBinding: ItemBinding<String> = ItemBinding.of(BR.item, R.layout.item_movie_grid)
    // other field
    private lateinit var subscription: Subscription
    var gson = Gson()

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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            if (movieList.isNotEmpty()) {
                                movieList.clear()
                            }
                            movieList.addAll(it.subjects)
                            if (movieStringList.isNotEmpty()) {
                                movieStringList.clear()
                            }
                            for (movie in movieList) {
                                movieStringList.add(gson.toJson(movie))
                            }
                        },
                        onError = {
                            it.printStackTrace()
                            Toast.makeText(context, "can't get movies",
                                    Toast.LENGTH_SHORT).show()
                        }
                )
    }

}// Required empty public constructor
