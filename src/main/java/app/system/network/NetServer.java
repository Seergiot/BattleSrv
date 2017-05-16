package app.system.network;

import app.game.sessions.SessionManager;
import app.system.network.codec.HandshakeHandler;
import app.system.network.codec.WebSocketServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class NetServer {
    private static NetServer instance = new
            NetServer();

    public static NetServer getInstance()
    {
        return instance;
    }

    private ChannelGroup channels;
    private final List<String> blacklist;


    private ServerBootstrap server;
    private Channel listener;
    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;

    private final ConnectionHandler connectionHandler;

    public NetServer()
    {
        this.server = null;
        this.listener = null;
        this.channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        this.blacklist = new ArrayList<String>();

        connectionHandler = new ConnectionHandler(this, this.channels);
    }

    public boolean start(int port)
    {
        // Already running?
        if(this.server != null)
        {
            return false;
        }
        //ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);
        //ConnectionHandler management = new ConnectionHandler(this, this.channels);
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup(8);
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 200)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast( connectionHandler, new WebSocketServerInitializer(), new HandshakeHandler(SessionManager.getInstance()) )
                                .addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8));
                    }
                })
                .childOption(ChannelOption.TCP_NODELAY, true);

        try {
            this.listener = b.bind(port).sync().channel();
            if(this.listener.isOpen()) {
                this.server = b;
                // Bind and start to accept incoming connections.
                return true;
            }
        }
        catch (Exception e)
        {
            //Logger.getInstance().error(this.getClass().getSimpleName(), "Boot failed", e);
        }
        return false;
    }

    public void stop()
    {
        if(this.server != null)
        {
            // Close listener
            this.listener.close();
            this.listener = null;

            // Done!
            //Logger.getInstance().info(this.getClass().toString(), "Closed listener.");
        }
    }

    public void close()
    {
        // Close all connections
        this.channels.close();
        this.channels.clear();

        // Shutdown the executors to speed up shutdown
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();

        // Destroy bootstrapper
        this.server = null;
    }

    public boolean ipIsBlacklisted(String ip)
    {
        return this.blacklist.contains(ip);
    }

    public void reloadBlacklist()
    {
        synchronized(this.blacklist)
        {
            this.blacklist.clear();

        }
    }

    public void blacklistIp(String ip, String info)
    {
        // Already blacklisted?
        if(this.ipIsBlacklisted(ip))
        {
            return;
        }

        // Add to internal blacklist
        synchronized(this.blacklist)
        {
            this.blacklist.add(ip);
        }


    }

    public boolean allowChannel(Channel channel)
    {
        // Get address of joining connection
        InetAddress address = ((InetSocketAddress)channel.remoteAddress()).getAddress();

        // IP is blacklisted?
        if(this.ipIsBlacklisted(address.getHostAddress()))
        {
            return false;
        }



        // Allow!
        return true;
    }
}

