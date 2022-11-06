package com.mk.pwdguard.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mk.pwdguard.databinding.ListItemBinding
import com.mk.pwdguard.model.domain.DomainModels.*
import com.mk.pwdguard.model.smartShrink

class CredentialAdapter: ListAdapter<Credential, CredentialAdapter.ItemViewHolder>(DiffUtilCallBack()){


    //lambda
    private var copyBtnClickListener: ((text: String) -> Unit)? = null
    private var deleteBtnClickListener: ((id: Long) -> Unit)? = null
    private var clickListener:(( credential:Credential)->Unit)? = null

    //viewHolder
    class ItemViewHolder private constructor(val binding:ListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(
            credential: Credential,
            clickListener: ((credential: Credential) -> Unit)?,
            deleteBtnClickListener: ((id: Long) -> Unit)?,
            copyBtnClickListener: ((text: String) -> Unit)?
        )
        {


            //shorten to avoid overflow
            binding.titleTv.text = credential.title.smartShrink()
            binding.usernameTv.text = credential.username.smartShrink()
            binding.passwordTv.text = credential.password.smartShrink()
            binding.siteTv.text = credential.site.smartShrink()

            //if clicklistener is not null
            clickListener?.let {
                binding.root.setOnClickListener{
                    clickListener.invoke(credential)
                }
            }
             deleteBtnClickListener?.let {
                binding.deleteBtn.setOnClickListener{
                    deleteBtnClickListener.invoke(credential.id)
                }
            }
            copyBtnClickListener?.let {
                binding.copyUsernameBtn.setOnClickListener{
                    copyBtnClickListener.invoke(credential.username)
                }
                binding.copyPasswordBtn.setOnClickListener{
                    copyBtnClickListener.invoke(credential.password)
                }
            }

        }
        companion object{
            fun from(parent: ViewGroup):ItemViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater,parent,false)
                return ItemViewHolder(binding)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder = ItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int)
    {
        holder.bind(getItem(position), clickListener,deleteBtnClickListener,copyBtnClickListener)
    }

    fun setOnclickListener(clickListener:(( credential:Credential)->Unit)){
        this.clickListener = clickListener
    }
    fun setDeleteBtnClickListener(clickListener:(( id:Long)->Unit)){
        this.deleteBtnClickListener = clickListener
    }
    fun setCopyBtnClickListener(clickListener:(( text:String)->Unit)){
        this.copyBtnClickListener = clickListener
    }
}
class DiffUtilCallBack: DiffUtil.ItemCallback<Credential>(){
    override fun areItemsTheSame(oldItem: Credential, newItem: Credential): Boolean = oldItem.id==newItem.id
    override fun areContentsTheSame(oldItem: Credential, newItem: Credential): Boolean = oldItem==newItem
}
