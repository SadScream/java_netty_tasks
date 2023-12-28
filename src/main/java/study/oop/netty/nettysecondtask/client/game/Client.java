package study.oop.netty.nettysecondtask.client.game;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import study.oop.netty.nettysecondtask.client.handlers.GameClientHandler;
import study.oop.netty.nettysecondtask.shared.data.decoders.MessageDecoder;
import study.oop.netty.nettysecondtask.shared.data.encoders.MessageEncoder;

public class Client {
    private EventLoopGroup serverGroup = null;
    private GameClientHandler gameClientHandler;

    public void connect(int port, String host) throws Exception {
        gameClientHandler = new GameClientHandler();

        this.serverGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(serverGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(
                                    new MessageDecoder(),
                                    new MessageEncoder(),
                                    gameClientHandler
                            );
                        }
                    });

            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
        }
    }

    public void disconnect() {
        if (this.serverGroup != null) {
            System.out.println("[Disconnecting]");
            gameClientHandler.disconnect();
            this.serverGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 46000;

        Client client = new Client();
        client.connect(port, host);
    }
}
