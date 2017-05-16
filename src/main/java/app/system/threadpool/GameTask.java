package app.system.threadpool;

import java.util.concurrent.ScheduledFuture;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class GameTask extends Thread
{
    public ScheduledFuture<?> future;
}
