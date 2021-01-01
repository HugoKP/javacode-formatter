package br.com.hkp.javacode_styler.elements;

import java.util.regex.Pattern;

/**
 *
 * @author userhugo
 */
public final class LiteralString extends Elements
{
    private static final String QUOT_IN_CHAR = "'\"'";
    private static final String FAKE_QUOT_IN_CHAR = "'" + FAKE_QUOT + "'";
    private static final String QUOT_IN_STRING = "\\\"";
    private static final String FAKE_QUOT_IN_STRING = "\\" + FAKE_QUOT;
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
        editedContent = 
            editedContent.replace(DOUBLE_SLASH, DOUBLE_FAKE_SLASH).
            replace(QUOT_IN_STRING, FAKE_QUOT_IN_STRING).
            replace(QUOT_IN_CHAR, FAKE_QUOT_IN_CHAR);
        
        match = Pattern.compile("\"(.|\\n)*?\"").matcher(editedContent);
        
        mapOccurrences("literalstring", 0, false);
                 
    }//map()
    
}//classe LiteralString
