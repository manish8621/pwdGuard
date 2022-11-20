package com.mk.pwdguard.view.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mk.pwdguard.R
import com.mk.pwdguard.databinding.ListHeaderBinding
import com.mk.pwdguard.databinding.ListItemBinding
import com.mk.pwdguard.model.ListItem
import com.mk.pwdguard.model.domain.DomainModels
import com.mk.pwdguard.model.domain.DomainModels.*

const val HEADER = 0
const val ITEM = 1


class CredentialAdapter: ListAdapter<ListItem, RecyclerView.ViewHolder>(DiffUtilCallBack()){

    private var clickListener:ClickListener? = null

    //viewHolders
    //header
    class HeaderViewHolder private constructor(val binding:ListHeaderBinding): RecyclerView.ViewHolder(binding.root){
        companion object{
            fun from(parent: ViewGroup):HeaderViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListHeaderBinding.inflate(layoutInflater,parent,false)
                return HeaderViewHolder(binding)
            }
        }
    }
    //item
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


    override fun getItemViewType(position: Int): Int {
        return if(position==0) HEADER else ITEM
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM -> ItemViewHolder.from(parent)
            HEADER -> HeaderViewHolder.from(parent)
            else -> throw IllegalArgumentException("Illegal arg in onCreate viewHolder credential adapter")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ItemViewHolder)
            holder.bind((getItem(position) as ListItem.CredentialItem).credential,clickListener)

    }

    fun addHeaderAndSubmitList(list: List<Credential>?){
        val items = when(list){
            null -> listOf(ListItem.HeaderItem)
            else -> listOf(ListItem.HeaderItem)+(list.map { ListItem.CredentialItem(it) })
        }

        println()
        submitList(items)
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

class DiffUtilCallBack: DiffUtil.ItemCallback<ListItem>(){
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean = oldItem.id==newItem.id
    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean = oldItem==newItem
}
