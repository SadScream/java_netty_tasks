package study.oop.netty.nettyfirsttask.server.data.decoders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import study.oop.netty.nettyfirsttask.shared.models.RequestType;
import study.oop.netty.nettyfirsttask.shared.requests.Request;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RequestDecoder extends ReplayingDecoder<Request> {
    private final Charset charset = StandardCharsets.UTF_8;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf,
                          List<Object> list) throws Exception {
        String requestType = readRequestType(byteBuf);
        System.out.println("[Decoding] " + requestType);
        Object data = resolveRequestType(requestType, byteBuf);
        list.add(data);
    }

    private String readRequestType(ByteBuf byteBuf) {
        int strLen = byteBuf.readInt();
        return byteBuf.readCharSequence(strLen, charset).toString();
    }

    private Object resolveRequestType(String requestType, ByteBuf byteBuf) {
        Request request = new Request();
        request.setRequestType(requestType);

        switch (requestType) {
            case RequestType.InitUnits -> {
                return request;
            }
            case RequestType.UnitReachedPoint -> {
                request.setId(byteBuf.readInt());
                return request;
            }
        }

        return null;
    }
}
