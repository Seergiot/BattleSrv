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
public class Chat implements IncomingEvent {
    @Override
    public void handle(Session session, ClientMessage message) throws Exception
    {
        String text = message.popString();
        RoomUser user = session.getRoomUser();
        if (user == null) {
            session.onNetClose("user null");
        } else {
            user.chat(text);
        }
    }
}
