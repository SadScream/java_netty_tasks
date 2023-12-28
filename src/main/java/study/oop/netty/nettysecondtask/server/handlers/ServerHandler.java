package study.oop.netty.nettysecondtask.server.handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import study.oop.netty.nettysecondtask.server.Server;
import study.oop.netty.nettysecondtask.shared.data.messages.*;
import study.oop.netty.nettysecondtask.shared.models.DataType;
import study.oop.netty.nettysecondtask.shared.models.Unit;

import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        channels.add(ctx.channel());

        Unit connectedUnit = Server.db.addUnit();
        PlayerConnectedMessage connectionMessage = new PlayerConnectedMessage(
                connectedUnit.getId(),
                connectedUnit.getPosition()
        );

        for (Channel channel : channels) {
            if (channel != ctx.channel()) {
                channel.writeAndFlush(connectionMessage);
            } else {
                ArrayList<Unit> copy = new java.util.ArrayList<>(Server.db.index().stream().toList());
                copy.remove(connectedUnit);
                BaseMessage connectionSucceedMessage = new ConnectionSucceedMessage(
                        connectedUnit.getId(),
                        connectedUnit.getPosition(),
                        copy
                );
                channel.writeAndFlush(connectionSucceedMessage);
            }
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        channels.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        BaseMessage requestData = (BaseMessage) msg;

        switch (requestData.messageType) {
            case DataType.PlayerMove -> {
                for (Channel channel : channels) {
                    channel.writeAndFlush((PlayerMoveMessage)requestData);
                }
            }
            case DataType.PlayerDisconnected -> {
                Server.db.removeUnit(((PlayerDisconnectedMessage)requestData).id);

                for (Channel channel : channels) {
                    if (channel != ctx.channel()) {
                        channel.writeAndFlush(requestData);
                    }
                }
            }
        }
    }
}
