package study.oop.netty.nettyfirsttask.client.data.encoders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import study.oop.netty.nettyfirsttask.shared.models.RequestType;
import study.oop.netty.nettyfirsttask.shared.requests.Request;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class RequestEncoder extends MessageToByteEncoder<Request> {
    private final Charset charset = StandardCharsets.UTF_8;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Request request,
                          ByteBuf byteBuf) throws Exception {
        resolveRequest(request, byteBuf);
//        System.out.println("[Resolved request] " + request.getRequestType());
    }

    private void resolveRequest(Request request, ByteBuf byteBuf) {
        switch (request.getRequestType()) {
            case RequestType.InitUnits -> {
                writeRequestType(request.getRequestType(), byteBuf);
            }
            case RequestType.UnitReachedPoint -> {
                writeRequestType(request.getRequestType(), byteBuf);
                byteBuf.writeInt(request.getId());
            }
        }
    }

    private void writeRequestType(String requestType, ByteBuf byteBuf) {
        byteBuf.writeInt(requestType.length());
        byteBuf.writeCharSequence(requestType, charset);
    }
}
