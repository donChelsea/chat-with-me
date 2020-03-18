package com.katsidzira.chat_with_me

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.katsidzira.chat_with_me.databinding.ItemMessageSendBinding
import com.katsidzira.chat_with_me.model.Message
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.databinding.BindableItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val messageAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = messageAdapter
        populateData()

        button.setOnClickListener {
            val message = Message(text = editText.text.toString(), sendBy = "me")
            val sendMessageItem = SendMessageItem(message)

            messageAdapter.add(sendMessageItem)
            editText.text.clear()

            receiveAutoResponse()
        }
    }

    private fun populateData() {
        val data = listOf<Message>()
        data.forEach {
            if (it.sendBy == "me") {
                messageAdapter.add(SendMessageItem(it))
            } else {
                messageAdapter.add(ReceiveMessageItem(it))
            }
        }
    }

    private fun receiveAutoResponse() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            val receive = Message(text = "Salut l'amis j'espere que vous allez bien, je suis tres bien j'ai manger to day", sendBy = "me")
            val receiveItem = ReceiveMessageItem(receive)

            messageAdapter.add(receiveItem)
        }
    }
}

class SendMessageItem(private val message: Message) : BindableItem<ItemMessageSendBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_message_send
    }

    override fun bind(viewBinding: ItemMessageSendBinding, position: Int) {
        viewBinding.message = message
    }
}

class ReceiveMessageItem(private val message: Message) : BindableItem<ItemMessageReceiveBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_message_receive
    }

    override fun bind(viewBinding: ItemMessageReceiveBinding, position: Int) {
        viewBinding.message = message
    }
}