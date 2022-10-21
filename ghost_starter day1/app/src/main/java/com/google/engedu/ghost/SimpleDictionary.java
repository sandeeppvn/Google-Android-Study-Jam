/*

        NAME: SANDEEP PVN
        email:sandeeppvn@gmail.com

 */

package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<String>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(word);
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    public String BinarySearch(String test,int f,int l)
    {
        if(f>l)
            return null;
        int m=(l+f)/2;
        while(f<=l)
        {
            m=(l+f)/2;
            if(words.get(m).startsWith(test))
                return words.get(m);
            else
            {
                if(words.get(m).compareTo(test) < 0)
                    f=m+1;
                else if(words.get(m).compareTo(test) > 0)
                    l=m-1;
            }
        }
        return null;
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        if(prefix==null)
            return null;
        return BinarySearch(prefix,0,words.size()-1);

    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        return null;
    }
}
