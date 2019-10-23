package org.game;


import com.javarush.engine.cell.*;
import org.game.objects.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MinesweeperGame extends Game {

    private static final int SIDE = 9;
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField = 0;
    private int countFlags = 0;

    @Override
    public void initialize(){
        setScreenSize(SIDE, SIDE);
        createGame();
    }

    private void createGame(){
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                gameField[i][j] = new GameObject(j, i, false);
                if (getRandomNumber(10) == 0){
                    gameField[i][j].setMine(true);
                    countMinesOnField++;
                }
                setCellColor(i, j, Color.ORANGE);
            }
        }
        countFlags = countMinesOnField;
        countMineNeighbors();
    }

    private void countMineNeighbors(){
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                List<GameObject> neighbors = getNeighbors(gameField[i][j]);
                gameField[i][j].setCountMineNeighbors(neighbors.stream()
                        .filter(neighbor -> neighbor.isMine())
                        .collect(Collectors.toList()).size());
            }
        }
    }

    private List<GameObject> getNeighbors(GameObject gameObject){
        List<GameObject> naighbors = new ArrayList<>();
        if (!gameField[gameObject.getY()][gameObject.getX()].isMine()){
            if (gameObject.getY() - 1 >= 0 && gameObject.getX() - 1 >= 0){
                naighbors.add(gameField[gameObject.getY()-1][gameObject.getX()-1]);
            }
            if (gameObject.getY() - 1 >= 0){
                naighbors.add(gameField[gameObject.getY()-1][gameObject.getX()]);
            }
            if (gameObject.getX() - 1 >= 0){
                naighbors.add(gameField[gameObject.getY()][gameObject.getX()-1]);
            }
            if (gameObject.getY() + 1 < SIDE ){
                naighbors.add(gameField[gameObject.getY()+1][gameObject.getX()]);
            }
            if (gameObject.getY() + 1 < SIDE && gameObject.getX() - 1 >= 0){
                naighbors.add(gameField[gameObject.getY()+1][gameObject.getX()-1]);
            }
            if (gameObject.getY() + 1 < SIDE && gameObject.getX() + 1 < SIDE){
                naighbors.add(gameField[gameObject.getY()+1][gameObject.getX()+1]);
            }
            if (gameObject.getX() + 1 < SIDE) {
                naighbors.add(gameField[gameObject.getY()][gameObject.getX()+1]);
            }
            if (gameObject.getX() + 1 < SIDE && gameObject.getY() - 1 >= 0){
                naighbors.add(gameField[gameObject.getY()-1][gameObject.getX()+1]);
            }
        }
        return naighbors;
    }

    private void openTile(int x, int y){
        GameObject field = gameField[y][x];
        if (!field.isOpen() && field.getCountMineNeighbors() == 0 && !field.isMine()){
            field.setOpen(true);
            setCellValue(x, y, "");
            setCellColor(x, y, Color.GREEN);
            getNeighbors(field).stream().filter(n-> !n.isOpen() && n.getCountMineNeighbors() == 0)
                    .forEach(n->openTile(n.getX(), n.getY()));
        } else if (!field.isOpen() && field.getCountMineNeighbors() != 0) {
            if (field.isMine()) {
                setCellValue(x, y, MINE);
                field.setOpen(true);
                setCellColor(x, y, Color.RED);
            } else {
                setCellNumber(x, y, field.getCountMineNeighbors());
                field.setOpen(true);
                setCellColor(x, y, Color.GREEN);
            }
        }
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        openTile(x, y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        GameObject field = gameField[y][x];
        if (!field.isOpen()){
            if (!field.isFlag()){
                setCellValue(x, y, FLAG);
                countFlags--;
            } else {
                setCellValue(x, y, "");
                countFlags++;
            }
        }
    }
}
