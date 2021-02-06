package br.com.hkp.javacode_styler.tokens;

import static br.com.hkp.javacode_styler.global.Global.DIF;
import java.util.HashMap;

/**
 *
 * @author userhugo
 */
public abstract class Tokens
{
    public static String javaSourceCode;
    
    protected static final int HASH_CAPACITY = 2000;
    
    protected static final HashMap<String, String> MAP = 
        new HashMap<>(HASH_CAPACITY);
    
    protected static final String FAKE_SLASH = "\u20e0";
    protected static final String TAG = "<span class=\"" + DIF;
    
    private static int countElements = 0;
    
    /*[01]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    /**
     *    
     */
    public abstract void map();
    
    /*[02]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    protected String getMark()
    {
        return (DIF + "[" + countElements++ + "]");
    }//getMark()
    
    /*[03]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    public static void insertSpanTags()
    {
        for(String mark: MAP.keySet())
            javaSourceCode = javaSourceCode.replace(mark, MAP.get(mark));
    }//StyleTheContent()
  
   
}//classe Tokens
