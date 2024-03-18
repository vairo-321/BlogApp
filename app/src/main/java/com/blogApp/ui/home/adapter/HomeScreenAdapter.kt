package com.blogApp.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blogApp.core.BaseViewHolder
import com.blogApp.core.TimeUtils
import com.blogApp.data.model.Post
import com.blogApp.databinding.PostItemViewBinding
import com.bumptech.glide.Glide

@Suppress("UNREACHABLE_CODE")
class HomeScreenAdapter(private val postList: List<Post>): RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = PostItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeScreenViewHolder(itemBinding, parent.context)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is HomeScreenViewHolder -> holder.bind(postList[position])
        }
    }

    private inner class HomeScreenViewHolder(
        val binding: PostItemViewBinding,
        val context: Context
    ): BaseViewHolder<Post>(binding.root){
        override fun bind(item: Post) {
            //todos los datos del post se cargan aqui
            Glide.with(context).load(item.post_image).centerCrop().into(binding.postImage)
            Glide.with(context).load(item.profile_picture).centerCrop().into(binding.profilePicture)
            binding.txtProfileName.text = item.profile_name
            if (item.post_description.isEmpty()){
                binding.postDescription.visibility = View.GONE
            }else{
                binding.postDescription.text = item.post_description
            }

            val createdAt = (item.created_at?.time?.div(1000L))?.let {
                TimeUtils.getTimeAgo(it.toInt())
            }
            binding.postTimestamp.text = createdAt
        }
    }

}