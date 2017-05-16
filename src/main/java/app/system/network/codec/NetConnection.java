package app.system.network.codec;

import app.system.network.CommunicationHandler;
import app.system.protocol.ClientMessage;
import app.system.protocol.ServerMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.net.InetSocketAddress;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class NetConnection  extends SimpleChannelInboundHandler<ClientMessage>
{
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ClientMessage o) throws Exception
    {
        if(o != null && handler != null)
        {
            handler.onNetNewMessage(o);
        }
    }

    private Channel channel;
    private String ipAddress;
    private CommunicationHandler handler;

    public NetConnection(Channel channel)
    {
        this.channel = channel;
        if(channel != null)
        {
            this.ipAddress = ((InetSocketAddress)channel.remoteAddress()).getAddress().getHostAddress();
        }
    }

    public Channel getChannel()
    {
        return this.channel;
    }

    public boolean isOpen()
    {
        return this.channel.isOpen();
    }

    public void close()
    {
        if(this.channel.isOpen())
        {
            //this.channel.close();
            this.channel.close();// TODO no close connection :/.write(ChannelBuffers.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    private void requestClose(String reason)
    {
        this.close();
        if(this.handler != null)
        {
            this.handler.onNetClose(reason);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)
    {
        this.requestClose("disconnected by user");
    }

    public void send(ServerMessage msg, boolean copy)
    {
        if(this.channel.isWritable())
        {
            this.channel.writeAndFlush(new TextWebSocketFrame(msg.toString()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        this.requestClose("error on parse");
        //Logger.getInstance().error(getClass().getSimpleName(), "ExceptionCaught NetConnection", (Exception)cause);
    }

    public CommunicationHandler getHandler()
    {
        return this.handler;
    }

    public void setHandler(CommunicationHandler handler)
    {
        this.handler = handler;
    }

    public String getIpAddress()
    {
        return this.ipAddress;
    }
}
