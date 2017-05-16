package app.game.users;

import app.game.sessions.Session;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class User
{
    private int id;
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the look
     */
    public String getLook() {
        return look;
    }

    /**
     * @param look the look to set
     */
    public void setLook(String look) {
        this.look = look;
    }

    /**
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * @return the disconnected
     */
    public boolean isDisconnected() {
        return disconnected;
    }

    /**
     * @param disconnected the disconnected to set
     */
    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }

    private String username;
    private String look;
    private Session session;
    private boolean disconnected;

    /**
     * @param id
     * @param username
     * @param look
     * @param session
     */
    public User(int id, String username, String look, Session session) {
        this.id = id;
        this.username = username;
        this.look = look;
        this.session = session;
        this.disconnected = false;
    }

    public void onDisconnect() {
        if (!disconnected)
        {
            //Logging.writeLine(username + " has logged out", LogLevel.INFO);
        }
    }
}
