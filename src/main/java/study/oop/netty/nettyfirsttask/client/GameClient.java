package study.oop.netty.nettyfirsttask.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import study.oop.netty.nettyfirsttask.client.data.decoders.ResponseDecoder;
import study.oop.netty.nettyfirsttask.client.data.encoders.RequestEncoder;
import study.oop.netty.nettyfirsttask.client.handlers.GameClientHandler;

public class GameClient {
    private EventLoopGroup serverGroup = null;

    public void connect(int port, String host) throws Exception {
        this.serverGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(serverGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(
                                    new RequestEncoder(),
                                    new ResponseDecoder(),
                                    new GameClientHandler()
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
            this.serverGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 46000;

        GameClient client = new GameClient();
        client.connect(port, host);
    }
}
