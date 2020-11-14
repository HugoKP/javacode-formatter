package br.com.hkp.JavaCodeFormatter.elements;

import java.util.regex.Pattern;

/**
 *
 * @author userhugo
 */
public final class LiteralString extends Elements
{
    /*[01]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    /**
     *
     */
    @Override
    public void map()
    {
        match = Pattern.compile("\"(.|\\n)*?\"").matcher(editedContent);
        
        mapOccurrences("literalstring", 0, false);
        
    }//map()
    
}//classe LiteralString
