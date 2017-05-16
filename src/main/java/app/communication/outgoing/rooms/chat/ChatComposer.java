package app.communication.outgoing.rooms.chat;

import app.game.rooms.RoomUser;
import app.system.protocol.ServerMessage;
import app.system.protocol.opcodes.ServerOpCodes;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class ChatComposer
{
    public static ServerMessage compose(RoomUser roomUser, String message)
    {
        ServerMessage chatMessage = new ServerMessage(ServerOpCodes.CHAT);
        chatMessage.appendInt(roomUser.getUserId());
        chatMessage.appendString(roomUser.getUser().getUsername());
        chatMessage.appendString(message);
        return chatMessage;
    }
}
