package app.system.network.codec;

import app.game.sessions.Session;
import app.game.sessions.SessionManager;
import app.game.users.User;
import app.system.protocol.ClientMessage;
import app.system.protocol.ServerMessage;
import app.system.protocol.opcodes.ClientOpCodes;
import app.system.protocol.opcodes.ServerOpCodes;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrameEncoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;

import java.util.List;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class HandshakeHandler extends WebSocketExtensionDecoder
{
    private SessionManager sessionMgr;

    public HandshakeHandler(SessionManager sessionMgr)
    {
        this.sessionMgr = sessionMgr;
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e)
    {
        ctx.channel().close();
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception
    {
        String _str = ((TextWebSocketFrame)webSocketFrame).text();

        ClientMessage message = new ClientMessage(_str);

        if(message.getId() == ClientOpCodes.LOGIN) // login user xd
        {
            NetConnection conn = new NetConnection(channelHandlerContext.channel());

            Session session = this.sessionMgr.startSession(conn);
            session.user = new User((int)session.getId(), message.popString(), message.popString(), session);

            channelHandlerContext.channel().pipeline().addLast("decoder", new ClientMessageDecoder());
            channelHandlerContext.channel().pipeline().addLast("handler", conn);
            channelHandlerContext.channel().pipeline().remove(this);

            conn.send(new ServerMessage(ServerOpCodes.LOGIN_OK), true);
        }

    }
}
