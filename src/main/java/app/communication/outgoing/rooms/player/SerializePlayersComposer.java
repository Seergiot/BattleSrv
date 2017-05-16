package app.communication.outgoing.rooms.player;

import app.game.rooms.RoomUser;
import app.system.protocol.ServerMessage;
import app.system.protocol.opcodes.ServerOpCodes;

import java.util.Collection;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class SerializePlayersComposer
{
    public static ServerMessage compose(Collection<RoomUser> users)
    {
        ServerMessage usersData = new ServerMessage(ServerOpCodes.PLAYERS_DATA);
        usersData.appendInt(users.size());

        for (RoomUser player : users) {
            usersData.appendInt(player.getUserId());
            usersData.appendInt(player.getX());
            usersData.appendInt(player.getY());
            usersData.appendInt(player.getRot());
            usersData.appendString(player.getUser().getLook());
        }
        return usersData;
    }
}
