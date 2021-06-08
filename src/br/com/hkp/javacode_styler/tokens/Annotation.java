package br.com.hkp.javacode_styler.tokens;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since 4 de fevereiro de 2021
 * @version 1.0
 * @author Hugo Kaulino Pereira
 */
public final class Annotation extends Tokens
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
        
        Matcher match = Pattern.compile("@[\\w$][\\w]*").matcher(javaSourceCode);
                          
        while (match.find()) set.add(match.group()); 
                    
        set.forEach
        (
            element ->
            {
                String mark = getMark();

                javaSourceCode = javaSourceCode.replace(element, mark);

                MAP.put(mark, TAG + "annotation\">" + element + "</span>");
            }
        );//for
        
    }//map()
    
    
}//classe Annotation
