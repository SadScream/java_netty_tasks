package study.oop.netty.nettyfirsttask.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import study.oop.netty.nettyfirsttask.server.dao.UnitDAO;
import study.oop.netty.nettyfirsttask.server.data.decoders.RequestDecoder;
import study.oop.netty.nettyfirsttask.server.data.encoders.ResponseEncoder;
import study.oop.netty.nettyfirsttask.server.handlers.UnitHandler;

public class Server {
    public static UnitDAO db;
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    private void initDB() {
        Server.db = new UnitDAO();
    }

    public void run() throws Exception {
        initDB();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel
                                    .pipeline()
                                    .addLast(
                                            new RequestDecoder(),
                                            new ResponseEncoder(),
                                            new UnitHandler()
                                    );
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            System.out.println("[Server Started]");
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 46000;
        }
        new Server(port).run();
    }
}
