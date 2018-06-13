package com.zamkovenko.time4parent.messanging;

import com.zamkovenko.utils.model.Message;
import com.zamkovenko.utils.model.serializer.MessageSerializer;
import com.zamkovenko.utils.model.serializer.SmsMessageSerializer;
import com.zamkovenko.time4parent.manager.SocketServerManager;
import com.zamkovenko.utils.MessageSender;

/**
 * User: Yevgeniy Zamkovenko
 * Date: 17.12.2017
 */

public class WifIMessageSender implements MessageSender {

    @Override
    public void sendMessage(Message message) {
        MessageSerializer<String> messageSerializer = new SmsMessageSerializer();

        SocketServerManager.getInstance().sendMessage(messageSerializer.serialize(message));
    }
}
