package com.blogApp.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.blogApp.R
import com.blogApp.core.BaseViewHolder
import com.blogApp.core.TimeUtils
import com.blogApp.core.hide
import com.blogApp.core.show
import com.blogApp.data.model.Post
import com.blogApp.databinding.PostItemViewBinding
import com.bumptech.glide.Glide

@Suppress("UNREACHABLE_CODE")
class HomeScreenAdapter(private val postList: List<Post>, private val onPostClickListener: OnPostClickListener): RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var postClickListener: OnPostClickListener? = null

    init {
        postClickListener = onPostClickListener
    }

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
            setupProfileInfo(item)
            addPostTimeStamp(item)
            setupPostImage(item)
            tintHeartIcon(item)
            setupLikeCount(item)
            setLikeClickAction(item)
        }


        private fun setupProfileInfo(post: Post){
            Glide.with(context).load(post.poster?.profile_picture).centerCrop().into(binding.profilePicture)
            binding.txtProfileName.text = post.poster?.username
        }

        private fun addPostTimeStamp(post: Post){
            val createdAt = (post.created_at?.time?.div(1000L))?.let {
                TimeUtils.getTimeAgo(it.toInt())
            }
            binding.postTimestamp.text = createdAt
        }

        private fun setupPostImage(post: Post){
            //todos los datos del post se cargan aqui
            Glide.with(context).load(post.post_image).centerCrop().into(binding.postImage)

            if (post.post_description.isEmpty()){
                binding.postDescription.visibility = View.GONE
            }else{
                binding.postDescription.text = post.post_description
            }
        }

        private fun tintHeartIcon(post: Post){
            if (!post.liked){
                binding.btnLike.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_border_24))
                binding.btnLike.setColorFilter(ContextCompat.getColor(context, R.color.white))
            }else{
                binding.btnLike.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_24))
                binding.btnLike.setColorFilter(ContextCompat.getColor(context, R.color.red))
            }
        }

        private fun setupLikeCount(post: Post){
            if (post.likes > 0 ){
                binding.txtLikeCount.show()
                binding.txtLikeCount.text = "${post.likes} likes"
            }else{
                binding.txtLikeCount.hide()
            }
        }

        private fun setLikeClickAction(post: Post){
            binding.btnLike.setOnClickListener{
                if(post.liked) post.apply { liked = false } else post.apply { liked = true }
                tintHeartIcon(post)
                postClickListener?.onLikeButtonCLick(post, post.liked)
            }
        }

    }

}


interface OnPostClickListener{
    fun onLikeButtonCLick(post: Post, liked: Boolean)
}