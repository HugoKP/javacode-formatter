package br.com.hkp.javacode_styler.elements;

import java.util.regex.Pattern;

/**
 *
 * @author userhugo
 */
public final class Comment extends Elements
{
    /*[01]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    @Override
    public void map()
    {
        match = Pattern.compile("[/][*](.|\\n)*?[*][/]").matcher(editedContent);    
        
        mapOccurrences("comment", 0, false);
        
    }//map()
    
}//classe Comment
