package study.oop.netty.nettysecondtask.shared.data.encoders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import study.oop.netty.nettysecondtask.shared.data.messages.BaseMessage;

public class MessageEncoder extends MessageToByteEncoder<BaseMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, BaseMessage message, ByteBuf out) throws Exception {
        System.out.println("[Encoder] Start data encoding: " + message.messageType);
        byte[] serializedData = message.serialize();
        out.writeInt(serializedData.length);
        out.writeBytes(serializedData);
        System.out.println("[Encoder] Data encoded: " + message.messageType);
    }
}
