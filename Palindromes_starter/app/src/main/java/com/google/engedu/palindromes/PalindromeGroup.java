package com.google.engedu.palindromes;

import android.text.TextUtils;

import java.util.ArrayList;


public class PalindromeGroup {
    protected ArrayList<String> strings = new ArrayList<>();

    public PalindromeGroup(char[] text, int start, int end) {
        strings.add(new String(text, start, end-start));
    }

    public void append(PalindromeGroup other) {
        if (other != null) {
            strings.addAll(other.strings);
        }
    }

    public int length() {
        return strings.size();
    }

    @Override
    public String toString() {
        return TextUtils.join(" ", strings);
    }
}
