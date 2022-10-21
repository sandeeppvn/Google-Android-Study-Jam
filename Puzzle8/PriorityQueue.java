package com.google.engedu.puzzle8;

import java.util.ArrayList;

class element
{
    private PuzzleBoard board;

    public PuzzleBoard getdata()
    {
        return board;
    }

    public int getpty()
    {
        return pty;
    }

    public void setValues(PuzzleBoard board, int p)
    {
        this.board=board;
        pty=p;
    }
}


public class MinPQueue {
    private ArrayList<PuzzleBoard> q;
    private int count;

    public MinPQueue()
    {
        q = new ArrayList<>();
    }

    public boolean isEmpty()
    {return (q.size()==0);}

    public void add(PuzzleBoard board)
    {
        if(q.isEmpty())
            q.add(board);
        else
            

        temp=new element();
        temp.setvalues(key,p);

        q[count++]=temp;
        int j=count-2;

        while(j>=0 && (q[j].getpty() > temp.getpty()))
        {
            q[j+1]=q[j];
            j--;
        }
        q[j+1]=temp;
    }

    public element remove() {
        element t;
        if (count == 0)
            return null;
        else {
            t = q[0];
            for (int i = 1; i < count; i++)
                q[i - 1] = q[i];
            count--;
        }
        return t;
    }

}















