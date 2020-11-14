package br.com.hkp.JavaCodeFormatter.elements;

import java.util.regex.Pattern;

/**
 *
 * @author userhugo
 */
public final class Constant extends Elements
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
            Pattern.compile("\\b([_A-Z][_A-Z0-9]*?)\\b").matcher(editedContent);
        
        mapOccurrences("constant", 0, true);
        
    }//map()
    
}//classe Constant
