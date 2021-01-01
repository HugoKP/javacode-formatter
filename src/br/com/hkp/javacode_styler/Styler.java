package br.com.hkp.javacode_styler;

import br.com.hkp.javacode_styler.elements.LiteralChar;
import br.com.hkp.javacode_styler.elements.ClassName;
import br.com.hkp.javacode_styler.elements.Comment;
import br.com.hkp.javacode_styler.elements.CommentLine;
import br.com.hkp.javacode_styler.elements.Constant;
import br.com.hkp.javacode_styler.elements.Elements;
import br.com.hkp.javacode_styler.elements.Method;
import br.com.hkp.javacode_styler.elements.LiteralString;
import br.com.hkp.javacode_styler.elements.Reserved;
import br.com.hkp.javacode_styler.global.Global;
import static br.com.hkp.javacode_styler.global.Global.fileChooserSettings;
import static br.com.hkp.javacode_styler.global.Global.readTextFile;
import static br.com.hkp.javacode_styler.global.Global.writeTextFile;
import java.io.File;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;

/******************************************************************************
 * A aplicacao converte um codigo fonte Java em um HTML que permite exibir este
 * fonte no navegador web.
 * 
 * @since 27 de dezembro de 2020 v1.0
 * @version 1.0
 * @author Hugo Kaulino Pereira
 *****************************************************************************/
public final class Styler
{
    private static final String HEAD =
"<!DOCTYPE html>\n" +
"<html lang=\"en\">\n" +
"<head>\n" +
"\t<meta charset=\"UTF-8\">\n" +
"\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
+
"\t<link rel=\"stylesheet\" href=\"../css/javacode.css\"/>\n" +
"\t<title>Java Code</title>\n"  +  
"</head>\n" + 
"<body>\n";
    
    private static final String FOOTER =
"</body>\n" + "</html>";

    /*
    Arquivo que serah lido e arquivo que sera gravado
    */
    private final File inputFile;
    private final File outputFile;
    
    private final Comment comment;
    private final CommentLine commentLine;
    private final LiteralString literalString;
    private final LiteralChar literalChar;
    private final Reserved reserved;
    private final ClassName className;
    private final Constant constant;
    private final Method method;
   
    /*[00]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    /**
     * Construtor da classe.
     * 
     * @param file O arquivo que deve ser processado
     * 
     * @throws java.io.IOException
     */
    public Styler(final File file) throws IOException
    {
      
        inputFile = file;
   
        outputFile = 
            new File(inputFile.getAbsolutePath().replace(".java",".html"));
             
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
    Conta quantas linhas tem a String que recebeu o conteudo do arquivo texto 
    com o codigo fonte java.
    -------------------------------------------------------------------------*/
    private int countLines(final String str)
    {
        if (str.length() == 0) return 0;
        
        int index = 0;
        int count = 0;
 
        while ((index = (str.indexOf('\n', index)) + 1) != 0) count++;
        
        if (str.charAt(str.length() - 1) != '\n') count++;
  
        return count;
    }//countLines()
    
    /*[03]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    private void readFile() throws IOException
    {
        Elements.editedContent = readTextFile(inputFile);
              
        Elements.editedContent = 
            Elements.editedContent.replace("&", "&amp;").
            replace("<", "&lt;").replace(">", "&gt;").
            replace("\r\n", "\n").replace("\r", "\n");
             
    }//readFile()
    
     /*[04]---------------------------------------------------------------------
        
    -------------------------------------------------------------------------*/
    /**
     * Grava um novo arquivo. 
     * 
     * @throws IOException Em caso de erro de IO.
     */
    public void createHtmlFile() throws IOException
    {
        readFile();
        
        int countLinesReaded = countLines(Elements.editedContent);
        
        int padding = getNumberOfDigits(countLinesReaded);
                 
        StringBuilder lineNumbers = 
            new StringBuilder((padding + 5) * countLinesReaded + 100);
        
        lineNumbers.append("\n<div class=\"linenumber\">\n");
        
        for (int line = 1; line <= countLinesReaded; line++)
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
            "<pre><code>" + 
            Elements.editedContent.
            replace(Elements.FAKE_QUOT, "\"").
            replace(Elements.FAKE_SLASH, "\\") +
            "\n</code></pre>";
           
        writeTextFile
        (
            outputFile,
            HEAD +
            "\n<div class=\"javacode\" style=\"width:" +
            (630 + padding * 9) +
            "px;\">\n" +
            lineNumbers.toString() +
            Elements.editedContent +
            "\n</div>\n" +
            FOOTER
        );
     
    }//createHtmlFile()
      
    /*[05]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    public static void main(String[] args)
    {
        fileChooserSettings();
           
        /*
        Obtem o arquivo com o fonte java
        */
        FileNameExtensionFilter filter = 
            new FileNameExtensionFilter("C\u00f3digo Fonte Java", "java");
        
        File file = Global.choose("Selecione o arquivo", filter, false);
        
        if (file == null) System.exit(0);
        
        try
        {
            Styler j = new Styler(file);
            j.createHtmlFile();
        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
    }//main()
    
}//classe Styler