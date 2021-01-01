package br.com.hkp.javacode_styler.elements;

import java.util.regex.Pattern;

/**
 *
 * @author userhugo
 */
public final class ClassName extends Elements
{
    /*[01]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    /**
     *
     */
    @Override
    public void map()
    {
        match = 
            Pattern.compile("\\b[A-Z][\\w$]*?[a-z]+[\\w$]*\\b").
            matcher(editedContent);
        
        mapOccurrences("classname", 0, true);
        
    }//map()
    
}//classe ClassName
