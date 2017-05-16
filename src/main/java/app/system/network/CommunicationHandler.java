package app.system.network;

import app.system.protocol.ClientMessage;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public interface CommunicationHandler
{
    void onNetNewMessage(ClientMessage msg);
    void onNetClose(String reason);
}
