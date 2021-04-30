package br.com.hkp.javacode_styler.tokens;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @version 1.0
 * @since 12 fevereiro de 2021
 * @author Hugo Kaulino Pereira
*/
public final class Constructor extends Tokens
{
    /*[01]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    /**
     *
     */
    @Override
    public void map() 
    {
               
        HashSet<String> set = new HashSet<>(HASH_CAPACITY); 
        
        Matcher match = Pattern.compile("(\\b[A-Z][\\w$]*)(\\s|\\n)*([(])").
                        matcher(javaSourceCode);
                          
        while (match.find()) set.add(match.group(1)); 
                    
        set.forEach
        (
            element ->
            {
                String mark = getMark();

                javaSourceCode =
                        javaSourceCode.replaceAll("\\b" + element + "\\b", mark);

                MAP.put(mark, TAG + "constructor\">" + element + "</span>");
            }
        ); //for
        
    }//map()
    
    
}//classe Method

