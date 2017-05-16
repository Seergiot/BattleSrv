package app;

import app.communication.IncomingMessages;
import app.game.rooms.Room;
import app.system.network.NetServer;
import app.system.threadpool.WorkerTasks;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class Bootstrap
{
    private static Room mainRoom;

    public static void main(String[] args) throws Exception
    {
        WorkerTasks.initWorkers(0);
        NetServer.getInstance().start(8181); // init ws

        mainRoom = new Room();
        Room.addTask(mainRoom, 0, 500);

        IncomingMessages.init();
    }

    public static Room getMainRoom()
    {
        return mainRoom;
    }
}
