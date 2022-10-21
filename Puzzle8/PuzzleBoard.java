package com.google.engedu.puzzle8;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;


public class PuzzleBoard {

    private static final int NUM_TILES = 3;
    private static final int[][] NEIGHBOUR_COORDS = {
            { -1, 0 },
            { 1, 0 },
            { 0, -1 },
            { 0, 1 }
    };
    private ArrayList<PuzzleTile> tiles;
    private int STEPS=0;
    private PuzzleBoard prevBoard;
    PuzzleBoard(Bitmap bitmap, int parentWidth) {
        tiles=new ArrayList<PuzzleTile>();
        STEPS=0;
        bitmap = Bitmap.createScaledBitmap(bitmap, parentWidth, parentWidth, false);
        int childWidth = (parentWidth/NUM_TILES);

        for(int i=0;i<NUM_TILES;i++)
        {
            for(int j=0;j<NUM_TILES;j++)
            {
                Bitmap tmp=Bitmap.createBitmap(bitmap,i*childWidth,j*childWidth,childWidth,childWidth);
                tiles.add(new PuzzleTile(tmp,i*NUM_TILES+j));

             }
        }
        tiles.set(8,null);
    }

    PuzzleBoard(PuzzleBoard otherBoard) {
        tiles = (ArrayList<PuzzleTile>) otherBoard.tiles.clone();
        this.STEPS= otherBoard.STEPS+1;//steps+1;
        prevBoard=otherBoard;
    }

    public void reset() {

        // Nothing for now but you may have things to reset once you implement the solver.
        prevBoard = null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        return tiles.equals(((PuzzleBoard) o).tiles);
    }

    public void draw(Canvas canvas) {
        if (tiles == null) {
            return;
        }
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                tile.draw(canvas, i / NUM_TILES, i % NUM_TILES);
            }
        }
    }

    public boolean click(float x, float y) {
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                if (tile.isClicked(x, y, i / NUM_TILES, i % NUM_TILES)) {
                    return tryMoving(i / NUM_TILES, i % NUM_TILES);
                }
            }
        }
        return false;
    }

    private boolean tryMoving(int tileX, int tileY) {
        for (int[] delta : NEIGHBOUR_COORDS) {
            int nullX = tileX + delta[0];
            int nullY = tileY + delta[1];
            if (nullX >= 0 && nullX < NUM_TILES && nullY >= 0 && nullY < NUM_TILES &&
                    tiles.get(XYtoIndex(nullX, nullY)) == null) {
                swapTiles(XYtoIndex(nullX, nullY), XYtoIndex(tileX, tileY));
                return true;
            }

        }
        return false;
    }

    public PuzzleBoard getPrevBoard()
    {
        return prevBoard;
    }

    public boolean resolved() {
        for (int i = 0; i < NUM_TILES * NUM_TILES - 1; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile == null || tile.getNumber() != i)
                return false;
        }
        return true;
    }

    private int XYtoIndex(int x, int y) {
        return y +x  * NUM_TILES;
    }

    protected void swapTiles(int i, int j) {
        PuzzleTile temp = tiles.get(i);
        tiles.set(i, tiles.get(j));
        tiles.set(j, temp);
    }

    public ArrayList<PuzzleBoard> neighbours() {
        ArrayList<PuzzleBoard> neigh =new ArrayList<PuzzleBoard>();
        int empty=0;
        for(PuzzleTile i : tiles)
        {
            if(i==null) {
                empty = tiles.indexOf(i);
                break;
            }
        }
        for (int[] delta : NEIGHBOUR_COORDS)
        {
            int X = empty / NUM_TILES + delta[0];
            int Y = empty % NUM_TILES + delta[1];

            if(X>=0 && X<NUM_TILES && Y>=0 && Y<NUM_TILES)
            {

                PuzzleBoard otherBoard=new PuzzleBoard(this);
                otherBoard.swapTiles(empty,XYtoIndex(X,Y));
                neigh.add(otherBoard);

            }

        }

        return neigh;
    }

    public int priority() {
        int manhantan=0;
        int n;
        PuzzleTile pz;
        for(int i=0;i<NUM_TILES*NUM_TILES;i++) {
            pz = tiles.get(i);
            if (pz != null)
            {
                n=pz.getNumber();
                manhantan+=Math.abs(i/NUM_TILES - n/NUM_TILES) + Math.abs(i%NUM_TILES - n%NUM_TILES);
            }
        }
        return manhantan+STEPS;
    }

}
