package app.communication.incoming.rooms;

import app.Bootstrap;
import app.communication.IncomingEvent;
import app.communication.outgoing.rooms.GetMapDataComposer;
import app.game.sessions.Session;
import app.system.protocol.ClientMessage;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class RequestMap implements IncomingEvent
{
    @Override
    public void handle(Session session, ClientMessage message) throws Exception
    {
        Bootstrap.getMainRoom().addPlayerToRoom(session);
        session.send(GetMapDataComposer.compose(session.getRoomUser().getRoom()));
    }
}
