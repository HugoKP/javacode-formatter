package br.com.hkp.javacode_styler.tokens;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author userhugo
 */
public final class LiteralString extends Tokens
{
    private static final String DOUBLE_SLASH = "\\\\";
    private static final String DOUBLE_FAKE_SLASH = FAKE_SLASH + FAKE_SLASH;
           
    /*[01]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    /**
     *
     */
    @Override
    public void map()     
    {
        HashSet<String> set = new HashSet<>(HASH_CAPACITY); 
        
        javaSourceCode = 
            javaSourceCode.replace(DOUBLE_SLASH, DOUBLE_FAKE_SLASH);
          
             
        Matcher match = 
            Pattern.compile("([^'\\\\])((\"\")|(\".*?[^\\\\]\"))").
            matcher(javaSourceCode);
        
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
                    TAG + "literalstring\">" + element.replace(FAKE_SLASH,"\\")
                     + "</span>"
                );
            }
        ); //for
        
    }//map()
    
       
}//classe LiteralString
