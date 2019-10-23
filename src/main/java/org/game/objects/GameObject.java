package org.game.objects;

public class GameObject {

    private int x, y;
    private boolean isMine;
    private int countMineNeighbors;
    private boolean isOpen;
    private boolean isFlag;

    public GameObject(int x, int y, boolean isMine) {
        this.x = x;
        this.y = y;
        this.isMine = isMine;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public int getCountMineNeighbors() {
        return countMineNeighbors;
    }

    public void setCountMineNeighbors(int countMineNeighbors) {
        this.countMineNeighbors = countMineNeighbors;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }
}
