package com.java.games;


import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<GameObject> snakeParts = new ArrayList<>();

    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";

    private Direction direction = Direction.LEFT;
    public boolean isAlive = true;

    public Snake(int x, int y){
        snakeParts.add(new GameObject(x, y));
        snakeParts.add(new GameObject(x + 1, y));
        snakeParts.add(new GameObject(x + 2, y));
    }

    public void draw(Game game){

        for (int i = 0; i < snakeParts.size(); i++) {

            boolean isHead = i == 0;
            int x = snakeParts.get(i).x;
            int y = snakeParts.get(i).y;

            game.setCellValueEx(x, y, Color.NONE, isHead ? HEAD_SIGN : BODY_SIGN, !isAlive ? Color.RED : Color.GREEN , 75);
        }
    }

    public void setDirection(Direction direction){
        if(this.direction == Direction.LEFT && direction == Direction.RIGHT
                || this.direction == Direction.RIGHT && direction == Direction.LEFT
                || this.direction == Direction.UP && direction == Direction.DOWN
                || this.direction == Direction.DOWN && direction == Direction.UP)
            return;

        if(this.direction == Direction.LEFT && snakeParts.get(0).x == snakeParts.get(1).x
                || this.direction == Direction.RIGHT && snakeParts.get(0).x == snakeParts.get(1).x
                || this.direction == Direction.UP && snakeParts.get(0).y == snakeParts.get(1).y
                || this.direction == Direction.DOWN && snakeParts.get(0).y == snakeParts.get(1).y)
            return;

        this.direction = direction;
    }

    public void move(Apple apple){
        if(isAlive){
            GameObject newHead = createNewHead();

            if(newHead.x > SnakeGame.WIDTH - 1 || newHead.x < 0 || newHead.y > SnakeGame.HEIGHT - 1 || newHead.y < 0
                    || checkCollision(newHead)){
                isAlive = false;
                return;
            }

            snakeParts.add(0, newHead);

            if(apple.x == snakeParts.get(0).x && apple.y == snakeParts.get(0).y){
                apple.isAlive = false;
                return;
            }
            removeTail();
        }
    }

    public GameObject createNewHead(){
        switch (direction){
            case LEFT:
                return new GameObject(snakeParts.get(0).x - 1, snakeParts.get(0).y);
            case DOWN:
                return new GameObject(snakeParts.get(0).x, snakeParts.get(0).y + 1);
            case UP:
                return new GameObject(snakeParts.get(0).x, snakeParts.get(0).y - 1);
            case RIGHT:
                return new GameObject(snakeParts.get(0).x + 1, snakeParts.get(0).y);
        }

        return null;
    }

    public void removeTail(){
        if(snakeParts.size() > 0)
            snakeParts.remove(snakeParts.size() - 1);
    }

    public boolean checkCollision(GameObject gameObject){
        for (GameObject g : snakeParts){
            if(g.x == gameObject.x && g.y == gameObject.y)
                return true;
        }

        return false;
    }

    public int getLength(){
        return snakeParts.size();
    }
}
