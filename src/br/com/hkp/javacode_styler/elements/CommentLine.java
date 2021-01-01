package br.com.hkp.javacode_styler.elements;

import java.util.regex.Pattern;

/**
 *
 * @author userhugo
 */
public final class CommentLine extends Elements
{
    /*[01]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    @Override
    public void map()
    {
        match = Pattern.compile("\\/\\/.*?\\n").matcher(editedContent);    
        
        mapOccurrences("commentLine", 0, false);
        
    }//map()
    
}//classe CommentLine
