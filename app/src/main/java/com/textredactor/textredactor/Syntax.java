package com.textredactor.textredactor;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

class Syntax {

    Syntax(EditText editText) {
        if(!SettingsMemory.Syntax.equals("No syntax")) {
            fullReplace(editText);

            if (SettingsMemory.Syntax.equals("HTML")) {
                setHtmlColor(editText, SettingsMemory.SyntaxColor);
            }
        }
    }

    Syntax() {

    }

    void fullReplace(EditText editText) {
        Spannable firstText = new SpannableString(editText.getText().toString());
        editText.getText().replace(0, editText.getText().toString().length(), span(firstText, SettingsMemory.DocTextColor));
    }

    private void setHtmlColor(EditText editText, int color) {

replaceHmtl(editText, color);

    }

    private void replaceHmtl(EditText editText, int color) {
        List<Integer> ind = new ArrayList<>(findWordUpgrade(editText.getText().toString(),"<"));
        List<Integer> indEnd = new ArrayList<>(findWordUpgrade(editText.getText().toString(),">"));

        for (int i = 0; i < ind.size(); i++) {
            int iEnd = 0;
            boolean check = true;
            boolean findCheck = false;

            for (Integer q : indEnd) {
                if(q > ind.get(i)) {
                    iEnd = q;
                    findCheck = true;
                    break;
                }
            }


            if (findCheck) {
                 Spannable spans = new SpannableString(editText.getText().toString().substring(ind.get(i), iEnd + 1));

                 boolean probelMode = false;
                 for (int b = 1; b < spans.length() - 1; b++) {
                     if(!probelMode)
                     check = spans.charAt(b) == '!' || spans.charAt(b) == '-' || spans.charAt(b) == '\n' || spans.charAt(b) == '/' || spans.charAt(b) == 'a' || spans.charAt(b) == 'b' || spans.charAt(b) == 'c' || spans.charAt(b) == 'd' ||
                             spans.charAt(b) == 'e' || spans.charAt(b) == 'f' || spans.charAt(b) == 'j' || spans.charAt(b) == 'g' || spans.charAt(b) == 'h' || spans.charAt(b) == 'i' ||
                             spans.charAt(b) == 'k' || spans.charAt(b) == 'l' || spans.charAt(b) == 'm' || spans.charAt(b) == 'n' || spans.charAt(b) == 'o' || spans.charAt(b) == 'p' ||
                             spans.charAt(b) == 'q' || spans.charAt(b) == 'r' || spans.charAt(b) == 's' || spans.charAt(b) == 't' || spans.charAt(b) == 'u' || spans.charAt(b) == 'v' ||
                             spans.charAt(b) == 'w' || spans.charAt(b) == 'x' || spans.charAt(b) == 'y' || spans.charAt(b) == 'z' || spans.charAt(b) == ' '|| spans.charAt(b) == '1' ||
                             spans.charAt(b) == '2'|| spans.charAt(b) == '3' || spans.charAt(b) == '4'|| spans.charAt(b) == '5' || spans.charAt(b) == '6' || spans.charAt(b) == '7' ||
                             spans.charAt(b) == '8' || spans.charAt(b) == '9' || spans.charAt(b) == '0' || spans.charAt(b) == 'A' || spans.charAt(b) == 'B' || spans.charAt(b) == 'C' ||
                             spans.charAt(b) == 'D' || spans.charAt(b) == 'E' || spans.charAt(b) == 'F' || spans.charAt(b) == 'J' || spans.charAt(b) == 'G' || spans.charAt(b) == 'H' || spans.charAt(b) == 'I' ||
                             spans.charAt(b) == 'K' || spans.charAt(b) == 'L' || spans.charAt(b) == 'M' || spans.charAt(b) == 'N' || spans.charAt(b) == 'O' || spans.charAt(b) == 'P' ||
                             spans.charAt(b) == 'Q' || spans.charAt(b) == 'R' || spans.charAt(b) == 'S' || spans.charAt(b) == 'T' || spans.charAt(b) == 'U' || spans.charAt(b) == 'V' ||
                             spans.charAt(b) == 'W' || spans.charAt(b) == ';' || spans.charAt(b) == 'X' || spans.charAt(b) == 'Y' || spans.charAt(b) == 'Z';
                     else
                         check = spans.charAt(b) != '>' && spans.charAt(b) != '<';

                     if(!check)
                         break;

                     if (spans.charAt(b) == ' ' && spans.charAt(1) == '/') {
                         check = false;
                         break;
                     }
                     else if (spans.charAt(b) == ' ' && spans.charAt(1) != '/') {
                         probelMode = true;
                     }

                 }

                 if(spans.toString().equals("<>"))
                     check = true;

                 if(check)
                     editText.getText().replace(ind.get(i), iEnd + 1, span(spans, color));
             }

         }


        }

    private Spannable span(Spannable spans, int color) {
        spans.setSpan(new ForegroundColorSpan(color), 0, spans.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spans;
    }

    private void replace(EditText editText, String word, int color) {
        for (Integer b: findWordUpgrade(editText.getText().toString(), word)) {
            Spannable spans = new SpannableString(word);
            editText.getText().replace(b, b + word.length(), span(spans, color));
        }
    }

   List<Integer> findWordUpgrade(String textString, String word) {
        List<Integer> indexes = new ArrayList<>();
        new StringBuilder();
        String lowerCaseTextString = textString.toLowerCase();
        String lowerCaseWord = word.toLowerCase();
        int wordLength = 0;

        int index = 0;
        while(index != -1){
            index = lowerCaseTextString.indexOf(lowerCaseWord, index + wordLength); //Slight improvement
            if (index != -1) {
                indexes.add(index);
            }
            wordLength = word.length();
        }
        return indexes;
    }

}
