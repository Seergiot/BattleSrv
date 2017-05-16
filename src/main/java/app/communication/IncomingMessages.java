package app.communication;

import app.communication.incoming.rooms.RequestMap;
import app.communication.incoming.rooms.avatar.Chat;
import app.communication.incoming.rooms.avatar.MoveUser;
import app.game.sessions.Session;
import app.system.protocol.ClientMessage;
import app.system.protocol.opcodes.ClientOpCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class IncomingMessages
{
    public final static Logger logger = LoggerFactory.getLogger(IncomingMessages.class);
    public static Map<Integer, IncomingEvent> messages;

    public static void init()
    {
        messages = new HashMap<>();
        messages.put(ClientOpCodes.REQUEST_MAP, new RequestMap());
        messages.put(ClientOpCodes.REQUEST_MOVEMENT, new MoveUser());
        messages.put(ClientOpCodes.REQUEST_CHAT, new Chat());
    }

    public static void handlePacket(Session session, ClientMessage message)
    {
        IncomingEvent event = messages.get(message.getId());
        if(event != null)
        {
            try
            {
                event.handle(session, message);
            }
            catch (Exception e)
            {
               logger.error("Error in handlePacket ", e);
            }

            logger.info("[CONOCIDO] -> "+ message.toString());
        }
        else
        {
            logger.info("[DESCONOCIDO] -> "+ message.toString());
        }
    }
}
