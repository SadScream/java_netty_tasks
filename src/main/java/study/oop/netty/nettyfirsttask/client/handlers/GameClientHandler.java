package study.oop.netty.nettyfirsttask.client.handlers;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import study.oop.netty.nettyfirsttask.client.GameService;
import study.oop.netty.nettyfirsttask.client.GameStore;
import study.oop.netty.nettyfirsttask.shared.models.RequestType;
import study.oop.netty.nettyfirsttask.shared.models.ResponseType;
import study.oop.netty.nettyfirsttask.shared.requests.Request;
import study.oop.netty.nettyfirsttask.shared.responses.Response;

public class GameClientHandler extends ChannelInboundHandlerAdapter {
    GameService gameService;

    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        gameService = GameService.getInstance();
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
        GameStore store = GameStore.getInstance();

        switch (response.getResponseType()) {
            case (ResponseType.InitialUnitArrayData) -> {
                store.setUnits(response.getUnits());
            }
            case ResponseType.UnitData -> {
                store.patchUnitPosition(response);
            }
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
