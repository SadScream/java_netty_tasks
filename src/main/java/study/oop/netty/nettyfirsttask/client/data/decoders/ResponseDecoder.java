package study.oop.netty.nettyfirsttask.client.data.decoders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import study.oop.netty.nettyfirsttask.shared.models.ResponseType;
import study.oop.netty.nettyfirsttask.shared.models.Unit;
import study.oop.netty.nettyfirsttask.shared.responses.Response;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ResponseDecoder extends ReplayingDecoder<Response> {
    private final Charset charset = StandardCharsets.UTF_8;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf,
                          List<Object> list) throws Exception {
        Response data = new Response();
        data.setResponseType(readResponseType(byteBuf));
        resolveResponseType(data, byteBuf);
        list.add(data);
    }

    private String readResponseType(ByteBuf byteBuf) {
        int strLen = byteBuf.readInt();
        return byteBuf.readCharSequence(strLen, charset).toString();
    }

    private void resolveResponseType(Response response, ByteBuf byteBuf) {
//        System.out.println("[Resolve response] " + response.getResponseType());
        switch (response.getResponseType()) {
            case ResponseType.InitialUnitArrayData -> {
                int arrayLength = byteBuf.readInt();
                List<Unit> units = new ArrayList<>();

                for (int i = 0; i < arrayLength; i++) {
                    Unit unit = new Unit();

                    unit.setId(byteBuf.readInt());
                    int x = byteBuf.readInt();
                    int y = byteBuf.readInt();
                    unit.setPosition(x, y);

                    units.add(unit);
                }

                response.setUnits(units);
                System.out.println("[Resolved response] " + response.getResponseType());
                System.out.println("... units length: " + response.getUnits().size()
                    + " first unit id: " + response.getUnits().get(0).getId());
            }
            case ResponseType.UnitData -> {
                response.setId(byteBuf.readInt());
                int x = byteBuf.readInt();
                int y = byteBuf.readInt();
                response.setPosition(x, y);
            }
        }
    }
}
