package app.game.rooms;

import app.communication.outgoing.rooms.chat.ChatComposer;
import app.game.users.User;

import java.awt.*;
import java.awt.List;
import java.util.*;

/**
 * Battleball_Srv
 *
 * @author SergioT
 * @since 16/05/2017
 */
public class RoomUser
{
    private int userId;
    private int x, y, rot;
    private int targetX, targetY;
    private boolean moving;
    private boolean pathRecalcNeeded;

    private Room room;
    private User user;

    private java.util.List<Point> path;

    public RoomUser(int userId, int x, int y, int rot, User user, Room room) {
        this.userId = userId;
        this.x = x;
        this.y = y;
        this.rot = rot;
        this.targetX = x;
        this.targetY = y;
        this.moving = false;
        this.pathRecalcNeeded = false;
        this.user = user;
        this.room = room;
        this.path = new LinkedList<>();
    }

    public void moveTo(int x, int y) {
        if (room.isValidTile(x, y)) {
            this.targetX = x;
            this.targetY = y;
            this.pathRecalcNeeded = true;
        }
    }

    public void chat(String message) {

        room.sendMessage(ChatComposer.compose(this, message));
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x
     *            the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y
     *            the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the rot
     */
    public int getRot() {
        return rot;
    }

    /**
     * @param rot
     *            the rot to set
     */
    public void setRot(int rot) {
        this.rot = rot;
    }

    /**
     * @return the targetX
     */
    public int getTargetX() {
        return targetX;
    }

    /**
     * @param targetX
     *            the targetX to set
     */
    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    /**
     * @return the targetY
     */
    public int getTargetY() {
        return targetY;
    }

    /**
     * @param targetY
     *            the targetY to set
     */
    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    /**
     * @return the moving
     */
    public boolean isMoving() {
        return moving;
    }

    /**
     * @param moving
     *            the moving to set
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    /**
     * @return the pathRecalcNeeded
     */
    public boolean isPathRecalcNeeded() {
        return pathRecalcNeeded;
    }

    /**
     * @param pathRecalcNeeded
     *            the pathRecalcNeeded to set
     */
    public void setPathRecalcNeeded(boolean pathRecalcNeeded) {
        this.pathRecalcNeeded = pathRecalcNeeded;
    }

    /**
     * @return the room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * @param room
     *            the room to set
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    public java.util.List<Point> getPath() {
        return path;
    }

    public void setPath(java.util.List<Point> path) {
        this.path = path;
    }
}
