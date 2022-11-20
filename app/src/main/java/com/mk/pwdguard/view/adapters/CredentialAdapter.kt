package com.mk.pwdguard.view.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mk.pwdguard.R
import com.mk.pwdguard.databinding.ListItemBinding
import com.mk.pwdguard.model.domain.DomainModels.*

class CredentialAdapter: ListAdapter<Credential, CredentialAdapter.ItemViewHolder>(DiffUtilCallBack()){

    private var clickListener:CredentialAdapter.ClickListener? = null

    //viewHolder
    class ItemViewHolder private constructor(val binding:ListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(credential: Credential,clickListener: ClickListener?)
        {
            binding.credential = credential

            //if click-listener is not null
            clickListener?.let { clickListeners ->
                val expandCollapseAction = { _:View->
                     if(binding.cardView.tag == "collapsed") {
                        binding.cardView.tag = "expanded"
                         binding.detailedGroup.visibility = View.VISIBLE
                         binding.expColBtn.setImageResource(R.drawable.ic_baseline_expand_less)
                    }
                    else
                    {
                        binding.cardView.tag = "collapsed"
                        binding.detailedGroup.visibility = View.GONE
                        binding.expColBtn.setImageResource(R.drawable.ic_baseline_expand_more)
                    }
                }

                binding.root.setOnClickListener(expandCollapseAction)
                binding.expColBtn.setOnClickListener(expandCollapseAction)

                binding.deleteBtn.setOnClickListener{
                    clickListeners.onDeleteBtnClicked(credential.id)
                }
                binding.copyUsernameBtn.setOnClickListener{
                    clickListeners.onCopyBtnClicked(credential.username)
                }
                binding.copyPasswordBtn.setOnClickListener{
                    clickListeners.onCopyBtnClicked(credential.password)
                }
                binding.siteTv.setOnClickListener{
                    clickListeners.onSiteViewClicked(credential.site)
                }
                binding.editBtn.setOnClickListener{
                    clickListeners.onEditClicked(credential.id)
                }
            }

        }
        companion object{
            fun from(parent: ViewGroup):ItemViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater,parent,false)
                //for url underline
                binding.siteTv.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                return ItemViewHolder(binding)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder = ItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int)
    {
        holder.bind(getItem(position), clickListener)
    }


    interface ClickListener{
        fun onDeleteBtnClicked( id:Long)
        fun onCopyBtnClicked( text:String)
        fun onSiteViewClicked( url:String)
        fun onEditClicked(id:Long)
    }
    fun setOnClickListeners(clickListener: ClickListener){
        this.clickListener = clickListener
    }
}

class DiffUtilCallBack: DiffUtil.ItemCallback<Credential>(){
    override fun areItemsTheSame(oldItem: Credential, newItem: Credential): Boolean = oldItem.id==newItem.id
    override fun areContentsTheSame(oldItem: Credential, newItem: Credential): Boolean = oldItem==newItem
}
