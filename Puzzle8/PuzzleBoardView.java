package com.google.engedu.puzzle8;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.jar.Attributes;

public class PuzzleBoardView extends View {
    public static final int NUM_SHUFFLE_STEPS = 40;
    private Activity activity;
    private PuzzleBoard puzzleBoard;
    private ArrayList<PuzzleBoard> animation;
    private Random random = new Random();

    public PuzzleBoardView(Context context) {
        super(context);
        activity = (Activity) context;
        animation = null;
    }



    public void initialize(Bitmap imageBitmap) {
            imageBitmap=Bitmap.createScaledBitmap(imageBitmap,650,650,false);
            int width = imageBitmap.getWidth();
            puzzleBoard = new PuzzleBoard(imageBitmap, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (puzzleBoard != null) {
            if (animation != null && animation.size() > 0) {
                puzzleBoard = animation.remove(0);
                puzzleBoard.draw(canvas);
                if (animation.size() == 0) {
                    animation = null;
                    puzzleBoard.reset();
                    Toast toast = Toast.makeText(activity, "Solved! ", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    this.postInvalidateDelayed(500);
                }
            } else {
                puzzleBoard.draw(canvas);
            }
        }
    }

    public void shuffle() {
        if (animation == null && puzzleBoard != null) {

            for(int i=0;i<NUM_SHUFFLE_STEPS;i++)
            {
                puzzleBoard=puzzleBoard.neighbours().get(random.nextInt(puzzleBoard.neighbours().size()));
            }
            puzzleBoard.reset();
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (animation == null && puzzleBoard != null) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (puzzleBoard.click(event.getX(), event.getY())) {
                        invalidate();
                        if (puzzleBoard.resolved()) {
                            Toast toast = Toast.makeText(activity, "Congratulations!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        return true;
                    }
            }
        }
        return super.onTouchEvent(event);
    }

    Comparator<? super PuzzleBoard> comparator=new Comparator<PuzzleBoard>() {
        @Override
        public int compare(PuzzleBoard lhs, PuzzleBoard rhs) {
            return lhs.priority()-rhs.priority();
        }
    };

    public void solve() {
        PriorityQueue<PuzzleBoard> pq=new PriorityQueue<>(1, comparator);
        pq.add(puzzleBoard);
        PuzzleBoard current;
        while (!pq.isEmpty())
        {
            current=pq.poll();

            if(!current.resolved())
            {

                for(PuzzleBoard t:current.neighbours()) {

                    if(!t.equals(current.getPrevBoard())) {
                        pq.add(t);
                    }
                }
            }
            else
            {
                ArrayList<PuzzleBoard> seq=new ArrayList<PuzzleBoard>();

                while(current!= null) {
                    seq.add(current);
                    current = current.getPrevBoard();
                }
                Collections.reverse(seq);
                animation=seq;
                invalidate();
                break;
            }

        }
    }
}
