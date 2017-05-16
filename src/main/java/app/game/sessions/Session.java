package app.game.sessions;

import app.communication.IncomingMessages;
import app.game.rooms.RoomUser;
import app.game.users.User;
import app.system.network.CommunicationHandler;
import app.system.network.codec.NetConnection;
import app.system.protocol.ClientMessage;
import app.system.protocol.ServerMessage;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class Session implements CommunicationHandler
{
    public long id;
    public NetConnection connection;

    public User user;

    public RoomUser roomUser;

    public Session(long id, NetConnection netConnection)
    {
        this.id = id;
        this.connection = netConnection;
    }

    public void send(ServerMessage message)
    {
        if(message != null)
            this.send(message, false);
    }

    public void send(ServerMessage message, boolean copy)
    {
        if(message != null)
            connection.send(message, copy);
    }

    public void setRoomUser(RoomUser user)
    {
        this.roomUser = user;
    }

    @Override
    public void onNetNewMessage(ClientMessage msg) {
        IncomingMessages.handlePacket(this, msg);
    }

    @Override
    public void onNetClose(String reason) {
        
    }

    public long getId()
    {
        return this.id;
    }

    public RoomUser getRoomUser(){
        return this.roomUser;
    }

    public User getUser()
    {
        return this.user;
    }
}
