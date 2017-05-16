package app.system.threadpool;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class WorkerTasks
{
    public static int serverType;

    public static int SERVER_TINY = 0;
    public static int SERVER_SMALL = 1;
    public static int SERVER_NORMAL = 2;
    public static int SERVER_LARGE = 3;

    public static ScheduledThreadPoolExecutor roomsTasks;

    public static void initWorkers(final int type) {
        serverType = type;

        if(serverType == SERVER_TINY) {
            roomsTasks = new ScheduledThreadPoolExecutor(1);
        } else if(serverType == SERVER_SMALL) {
            // 500 users
            // 6 threads
            roomsTasks = new ScheduledThreadPoolExecutor(2);
        } else if(serverType == SERVER_NORMAL) {
            roomsTasks = new ScheduledThreadPoolExecutor(4);
        } else if(serverType == SERVER_LARGE) {
            roomsTasks = new ScheduledThreadPoolExecutor(8);
        } else {
            roomsTasks = new ScheduledThreadPoolExecutor(10);
        }
    }

    public static void close()
    {
        roomsTasks.shutdown();
    }

    public static void addTask(final GameTask task, int initDelay, int repeatRate, ScheduledThreadPoolExecutor worker) {
        if(repeatRate > 0) {
            task.future = worker.scheduleAtFixedRate(task, initDelay, repeatRate, TimeUnit.MILLISECONDS);
        } else {
            task.future = worker.schedule(task, initDelay, TimeUnit.MILLISECONDS);
        }
    }
}
