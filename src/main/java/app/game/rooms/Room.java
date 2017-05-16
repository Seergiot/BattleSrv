package app.game.rooms;

import app.communication.outgoing.rooms.player.MoveUserComposer;
import app.communication.outgoing.rooms.player.SerializePlayersComposer;
import app.game.sessions.Session;
import app.system.protocol.ServerMessage;
import app.system.threadpool.GameTask;
import app.system.threadpool.WorkerTasks;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class Room extends GameTask
{
    public static void addTask(final GameTask task, int initDelay, int repeatDelay) {
        WorkerTasks.addTask(task, initDelay, repeatDelay, WorkerTasks.roomsTasks);
    }

    private int[][] playerMatrix;
    private int[][] gameMatrix;
    private int maxX, maxY;
    private Map<Integer, RoomUser> players;

    public MapModel mapModel;

    public Room()
    {
        mapModel = new MapModel();
        this.maxX = mapModel.width;
        this.maxY = mapModel.height;
        this.playerMatrix = new int[maxX][maxY];
        this.gameMatrix = new int[maxX][maxY];
        this.players = new HashMap<>();
    }

    public static Point GetNextStep(int X, int Y, int goalX, int goalY) {
        Point Next = new Point(-1, -1);

        if (X > goalX && Y > goalY) {
            Next = new Point(X - 1, Y - 1);
        } else if (X < goalX && Y < goalY) {
            Next = new Point(X + 1, Y + 1);
        } else if (X > goalX && Y < goalY) {
            Next = new Point(X - 1, Y + 1);
        } else if (X < goalX && Y > goalY) {
            Next = new Point(X + 1, Y - 1);
        } else if (X > goalX) {
            Next = new Point(X - 1, Y);
        } else if (X < goalX) {
            Next = new Point(X + 1, Y);
        } else if (Y < goalY) {
            Next = new Point(X, Y + 1);
        } else if (Y > goalY) {
            Next = new Point(X, Y - 1);
        }

        return Next;
    }

    public void addPlayerToRoom(Session session) {
        Random rnd = new Random();
        int x = rnd.nextInt(maxX);
        int y = rnd.nextInt(maxY);
        int rot = 2;

        RoomUser user = new RoomUser(session.getUser().getId(), x, y, rot, session.getUser(), this);
        session.setRoomUser(user);
        this.players.put(user.getUserId(), user);
        this.playerMatrix[user.getX()][user.getY()] = user.getUserId();
        serializeRoomUsers();
    }

    public void removeUserFromRoom(Session session) {
        if (session == null)
            return;
        RoomUser user = getRoomUserByUserId(session.getUser().getId());
        if (user == null)
            return;
        playerMatrix[user.getX()][user.getY()] = 0;
        user.setUser(null);
        players.remove(user.getUserId());
        serializeRoomUsers();
    }

    public RoomUser getRoomUserByUserId(int id) {
        if (players.containsKey(id)) {
            return players.get(id);
        }
        return null;
    }

    @Override
    public void run() {
        for (RoomUser player : players.values()) {
            if (player.getX() != player.getTargetX() || player.getY() != player.getTargetY())
            {
                Point nextStep = GetNextStep(player.getX(), player.getY(), player.getTargetX(), player.getTargetY());
                player.setRot(calculateRotation(player.getX(), player.getY(), nextStep.x, nextStep.y));
                player.setX(nextStep.x);
                player.setY(nextStep.y);

                sendMessage(MoveUserComposer.compose(player));
            }
			/*if (player.isPathRecalcNeeded()) {

				// TODO: Calculate path

				player.setMoving(true);
				player.setPathRecalcNeeded(false);
			}

			if (player.isMoving()) {
				if (player.getPath().size() > 1) {
					playerMatrix[player.getX()][player.getY()] = 0;
					// TODO: Check if player is candidate tile is valid

					onPlayerWalksOffTile(player, player.getX(), player.getY());
					player.setRot(calculateRotation(player.getX(), player.getY(), player.getPath().get(0).x,
							player.getPath().get(0).y));

					player.setX(player.getPath().get(0).x);
					player.setY(player.getPath().get(0).y);

					playerMatrix[player.getX()][player.getY()] = player.getUserId();
					player.getPath().remove(0);

					if (player.getTargetX() == player.getX() && player.getTargetY() == player.getTargetY()) {
						player.setMoving(false);
					}

					onPlayerWalksOnTile(player, player.getX(), player.getY());

					ServerMessage movementMessage = new ServerMessage(ServerOpcodes.PLAYER_MOVEMENT);
					movementMessage.appendInt(player.getUserId());
					movementMessage.appendInt(player.getX());
					movementMessage.appendInt(player.getY());
					movementMessage.appendInt(player.getRot());
					sendMessage(movementMessage);

				} else {
					player.setMoving(false);
				}
			}*/
        }

    }

    private int calculateRotation(int x1, int y1, int x2, int y2) {
        int rotation = 0;

        if (x1 > x2 && y1 > y2) {
            rotation = 7;
        } else if (x1 < x2 && y1 < y2) {
            rotation = 3;
        } else if (x1 > x2 && y1 < y2) {
            rotation = 5;
        } else if (x1 < x2 && y1 > y2) {
            rotation = 1;
        } else if (x1 > x2) {
            rotation = 6;
        } else if (x1 < x2) {
            rotation = 2;
        } else if (y1 < y2) {
            rotation = 4;
        } else if (y1 > y2) {
            rotation = 0;
        }

        return rotation;
    }

    public boolean isValidTile(int x, int y) {
        return x >= 0 && y >= 0 && x < maxX && y < maxY;
    }

    private void onPlayerWalksOnTile(RoomUser player, int x, int y) {

    }

    private void onPlayerWalksOffTile(RoomUser player, int x, int y) {

    }

    private void serializeRoomUsers() {

        sendMessage(SerializePlayersComposer.compose(players.values()));
    }

    public void sendMessage(ServerMessage message) {
        for (RoomUser player : this.players.values()) {
            if (player != null) {
                player.getUser().getSession().send(message);
            }
        }
    }
}
