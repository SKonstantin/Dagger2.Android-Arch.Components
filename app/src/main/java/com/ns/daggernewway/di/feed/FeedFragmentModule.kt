package com.ns.daggernewway.di.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ns.daggernewway.di.common.scopes.FragmentScope
import com.ns.daggernewway.interactor.getfeed.IGetFeedInteractor
import com.ns.daggernewway.ui.main.feed.FeedFragment
import com.ns.daggernewway.ui.main.feed.FeedViewModel
import dagger.Module
import dagger.Provides

@Module
class FeedFragmentModule {

    @Provides
    @FragmentScope
    fun provideFeedViewModel(fragment: FeedFragment,
                             factory: FeedViewModelFactory): FeedViewModel {
        return ViewModelProviders.of(fragment, factory).get(FeedViewModel::class.java)
    }

    @Provides
    @FragmentScope
    fun provideFeedViewModelFactory(interactor: IGetFeedInteractor): FeedViewModelFactory {
        return FeedViewModelFactory(interactor)
    }

    class FeedViewModelFactory(private val interactor: IGetFeedInteractor) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FeedViewModel(interactor) as T
        }

    }

}
