package br.com.hkp.javacode_styler.tokens;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author userhugo
 */
public final class Reserved extends Tokens
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
               
        HashSet<String> set = new HashSet<>(HASH_CAPACITY); 
        
        Matcher match = Pattern.compile(RESERVED_REGEX).matcher(javaSourceCode);
                         
        while (match.find()) set.add(match.group()); 
                    
        for (String element: set)
        {
            String mark = getMark();
          
            javaSourceCode = 
                javaSourceCode.replaceAll("\\b" + element + "\\b", mark);
             
            MAP.put(mark, TAG + "reserved\">" + element + "</span>");
            
        }//for
        
    }//map()
    
}//classe Reserved
