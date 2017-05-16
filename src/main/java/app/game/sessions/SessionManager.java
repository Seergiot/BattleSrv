package app.game.sessions;

import app.system.network.codec.NetConnection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class SessionManager
{
    private static SessionManager instance = new SessionManager();

    public static SessionManager getInstance()
    {
        return instance;
    }

    private Map<Long, Session> sessions;
    private long sessionCounter;

    public SessionManager()
    {
        this.sessions = new ConcurrentHashMap<>();
    }

    public Session startSession(NetConnection connection)
    {
        Session session = new Session(++this.sessionCounter, connection);
        connection.setHandler(session);
        sessions.put(session.getId(), session);

        return session;
    }
}
