package br.com.hkp.javacode_styler.elements;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;

/**
 *
 * @author userhugo
 */
public abstract class Elements
{
    private static final int HASH_CAPACITY = 2000;
           
    public static String editedContent;
  
    public static final HashMap<String, String> MAP = 
        new HashMap<>(HASH_CAPACITY);
    
    protected static Matcher match;
     
    private static int countElements = 0;
    
    public static final String FAKE_QUOT = "\uff02";
    public static final String FAKE_SLASH = "\u20e0";
    
    /*[00]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    /**
     *    
     */
    public abstract void map();
  
    /*[01]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    public void mapOccurrences
    (
        String type,
        final int group,
        final boolean anchor
    ) 
    {
               
        HashSet<String> set = new HashSet<>(HASH_CAPACITY); 
        
        boolean commentLine = type.equals("commentLine");
        
        if (commentLine) type = "comment";
                   
        while (match.find()) set.add(match.group(group)); 
                    
        for (String element: set)
        {
            String mark = "#[" + countElements++ + "]";
            
            if (anchor)
                editedContent = 
                    editedContent.replaceAll("\\b" + element + "\\b", mark);
            else
                editedContent = editedContent.replace(element, mark);
            
            if (commentLine)
                element = element.replace("\n", "</span>\n");
            else
                element = element + "</span>";
              
            MAP.put(mark, "<span class=\"xw52fz_" + type + "\">" + element);
            
        }//for
        
    }//mapOccurrences()
  
    
}//classe Elements
