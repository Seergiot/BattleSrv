package app.communication.outgoing.rooms.player;

import app.game.rooms.RoomUser;
import app.system.protocol.ServerMessage;
import app.system.protocol.opcodes.ServerOpCodes;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class MoveUserComposer
{
    public static ServerMessage compose(RoomUser player)
    {
        ServerMessage movementMessage = new ServerMessage(ServerOpCodes.PLAYER_MOVEMENT);
        movementMessage.appendInt(player.getUserId());
        movementMessage.appendInt(player.getX());
        movementMessage.appendInt(player.getY());
        movementMessage.appendInt(player.getRot());
        return movementMessage;
    }
}
