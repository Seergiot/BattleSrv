package app.system.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
@ChannelHandler.Sharable()
public class ConnectionHandler extends ChannelHandlerAdapter
{
    private NetServer server;
    private ChannelGroup channels;
    public ConnectionHandler(NetServer server, ChannelGroup channels)
    {
        this.server = server;
        this.channels = channels;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        if(this.server.allowChannel(channel))
        {
            this.channels.add(channel);
        }
        else
        {
            channel.close();
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channels.remove(ctx.channel());
    }
}