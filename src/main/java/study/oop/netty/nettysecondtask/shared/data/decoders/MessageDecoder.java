package study.oop.netty.nettysecondtask.shared.data.decoders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import study.oop.netty.nettysecondtask.shared.data.messages.BaseMessage;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("[Decoder] got message, decoding");
        int dataLength = in.readInt();
        byte[] serializedData = new byte[dataLength];
        in.readBytes(serializedData);

        BaseMessage message = BaseMessage.deserialize(serializedData);
        System.out.println("[Decoder] decoded: " + message.messageType);
        out.add(message);
    }
}
