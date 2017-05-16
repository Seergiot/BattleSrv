package app.communication;

import app.game.sessions.Session;
import app.system.protocol.ClientMessage;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public interface IncomingEvent
{
    void handle(Session session, ClientMessage message) throws Exception;
}
