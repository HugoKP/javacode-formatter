package br.com.hkp.JavaCodeFormatter;

import global.Global;
import static global.Global.fileChooserSettings;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author userhugo
 */
public final class CodeMarker
{
    private static final HashMap<String, String> HASH_MAP = 
        new HashMap<>(1000);
    
    /*[01]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    private static String readTextFile(final File file) throws IOException
    {
        return 
            new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
       
    }//readTextFile()
    
    /*[02]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    private static void map(File dir) throws IOException
    {
        Pattern REGEX = Pattern.compile("<span class=\"xw52fz_.+?</span>");
        
        File[] listFiles = dir.listFiles();
        
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
    
    /*[03]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    private static void editHtml(File file) throws IOException
    {
        int buffer = (int)file.length() + 10000;
        
        String htmlContent = readTextFile(file);
        
        for (String element: HASH_MAP.keySet())
        {
            htmlContent = htmlContent.replace(element, HASH_MAP.get(element));
        }//for
        
        BufferedWriter editedFile = 
            new BufferedWriter
            (
                new FileWriter(file, StandardCharsets.UTF_8), buffer
            );
        
              
        editedFile.write(htmlContent);
        
        editedFile.close();
        
    }//editHtml()
    
    /*[04]---------------------------------------------------------------------
    
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
               
            map(dir);
           
            /*
            Obtem arquivoHTML a ser editado
            */
            filter = new FileNameExtensionFilter("HTML", "html", "htm", "HTML");

            File file = Global.choose
                        (
                            "Selecione o arquivo HTML a ser editado",
                            filter,
                            false
                        );

            if (file == null) System.exit(0);
             
            editHtml(file);
           
        }
        catch (IOException e)
        {
            System.err.println(e);
        }
       
    }//main()
    
}//classe CodeMarker
