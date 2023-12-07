package study.oop.netty.nettyfirsttask.server.handlers;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import javafx.util.Pair;
import study.oop.netty.nettyfirsttask.server.Server;
import study.oop.netty.nettyfirsttask.shared.models.RequestType;
import study.oop.netty.nettyfirsttask.shared.models.ResponseType;
import study.oop.netty.nettyfirsttask.shared.models.Unit;
import study.oop.netty.nettyfirsttask.shared.requests.Request;
import study.oop.netty.nettyfirsttask.shared.responses.Response;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class UnitHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        Request requestData = (Request) msg;
        Response responseData = resolveRequest(requestData);

        ChannelFuture future = ctx.writeAndFlush(responseData);
    }

    private Response resolveRequest(Request request) {
        Response responseData = new Response();

        switch (request.getRequestType()) {
            case RequestType.InitUnits -> {
                List<Unit> units = Server.db.index();

                for (Unit unit: units) {
                    unit.setCoords(getNewRandomCoordsForUnit());
                }

                responseData.setResponseType(ResponseType.InitialUnitArrayData);
                responseData.setUnits(units);
                break;
            }
            case RequestType.UnitReachedPoint -> {
                int id = request.getId();

                Unit unit = Server.db.show(id);
                responseData.setResponseType(ResponseType.UnitData);
                responseData.setId(unit.getId());
                responseData.setCoords(getNewRandomCoordsForUnit());
            }
        }

        return responseData;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }

    private Pair<Integer, Integer> getNewRandomCoordsForUnit() {
        int x = ThreadLocalRandom.current().nextInt(0, 400);
        int y = ThreadLocalRandom.current().nextInt(0, 350);

        return new Pair<Integer, Integer>(x, y);
    }
}
