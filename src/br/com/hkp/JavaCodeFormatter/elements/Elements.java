package br.com.hkp.JavaCodeFormatter.elements;

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
    
    private static HashSet<String> set;
    
    private static int countElements = 0;
    
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
        final String typeOfClass,
        final int group,
        final boolean anchor
    ) 
    {
               
        set = new HashSet(HASH_CAPACITY); 
                   
        while (match.find()) set.add(match.group(group)); 
                    
        for (String element: set)
        {
            String mark = "#[" + countElements++ + "]";
            
            if (anchor)
                editedContent = 
                    editedContent.replaceAll("\\b" + element + "\\b", mark);
            else
                editedContent = editedContent.replace(element, mark);
              
            MAP.put
            (
                mark, 
                "<span class=\"xw52fz_" + typeOfClass + "\">" + element + 
                "</span>"
            );
            
        }//for
        
    }//mapOccurrences()
  
    
}//classe Elements
