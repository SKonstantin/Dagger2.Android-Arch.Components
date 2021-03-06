package com.ns.daggernewway.di.ui.main.post

import androidx.lifecycle.ViewModelProviders
import com.ns.daggernewway.di.common.scopes.FragmentScope
import com.ns.daggernewway.interactor.getcomments.IGetCommentsInteractor
import com.ns.daggernewway.ui.common.viewmodel.postcomments.CommentsViewModel
import com.ns.daggernewway.ui.common.viewmodel.postcomments.CommentsViewModelFactory
import com.ns.daggernewway.ui.main.post.PostCommentsFragment
import dagger.Module
import dagger.Provides

@Module
class PostCommentsModule {

    @Provides
    @FragmentScope
    fun postDetailsViewModel(fragment: PostCommentsFragment,
                             factory: CommentsViewModelFactory): CommentsViewModel {
        return ViewModelProviders.of(fragment, factory).get(CommentsViewModel::class.java)
    }

    @Provides
    @FragmentScope
    fun providePostCommentsViewModelFactory(interactor: IGetCommentsInteractor,
                                            fragment: PostCommentsFragment): CommentsViewModelFactory {
        return CommentsViewModelFactory(interactor, fragment.fullPost, fragment.savedInstanceState)
    }

}
