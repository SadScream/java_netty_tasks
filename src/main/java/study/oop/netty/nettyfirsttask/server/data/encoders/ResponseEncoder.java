package study.oop.netty.nettyfirsttask.server.data.encoders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import javafx.util.Pair;
import study.oop.netty.nettyfirsttask.shared.models.RequestType;
import study.oop.netty.nettyfirsttask.shared.models.ResponseType;
import study.oop.netty.nettyfirsttask.shared.models.Unit;
import study.oop.netty.nettyfirsttask.shared.requests.Request;
import study.oop.netty.nettyfirsttask.shared.responses.Response;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ResponseEncoder extends MessageToByteEncoder<Response> {
    private final Charset charset = StandardCharsets.UTF_8;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Response unitEntityResponse,
                          ByteBuf byteBuf) throws Exception {
        resolveResponseType(unitEntityResponse, byteBuf);
    }

    private void resolveResponseType(Response unitEntityResponse, ByteBuf byteBuf) {
        System.out.println("[Encoding] " + unitEntityResponse.getResponseType());
        switch (unitEntityResponse.getResponseType()) {
            case ResponseType.InitialUnitArrayData -> {
                byteBuf.writeInt(unitEntityResponse.getResponseType().length());
                byteBuf.writeCharSequence(unitEntityResponse.getResponseType(), charset);
                byteBuf.writeInt(unitEntityResponse.getUnits().size());

                for (Unit unit: unitEntityResponse.getUnits()) {
                    byteBuf.writeInt(unit.getId());
                    byteBuf.writeInt(unit.getCoords().getKey());
                    byteBuf.writeInt(unit.getCoords().getValue());
                }

                break;
            }
            case ResponseType.UnitData -> {
                byteBuf.writeInt(unitEntityResponse.getResponseType().length());
                byteBuf.writeCharSequence(unitEntityResponse.getResponseType(), charset);
                Pair<Integer, Integer> coords = unitEntityResponse.getCoords();
                int x = coords.getKey();
                int y = coords.getValue();
                int id = unitEntityResponse.getId();

                byteBuf.writeInt(id);
                byteBuf.writeInt(x);
                byteBuf.writeInt(y);

                break;
            }
        }
    }
}
