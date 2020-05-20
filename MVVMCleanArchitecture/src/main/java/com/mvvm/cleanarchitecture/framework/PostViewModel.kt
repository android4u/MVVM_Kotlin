package com.mvvm.cleanarchitecture.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mvvmcore.data.PostModel
import com.mvvmcore.repository.PostDataRepository
import com.mvvmcore.usecases.AddPost
import com.mvvmcore.usecases.GetPost
import com.mvvmcore.usecases.GetPosts
import com.mvvmcore.usecases.RemovePost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private  val coroutineScope = CoroutineScope(Dispatchers.IO)
    val repository = PostDataRepository(RoomPostDataSource(application))
    val currentPost = MutableLiveData<PostModel?>()
    private val useCases = UseCases(
        AddPost(repository),
        GetPost(repository),
        GetPosts(repository),
        RemovePost(repository)
    )

    private val saved = MutableLiveData<Boolean>()


    fun savePost(postModel: PostModel){
        coroutineScope.launch {
            useCases.addPost(postModel)
            saved.postValue(true)

        }
    }


    fun getPost(id : Long){
        coroutineScope.launch {
           var post =  useCases.getPost(id)
            currentPost.postValue(post)
        }
    }


    fun deletePost(postModel: PostModel){
        coroutineScope.launch {
            useCases.removePost(postModel)
            saved.postValue(true)
        }
    }

    fun fetchSavedLiveData():LiveData<Boolean> = saved


    fun fetchCurrentPostLiveData():LiveData<PostModel?> = currentPost
}