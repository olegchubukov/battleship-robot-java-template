package no.sonat.battleships.models;

import java.util.ArrayList;

public class Cell {

    private ShootResult shootResult;
    private ArrayList<Cell> neighbours;

    public Cell(ShootResult shootResult, ArrayList<Cell> neighbours) {
        this.shootResult = shootResult;
        this.neighbours = neighbours;
    }
}
