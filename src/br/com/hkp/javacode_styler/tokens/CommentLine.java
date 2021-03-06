package br.com.hkp.javacode_styler.tokens;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Hugo Kaulino Pereira
 */
public final class CommentLine extends Tokens
{
    /*[01]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    @Override
    public void map()
    {
               
        HashSet<String> set = new HashSet<>(HASH_CAPACITY); 
        
        Matcher 
            match = Pattern.compile("\\/\\/.*?\\n").matcher(javaSourceCode);    
                           
        while (match.find()) set.add(match.group()); 
                    
        set.forEach
        (
           element ->
            {
                String mark = getMark();

                javaSourceCode = javaSourceCode.replace(element, mark);

                MAP.put
                (
                    mark,
                    TAG + "comment\">" + element.replace("\n", "</span>\n")
                );
            }
        ); //for
        
    }//map()
    
}//classe CommentLine
