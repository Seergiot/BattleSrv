package app.system.protocol;

import java.util.StringTokenizer;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class ClientMessage
{
    private final String SEPARATOR = "|";
    private String body;
    private StringTokenizer tokenizer;
    private int pointer;
    private int id;

    public ClientMessage(String message) throws NumberFormatException {
        this.pointer = 0;
        this.id = -1;
        this.body = message;
        tokenizer = new StringTokenizer(body, SEPARATOR);

        try {
            this.id = Integer.parseInt(popToken());
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    public int getId() {
        return this.id;
    }

    private String popToken() {
        if (tokenizer.hasMoreTokens()) {
            return tokenizer.nextToken();
        }
        return null;
    }

    public int popInt() {
        try {
            return Integer.parseInt(popToken());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String popString() {
        int tickets = popInt();
        String totalString = popToken();

        for (int i = 0; i < tickets; i++) {
            totalString += SEPARATOR + popToken();
        }

        return totalString;
    }

    @Override
    public String toString()
    {
        return this.body;
    }
}
