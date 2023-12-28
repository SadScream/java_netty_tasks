package study.oop.netty.nettysecondtask.client.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import study.oop.netty.nettysecondtask.client.game.Service;
import study.oop.netty.nettysecondtask.shared.data.messages.*;
import study.oop.netty.nettysecondtask.shared.models.DataType;

public class GameClientHandler extends ChannelInboundHandlerAdapter {
    Service gameService;
    ChannelHandlerContext context;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        context = ctx;
        gameService = Service.getInstance();
        gameService.PlayerMove.subscribe(player -> {
            PlayerMoveMessage message = new PlayerMoveMessage(player.getId(), player.getNextPathPoint());
            ctx.writeAndFlush(message);
        });
        System.out.println("[Connection Established]");
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        BaseMessage response = (BaseMessage) msg;

        switch (response.messageType) {
            case DataType.ConnectionSucceed -> {
                ConnectionSucceedMessage connectionData = (ConnectionSucceedMessage) response;
                gameService.handlePlayerInitialization(
                        connectionData.id,
                        connectionData.x,
                        connectionData.y,
                        connectionData.unitsDataArray);
            }
            case (DataType.PlayerConnected) -> {
                PlayerConnectedMessage connectionData = (PlayerConnectedMessage) response;
                gameService.handleNewPlayer(connectionData.id, connectionData.x, connectionData.y);
            }
            case DataType.PlayerMove -> {
                PlayerMoveMessage moveData = (PlayerMoveMessage) response;
                gameService.handleUpdatePlayerPosition(moveData.id, moveData.x, moveData.y);
            }
            case DataType.PlayerDisconnected -> {
                PlayerDisconnectedMessage disconnectedMessage = (PlayerDisconnectedMessage) response;
                gameService.handlePlayerDisconnection(disconnectedMessage.id);
            }
        }
    }

    public void disconnect() {
        PlayerDisconnectedMessage message = new PlayerDisconnectedMessage(gameService.getPlayerId());
        context.writeAndFlush(message);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
