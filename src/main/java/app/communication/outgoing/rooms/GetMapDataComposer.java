package app.communication.outgoing.rooms;

import app.game.rooms.MapModel;
import app.game.rooms.Room;
import app.system.protocol.ServerMessage;
import app.system.protocol.opcodes.ServerOpCodes;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class GetMapDataComposer
{
    public static ServerMessage compose(Room room)
    {
        ServerMessage response = new ServerMessage(ServerOpCodes.MAP_DATA);
        MapModel model = room.mapModel;

        response.appendInt(model.width);
        response.appendInt(model.height);
        for (int i = 0; i < model.width; i++) {
            for (int j = 0; j < model.height; j++) {
                response.appendInt(model.getLayer()[i][j]);
            }
        }

        return response;
    }
}
