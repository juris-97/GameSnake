package com.java.games;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;

public class SnakeGame extends Game {

    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    private int turnDelay;
    private int score;
    private boolean isGameStopped;
    private static final int GOAL = 28;

    private Snake snake;
    private Apple apple;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame(){
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        isGameStopped = false;
        turnDelay = 300;
        score = 0;
        setScore(score);
        setTurnTimer(turnDelay);
        createNewApple();
        drawScene();
    }

    private void drawScene(){
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                setCellValueEx(x, y, Color.ORANGE, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if(!apple.isAlive){
            createNewApple();
            score+=5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
        }
        if(!snake.isAlive)
            gameOver();
        if(snake.getLength() > GOAL)
            win();
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {

        if(key == Key.LEFT)
            snake.setDirection(Direction.LEFT);
        else if(key == Key.RIGHT)
            snake.setDirection(Direction.RIGHT);
        else if(key == Key.UP)
            snake.setDirection(Direction.UP);
        else if(key == Key.DOWN)
            snake.setDirection(Direction.DOWN);
        else if(key == Key.SPACE){
            if(isGameStopped)
                createGame();
        }
    }

    private void createNewApple(){
        do{
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            this.apple = new Apple(x,y);
        }while (snake.checkCollision(apple));
    }

    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "GAME OVER", Color.PURPLE, 40);
    }

    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.BLACK, "YOU WIN", Color.GOLD, 40);

    }

}
