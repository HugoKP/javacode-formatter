package br.com.hkp.javacode_styler.tokens;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author userhugo
 */
public final class LiteralChar extends Tokens
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
        
        Matcher match = 
            Pattern.compile("([^\\\\])('.+?')").matcher(javaSourceCode);
                      
        while (match.find()) set.add(match.group(2)); 
                    
        set.forEach
        (
            element ->
            {
                String mark = getMark();

                javaSourceCode = javaSourceCode.replace(element, mark);

                MAP.put
                (
                    mark,
                    TAG + "literalchar\">" + element.replace(FAKE_SLASH, "\\")
                     + "</span>"
                );
            }
        ); //for
        
    }//map()
    
}//classe LiteralChar