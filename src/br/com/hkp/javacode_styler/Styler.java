package br.com.hkp.javacode_styler;

import br.com.hkp.javacode_styler.tokens.LiteralChar;
import br.com.hkp.javacode_styler.tokens.ClassName;
import br.com.hkp.javacode_styler.tokens.Comment;
import br.com.hkp.javacode_styler.tokens.CommentLine;
import br.com.hkp.javacode_styler.tokens.Constant;
import br.com.hkp.javacode_styler.tokens.Tokens;
import br.com.hkp.javacode_styler.tokens.Method;
import br.com.hkp.javacode_styler.tokens.LiteralString;
import br.com.hkp.javacode_styler.tokens.Reserved;
import br.com.hkp.javacode_styler.global.Global;
import static br.com.hkp.javacode_styler.global.Global.readTextFile;
import static br.com.hkp.javacode_styler.global.Global.writeTextFile;
import br.com.hkp.javacode_styler.tokens.Annotation;
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
    private final Annotation annotation;
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
        literalString = new LiteralString();
        literalChar = new LiteralChar();
        annotation = new Annotation();
        className = new ClassName();
        constant = new Constant();
        method = new Method();
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
        Tokens.javaSourceCode = readTextFile(inputFile);
              
        Tokens.javaSourceCode = 
            Tokens.javaSourceCode.replace("&", "&amp;").
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
        
        int countLinesReaded = countLines(Tokens.javaSourceCode);
        
        int padding = getNumberOfDigits(countLinesReaded);
                 
        StringBuilder lineNumbers = 
            new StringBuilder((padding + 5) * countLinesReaded + 100);
        
        lineNumbers.append("\n<div class=\"linenumber\">\n");
        
        for (int line = 1; line <= countLinesReaded; line++)
        {  
            String lineNumber = String.format("%0" + padding + "d", line);
            lineNumbers.append("<span id=\"").append(lineNumber).append("\">").
            append(lineNumber).append("</span></br>");
        }
     
        lineNumbers.append("\n</div>\n");
      
        comment.map(); 
        commentLine.map();
        literalString.map();
        literalChar.map();
        annotation.map();
        reserved.map();
        className.map();
        constant.map();
        method.map();
              
        Tokens.insertSpanTags();
          
        Tokens.javaSourceCode =
            "<pre><code>" + Tokens.javaSourceCode + "\n</code></pre>";
           
        writeTextFile
        (outputFile,
            HEAD +
            "\n<div class=\"javacode\" style=\"width:" +
            (630 + padding * 9) +
            "px;\">\n" +
            lineNumbers.toString() +
            Tokens.javaSourceCode +
            "\n</div>\n" +
            FOOTER
        );
     
    }//createHtmlFile()
    
    /*[05]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    public static void style(final File sourceFolder) throws IOException
    {
                
        File[] listFiles = sourceFolder.listFiles();
        
        for (File file: listFiles)
        {
            if (file.getName().endsWith(".java"))
            {
                System.out.println(file.getName());
                Styler j = new Styler(file);
                j.createHtmlFile();
            }
            else if (file.isDirectory()) style(file);
        }
      
    }//style()
      
    /*[06]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    public static void main(String[] args)
    {
        Global.fileChooserSettings();
           
        /*
        Obtem o diretorio src do projeto
        */
        FileNameExtensionFilter filter = 
            new FileNameExtensionFilter("Pasta src", "java");
        
        File sourceFolder =
            Global.choose("Selecione a pasta src", filter, true);
        
        if (sourceFolder == null) System.exit(0);
        
        try
        {
            style(sourceFolder);
        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
    }//main()
    
   
}//classe Styler