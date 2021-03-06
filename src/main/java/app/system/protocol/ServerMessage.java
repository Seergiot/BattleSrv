package app.system.protocol;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class ServerMessage
{
    private final char SEPARATOR = '|';
    private String body;

    public ServerMessage(int id)
    {
        this.body = String.valueOf(id);
    }

    private void appendToken(String token)
    {
        body += SEPARATOR + token;
    }

    public void appendInt(int i)
    {
        appendToken(String.valueOf(i));
    }

    public void appendString(String str)
    {
        int tickets = 0;
        for (int i = 0; i < str.length(); i++)
        {
            if (str.charAt(i) == SEPARATOR)
                tickets++;
        }
        appendInt(tickets);
        appendToken(str);
    }

    @Override
    public String toString()
    {
        return this.body;
    }
}
