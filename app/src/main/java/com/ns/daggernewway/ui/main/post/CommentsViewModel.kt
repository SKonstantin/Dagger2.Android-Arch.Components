package com.ns.daggernewway.ui.main.post

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelStoreOwner
import com.ns.daggernewway.entity.rest.Comment
import com.ns.daggernewway.entity.ui.FullPost
import com.ns.daggernewway.interactor.getcomments.IGetCommentsInteractor
import com.ns.daggernewway.ui.base.viewmodel.SavableViewModel
import com.ufkoku.mvp.viewstate.autosavable.AutoSavable
import com.ufkoku.mvp.viewstate.autosavable.DontSave
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

@AutoSavable(includeSuper = false)
class CommentsViewModel : SavableViewModel {

    lateinit var post: FullPost
        private set

    @DontSave
    private val interactor: IGetCommentsInteractor

    @DontSave
    private lateinit var comments: MutableLiveData<List<Comment>>

    @DontSave
    private var getCommentsDisposable: Disposable? = null

    constructor(app: Application,
                interactor: IGetCommentsInteractor,
                post: FullPost) : super(app) {
        this.interactor = interactor
        this.post = post
    }

    constructor(app: Application,
                interactor: IGetCommentsInteractor,
                owner: ViewModelStoreOwner,
                savedInstanceState: Bundle) : super(app, owner, savedInstanceState) {
        this.interactor = interactor
    }

    override fun save(bundle: Bundle) {
        CommentsViewModelSaver.save(this, bundle)
    }

    override fun restore(bundle: Bundle) {
        CommentsViewModelSaver.restore(this, bundle)
    }

    fun getComments(): LiveData<List<Comment>> {
        if (!::comments.isInitialized) {
            comments = MutableLiveData()
            loadComments()
        }
        return comments
    }

    private fun loadComments() {
        getCommentsDisposable?.dispose()

        getCommentsDisposable = interactor.getComments(post.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { res, ex ->
                    if (res != null) {
                        comments.value = res
                    } else if (ex != null) {
                        //handle ex
                    }
                }
    }

    override fun onCleared() {
        super.onCleared()
        getCommentsDisposable?.dispose()
    }

}