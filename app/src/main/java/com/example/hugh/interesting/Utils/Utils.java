package com.example.hugh.interesting.Utils;

/**
 * Created by Hugh on 2019/3/28.
 *
 */

public class Utils {

    public static int distanceBetweenPoints(int x,int y,float movingX,float MovingY){
        return (int)(Math.sqrt(Math.pow(movingX-x,2) + Math.pow(MovingY-y,2)));
    }
}
