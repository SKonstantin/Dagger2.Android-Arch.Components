package com.ns.daggernewway.ui.main.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ns.daggernewway.R
import com.ns.daggernewway.entity.ui.FullPost
import com.ns.daggernewway.ui.common.viewmodel.getfeed.FeedViewModel
import com.ns.daggernewway.ui.common.viewmodel.status.IStatus.State
import com.ns.daggernewway.ui.main.post.PostCommentsFragment
import com.ns.daggernewway.ui.utils.recyclerview.OnItemClickListener
import com.ufkoku.archcomponents.DaggerArchFragment
import kotlinx.android.synthetic.main.fragment_feed.*
import javax.inject.Inject

class FeedFragment : DaggerArchFragment(), OnItemClickListener<FullPost> {

    @Inject
    protected lateinit var viewModel: FeedViewModel

    private var adapter: FeedAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FeedAdapter(layoutInflater, this)
        feed.adapter = adapter
        feed.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        viewModel.getFeed().observe(viewLifecycleOwner, Observer {
            if (it == null){
                return@Observer
            }

            @Suppress("NON_EXHAUSTIVE_WHEN")
            when(it.state){
                State.COMPLETED -> adapter!!.postItems(it.data!!)
                State.ERROR -> Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    override fun onItemClicked(item: FullPost) {
        fragmentManager?.beginTransaction()
                ?.replace(R.id.mainRoot, PostCommentsFragment.getInstance(item))
                ?.addToBackStack(null)
                ?.commit()
    }

}