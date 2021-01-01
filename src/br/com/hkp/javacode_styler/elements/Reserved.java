package br.com.hkp.javacode_styler.elements;

import java.util.regex.Pattern;

/**
 *
 * @author userhugo
 */
public final class Reserved extends Elements
{
    private static final String RESERVED_REGEX =
          "\\b"
        + "(abstract|"
        + "assert|"
        + "boolean|"
        + "break|"
        + "byte|"
        + "case|"
        + "catch|"
        + "char|"
        + "class|"
        + "const|"
        + "continue|"
        + "default|"
        + "do|"
        + "double|"
        + "else|"
        + "enum|"
        + "extends|"
        + "final|"
        + "finally|"
        + "float|"
        + "for|"
        + "goto|"
        + "if|"
        + "implements|"
        + "import|"
        + "instanceof|"
        + "int|"
        + "interface|"
        + "long|"
        + "native|"
        + "new|"
        + "null|"
        + "package|"
        + "private|"
        + "protected|"
        + "public|"
        + "return|"
        + "short|"
        + "static|"
        + "strictfp|"
        + "super|"
        + "switch|"
        + "synchronized|"
        + "this|"
        + "throw|"
        + "throws|"
        + "transient|"
        + "try|"
        + "void|"
        + "volatile|"
        + "while)\\b";
    
    /*[01]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    /**
     *
     */
    @Override
    public void map()
    {
        match = Pattern.compile(RESERVED_REGEX).matcher(editedContent);
        
        mapOccurrences("reserved", 0, true);
        
    }//map()
    
}//classe Reserved
