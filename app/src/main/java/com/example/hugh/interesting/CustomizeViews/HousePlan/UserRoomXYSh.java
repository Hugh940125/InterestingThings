package com.example.hugh.interesting.CustomizeViews.HousePlan;

import java.io.Serializable;

/**
 * Author: lzw
 * Date: 2018/12/5
 * Description: This is UserRoomXYSh
 */

public class UserRoomXYSh implements Serializable {
    private String roomId;
    private String roomName;
    private String roomX;
    private String roomY;
    private String roomFloor;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomX() {
        return roomX;
    }

    public void setRoomX(String roomX) {
        this.roomX = roomX;
    }

    public String getRoomY() {
        return roomY;
    }

    public void setRoomY(String roomY) {
        this.roomY = roomY;
    }

    public String getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(String roomFloor) {
        this.roomFloor = roomFloor;
    }

    @Override
    public String toString() {
        return "UserRoomXYSh{" +
                "roomId='" + roomId + '\'' +
                ", roomName='" + roomName + '\'' +
                ", roomX='" + roomX + '\'' +
                ", roomY='" + roomY + '\'' +
                ", roomFloor='" + roomFloor + '\'' +
                '}';
    }
}
