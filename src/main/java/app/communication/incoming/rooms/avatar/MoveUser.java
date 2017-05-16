package app.communication.incoming.rooms.avatar;

import app.communication.IncomingEvent;
import app.game.rooms.RoomUser;
import app.game.sessions.Session;
import app.system.protocol.ClientMessage;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class MoveUser implements IncomingEvent
{
    @Override
    public void handle(Session session, ClientMessage message) throws Exception
    {
        int x = message.popInt();
        int y = message.popInt();

        RoomUser user = session.getRoomUser();
        if (user == null) {
            session.onNetClose("user null");
        } else {
            user.moveTo(x, y);
        }
    }
}
