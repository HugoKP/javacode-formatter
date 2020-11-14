package br.com.hkp.JavaCodeFormatter.elements;

import java.util.regex.Pattern;

/**
 *
 * @author userhugo
 */
public final class Function extends Elements
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
        
        mapOccurrences("function", 1, true);
        
    }//map()
    
    
}//classe Function
