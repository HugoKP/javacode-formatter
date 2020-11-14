package br.com.hkp.JavaCodeFormatter.elements;

import java.util.regex.Pattern;

/**
 *
 * @author userhugo
 */
public final class LiteralChar extends Elements
{
    /*[01]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    /**
     *
     */
    @Override
    public void map()
    {
        match = Pattern.compile("'.'").matcher(editedContent);
        
        mapOccurrences("literalchar", 0, false);
        
    }//map()
    
}//classe LiteralChar