package br.com.hkp.JavaCodeFormatter;

import br.com.hkp.JavaCodeFormatter.elements.LiteralChar;
import br.com.hkp.JavaCodeFormatter.elements.ClassName;
import br.com.hkp.JavaCodeFormatter.elements.Comment;
import br.com.hkp.JavaCodeFormatter.elements.CommentLine;
import br.com.hkp.JavaCodeFormatter.elements.Constant;
import br.com.hkp.JavaCodeFormatter.elements.Elements;
import br.com.hkp.JavaCodeFormatter.elements.Method;
import br.com.hkp.JavaCodeFormatter.elements.LiteralString;
import br.com.hkp.JavaCodeFormatter.elements.Reserved;
import global.Global;
import static global.Global.fileChooserSettings;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author userhugo
 */
public final class JavaSource2Html
{
    private static final String HEAD =
"<!DOCTYPE html>\n" +
"<html lang=\"pt-br\">\n" +
"<head>\n" +
"    <meta charset=\"UTF-8\">\n" +
"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"    <link rel=\"stylesheet\" href=\"../css/javacode.css\"/>\n" +
"</head>\n" + 
"<body>\n";
    
    private static final String FOOTER =
"</body>\n" + "</html>";
    
    private static final String ASPAS = "\uff02";
    private static final String ASPAS_IN_STRING = "\\\"";
    private static final String ASPAS_IN_CHAR = "'\"'";
    private static final String FAKE_ASPAS_IN_STRING = "\\" + ASPAS;
    private static final String FAKE_ASPAS_IN_CHAR = "'" + ASPAS + "'";
    
    /*
    Quantas linhas foram lidas do arquivo com o codigo fonte java
    */
    private int countLinesReaded;
    
    /*
    buffer para otimizar processos de leitura e gravacao
    */
    private final int buffer;
    
    /*
    Arquivo que serah lido e arquivo que sera gravado
    */
    private final File inputFile;
    private final File outputFile;
    
    private final Comment comment;
    private final CommentLine commentLine;
    private final LiteralChar literalChar;
    private final ClassName className;
    private final Constant constant;
    private final Method method;
    private final LiteralString literalString;
    private final Reserved reserved;
    
   
    /*[00]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    /**
     * Construtor da classe.
     * 
     * @param file O arquivo que deve ser processado
     * 
     * @throws java.io.IOException
     */
    public JavaSource2Html(final File file) throws IOException
    {
      
        inputFile = file;
        
        String absoluteFileName = inputFile.getAbsolutePath();
        
        buffer = (int)inputFile.length();
       
        outputFile = new File(absoluteFileName.replace(".java",".html"));
          
        countLinesReaded = 0;
        
        comment = new Comment();
        commentLine = new CommentLine();
        literalChar = new LiteralChar();
        className = new ClassName();
        constant = new Constant();
        method = new Method();
        literalString = new LiteralString();
        reserved = new Reserved();
      
    }//construtor
 
    /*[01]---------------------------------------------------------------------
    Calcula quantos digitos tem o numero de linhas do programa. Serve para 
    formatar a coluna com os indices das linhas do codigo fonte
    -------------------------------------------------------------------------*/
    private int getNumberOfDigits(int number)
    {
        int digits = 1;
        while ((number = (number/10)) != 0) digits++;
        return digits;
    }//getNumberOfDigits()
    
    /*[02]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    /**
     * Le o arquivo e copia seu conteudo para uma variavel interna do objeto.
     * 
     * @throws IOException Em caso de erro de IO.
     */
    public void readFile() throws IOException
    {
           
        BufferedReader htmlFile = 
            new BufferedReader
            (
                new FileReader(inputFile, StandardCharsets.UTF_8), buffer
            );
        
        StringBuilder content = new StringBuilder(buffer);
        
        String line;
        
        while ((line = htmlFile.readLine()) != null)
        {
            countLinesReaded++;
            content.append(line).append("\n");
        }
        
        htmlFile.close();
        
        Elements.editedContent = 
            content.toString().replace("&", "&amp;").
            replace("<", "&lt;").replace(">", "&gt;").
            replace(ASPAS_IN_STRING, FAKE_ASPAS_IN_STRING).
            replace(ASPAS_IN_CHAR, FAKE_ASPAS_IN_CHAR);
        
             
    }//readFile()
    
     /*[03]---------------------------------------------------------------------
        
    -------------------------------------------------------------------------*/
    /**
     * Grava um novo arquivo. 
     * 
     * @throws IOException Em caso de erro de IO.
     */
    public void createNewFile() throws IOException
    {
        BufferedWriter htmlFile = 
            new BufferedWriter
            (
                new FileWriter(outputFile, StandardCharsets.UTF_8), buffer
            );
        
        int padding = getNumberOfDigits(countLinesReaded);
        
                
        StringBuilder lineNumbers = 
            new StringBuilder(padding * countLinesReaded + 1000);
        
        lineNumbers.append("\n<div class=\"linenumber\">\n");
        
        for (int line = 0; line <= countLinesReaded; line++)
            lineNumbers.append(String.format("%0" + padding + "d</br>", line));
     
        lineNumbers.append("\n</div>\n");
      
        comment.map(); 
        commentLine.map();
        literalString.map();
        literalChar.map();
        reserved.map();
        className.map();
        constant.map();
        method.map();
              
        for(String mark: Elements.MAP.keySet())
            Elements.editedContent = 
                Elements.editedContent.replace(mark, Elements.MAP.get(mark));
            
        
        Elements.editedContent =
            "<pre><code>\n" + 
            Elements.editedContent.replace(FAKE_ASPAS_IN_STRING, ASPAS_IN_STRING).
            replace(FAKE_ASPAS_IN_CHAR, ASPAS_IN_CHAR) +
            "\n</code></pre>";
            
        htmlFile.write
        (
            HEAD +
            "\n<div class=\"javacode\" style=\"width:" +
            (630 + padding * 9) +
            "px;\">\n" +
            lineNumbers.toString() +
            Elements.editedContent +
            "\n</div>" +
            FOOTER
        );
        
        htmlFile.close();
        
         
    }//createNewFile()
      
    /*[04]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    public static void main(String[] args)
    {
        fileChooserSettings();
           
        /*
        Obtem o arquivoL com o fonte java
        */
        FileNameExtensionFilter filter = 
            new FileNameExtensionFilter("C\u00f3digo Fonte Java", "java");
        
        File file = Global.choose("Selecione o arquivo", filter, false);
        
        if (file == null) System.exit(0);
        
        try
        {
            JavaSource2Html j = new JavaSource2Html(file);
            j.readFile();
            j.createNewFile();
        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
    
       
    }//main()
    
}//classe JavaSource2Html

