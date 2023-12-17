package study.oop.netty.nettyfirsttask.client.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import study.oop.netty.nettyfirsttask.client.game.Service;
import study.oop.netty.nettyfirsttask.client.game.Store;
import study.oop.netty.nettyfirsttask.client.game.models.GameUnit;
import study.oop.netty.nettyfirsttask.shared.models.RequestType;
import study.oop.netty.nettyfirsttask.shared.models.ResponseType;
import study.oop.netty.nettyfirsttask.shared.models.Unit;
import study.oop.netty.nettyfirsttask.shared.requests.Request;
import study.oop.netty.nettyfirsttask.shared.responses.Response;

import java.util.ArrayList;
import java.util.List;

public class GameClientHandler extends ChannelInboundHandlerAdapter {
    Service gameService;

    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        gameService = Service.getInstance();
        gameService.UnitReachedPosition.subscribe(
                (unitId) -> requestNewPosition(ctx, unitId)
        );

        Request msg = new Request();
        msg.setRequestType(RequestType.InitUnits);
        ctx.writeAndFlush(msg);
    }

    public void requestNewPosition(ChannelHandlerContext ctx, int unitId) throws InterruptedException {
        Request msg = new Request();
        msg.setRequestType(RequestType.UnitReachedPoint);
        msg.setId(unitId);
        ctx.channel().writeAndFlush(msg).sync();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        Response response = (Response) msg;
        Service service = Service.getInstance();

        switch (response.getResponseType()) {
            case (ResponseType.InitialUnitArrayData) -> {
                List<Unit> serverUnits = response.getUnits();
                service.handleUnitsInitialization(serverUnits);
            }
            case ResponseType.UnitData -> {
                service.updateUnitPosition(response);
            }
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
