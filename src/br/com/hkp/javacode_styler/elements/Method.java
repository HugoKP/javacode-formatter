package br.com.hkp.javacode_styler.elements;

import java.util.regex.Pattern;

/**
 *
 * @author userhugo
 */
public final class Method extends Elements
{
    /*[01]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    /**
     *
     */
    @Override
    public void map()
    {
        match = Pattern.compile("(\\b[_a-z$][\\w$]*)(\\s|\\n)*([(])").
                matcher(editedContent);
        
        mapOccurrences("method", 1, true);
        
    }//map()
    
    
}//classe Method
