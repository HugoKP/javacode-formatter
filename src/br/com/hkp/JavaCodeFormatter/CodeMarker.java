package br.com.hkp.JavaCodeFormatter;

import global.Global;
import static global.Global.fileChooserSettings;
import static global.Global.readTextFile;
import static global.Global.writeTextFile;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.filechooser.FileNameExtensionFilter;

/******************************************************************************
 *  
 * @since 27 de dezembro de 2020 v1.0
 * @version 1.0
 * @author Hugo Kaulino Pereira
 *****************************************************************************/
public final class CodeMarker
{
    private static final HashMap<String, String> HASH_MAP = 
        new HashMap<>(1000);
    
    /*[01]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    private static void collectElements(final File dir) throws IOException
    {
        Pattern REGEX = Pattern.compile("<span class=\"xw52fz_.+?</span>");
        
        File[] listFiles = dir.listFiles(new HtmlFilter());
        
        for (File file: listFiles)
        {
              
            Matcher m = REGEX.matcher(readTextFile(file));
            
            while(m.find())
            {
                String tag = m.group();
                
                String element = 
                    '*' +
                    tag.substring(tag.indexOf('>') + 1, tag.lastIndexOf('<')) +
                    '*';
                
                if (!HASH_MAP.containsKey(element)) HASH_MAP.put(element,tag);
                
            }//while
            
        }//for
        
        
    }//map()
    
    /*[02]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    private static void editDocumentation(final File file) throws IOException
    {
        String htmlContent = readTextFile(file);
        
        for (String element: HASH_MAP.keySet())
        {
            htmlContent = htmlContent.replace(element, HASH_MAP.get(element));
        }//for
        
        writeTextFile(file, htmlContent);
        
    }//editHtml()
    
    /*[03]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    public static void main(String[] args)
    {
        fileChooserSettings();
        
        try
        {
            /*
            Obtem o diretorio onde estao os arquivos HTML com fontes java
            */
            FileNameExtensionFilter filter = 
                new FileNameExtensionFilter("Diret\u00f3rio", "x");

            File dir = Global.choose("Selecione o Diret\u00f3rio",filter, true);

            if (dir == null) System.exit(0);
               
            collectElements(dir);
           
            /*
            Obtem arquivo HTML a ser editado
            */
            filter = new FileNameExtensionFilter("HTML", "html", "htm", "HTML");

            File documentationFile = Global.choose
                        (
                            "Selecione o arquivo HTML a ser editado",
                            filter,
                            false
                        );

            if (documentationFile == null) System.exit(0);
             
            editDocumentation(documentationFile);
           
        }
        catch (IOException e)
        {
            System.err.println(e);
        }
       
    }//main()
    
    /*-------------------------------------------------------------------------
     *                             Classe Interna
     ------------------------------------------------------------------------*/
    private static final class HtmlFilter implements FilenameFilter
    {
        @Override
        public boolean accept(File dir, String filename)
        {
            return filename.endsWith(".html");
        }//accept()
        
    }//classe HtmlFilter
    
}//classe CodeMarker
