package com.example.hugh.interesting.CustomizeViews.HousePlan;

import java.io.Serializable;

/**
 * Author: lzw
 * Date: 2018/12/4
 * Description: 获取全部房间接口
 */

public class UserRoomInfoSh implements Serializable {
    /**
     * id : 305
     * memberId : 3281191
     * itemId : 275
     * baseRoomId : 86
     * roomName : 次卧室
     * roomDesc : null
     * roomOrder : null
     * roomPicture : null
     * roomFloor : null
     * roomX : 0.0
     * roomY : 0.0
     * extRoomCode : null
     * tenantId : 38
     */

    private String id;
    private String memberId;
    private String itemId;
    private String baseRoomId;
    private String roomName;
    private String roomDesc;
    private String roomOrder;
    private String roomPicture;
    private String roomFloor;
    private String roomX;
    private String roomY;
    private String extRoomCode;
    private String tenantId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getBaseRoomId() {
        return baseRoomId;
    }

    public void setBaseRoomId(String baseRoomId) {
        this.baseRoomId = baseRoomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomDesc() {
        return roomDesc;
    }

    public void setRoomDesc(String roomDesc) {
        this.roomDesc = roomDesc;
    }

    public String getRoomOrder() {
        return roomOrder;
    }

    public void setRoomOrder(String roomOrder) {
        this.roomOrder = roomOrder;
    }

    public String getRoomPicture() {
        return roomPicture;
    }

    public void setRoomPicture(String roomPicture) {
        this.roomPicture = roomPicture;
    }

    public String getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(String roomFloor) {
        this.roomFloor = roomFloor;
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

    public String getExtRoomCode() {
        return extRoomCode;
    }

    public void setExtRoomCode(String extRoomCode) {
        this.extRoomCode = extRoomCode;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String toString() {
        return "UserRoomInfoSh{" +
                "id='" + id + '\'' +
                ", memberId='" + memberId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", baseRoomId='" + baseRoomId + '\'' +
                ", roomName='" + roomName + '\'' +
                ", roomDesc='" + roomDesc + '\'' +
                ", roomOrder='" + roomOrder + '\'' +
                ", roomPicture='" + roomPicture + '\'' +
                ", roomFloor='" + roomFloor + '\'' +
                ", roomX='" + roomX + '\'' +
                ", roomY='" + roomY + '\'' +
                ", extRoomCode='" + extRoomCode + '\'' +
                ", tenantId='" + tenantId + '\'' +
                '}';
    }
}
