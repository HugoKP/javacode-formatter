package br.com.hkp.javacode_styler.tokens;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author userhugo
 */
public final class ClassName extends Tokens
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
        
        Matcher match = Pattern.compile("\\b[A-Z][\\w$]*?[a-z]+[\\w$]*\\b").
                        matcher(javaSourceCode);
                                
        while (match.find()) set.add(match.group()); 
                    
        for (String element: set)
        {
            String mark = getMark();
            
            javaSourceCode = 
                javaSourceCode.replaceAll("\\b" + element + "\\b", mark);
                      
            MAP.put(mark, TAG + "classname\">" + element + "</span>");
            
        }//for
        
    }//map()
    
}//classe ClassName
