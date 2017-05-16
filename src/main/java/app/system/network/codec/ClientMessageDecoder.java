package app.system.network.codec;

import app.system.protocol.ClientMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;

import java.util.List;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class ClientMessageDecoder extends WebSocketExtensionDecoder
{

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception
    {
        if(webSocketFrame instanceof TextWebSocketFrame)
        {
            String text = ((TextWebSocketFrame)webSocketFrame).text();
            list.add(new ClientMessage(text));
        }
    }
}
