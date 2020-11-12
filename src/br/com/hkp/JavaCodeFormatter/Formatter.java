package br.com.hkp.JavaCodeFormatter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/******************************************************************************
 * 
 * @author "Hugo"
 * @since 9 de novembro de 2020 v1.0
 * @version v1.0
 *****************************************************************************/
public final class Formatter
{
    private static final int INITIAL_HASH_CAPACITY = 2000;
    
    
    
    private static final String COMMENT_CLASS = "xt3wquy_comment";
    
    private static final String FUNCTION_CLASS = "xt3wquy_function";
    
    private static final String CHAR_CLASS = "xt3wquy_char";
    
    private static final String LITERAL_CLASS = "xt3wquy_literal";
    
    private static final String RESERVED_CLASS = "xt3wquy_reserved";
    
    private static final String CONST_CLASS = "xt3wquy_const";
    
    private static final String CLASS_CLASS = "xt3wquy_class";
    
    
    
    private static final String COMMENT_REGEX = 
        "([/][*](.|\\n)*?[*][/])|(\\/\\/.*?\\n)";
    
    private static final String FUNCTION_REGEX = 
        "(\\b[_a-z$][\\w$]*)(\\s|\\n)*([(])";
    
    private static final String LITERAL_REGEX = "\"(.|\\n)*?\"";
    
    private static final String CHAR_REGEX = "'.'";
    
    private static final String CONST_REGEX = "\\b([_A-Z][_A-Z0-9]*?)\\b";
    
    private static final String CLASS_REGEX = 
        "\\b[A-Z][\\w$]*?[a-z]+[\\w$]*\\b";
    
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
    
    private static final Pattern COMMENT_PATTERN = 
        Pattern.compile(COMMENT_REGEX);
           
    private static final Pattern FUNCTION_PATTERN = 
        Pattern.compile(FUNCTION_REGEX);

    private static final Pattern LITERAL_PATTERN = 
        Pattern.compile(LITERAL_REGEX);
    
    private static final Pattern CHAR_PATTERN = Pattern.compile(CHAR_REGEX);

    private static final Pattern CONSTANT_PATTERN = 
        Pattern.compile(CONST_REGEX); 
    
     private static final Pattern CLASS_PATTERN = 
        Pattern.compile(CLASS_REGEX); 

    private static final Pattern RESERVED_PATTERN = 
        Pattern.compile(RESERVED_REGEX);

    
    /*
    Aramazena todo o conteudo de um arquivo com codigo fonte java
    */
    private StringBuffer content;
    
    private String editedContent;
       
    /*
    buffer para otimizar processos de leitura e gravacao
    */
    private final int buffer;
   
    /*
    Arquivo que serah lido e arquivo que sera gravado
    */
    private final File inputFile;
    private final File outputFile;
    
    private final HashMap<String, String> map;
      
    private final HashMap<String, String> cMap;
    
    private final BufferedWriter logFile;
    
    private int countLinesReaded;
    
    private int countComments;
    
    /*[00]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    /**
     * Construtor da classe.
     * 
     * @param file O arquivo que deve ser processado
     * @throws java.io.IOException
     */
    public Formatter(final File file) throws IOException
    {
        inputFile = file;
        
        String absoluteFileName = inputFile.getAbsolutePath();
        
        buffer = (int)inputFile.length();
        
        content = new StringBuffer(buffer / 2);
        
        outputFile = new File(absoluteFileName.replace(".jv",".html"));
        
        map = new HashMap<>(INITIAL_HASH_CAPACITY);
         
        cMap = new HashMap<>(INITIAL_HASH_CAPACITY);
        
        logFile = 
            new BufferedWriter
            (
                new FileWriter(new File("src/log.txt"), StandardCharsets.UTF_8), 
                50000
            );
        
        countLinesReaded = 0;
        
        countComments = 0;
        
    }//construtor
    
    /*[01]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    private int getNumberOfDigits(int number)
    {
        int digits = 1;
        while ((number = (number/10)) != 0) digits++;
        return digits;
    }//getNumberOfDigits()
    
    /*[02]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    private void createCMap() throws IOException
    {
        Matcher match = COMMENT_PATTERN.matcher(content);
         
        logFile.write("\nProcessando comentarios... \n\n");
        
        HashSet<String> cSet = new HashSet<>(INITIAL_HASH_CAPACITY);
             
        while (match.find()) cSet.add(match.group()); 
        
        editedContent = content.toString();
        
        for (String comment: cSet)
        {
            String replaceComment = "/*" + countComments++ + "*/";
            
            editedContent = 
                editedContent.replace(comment, replaceComment);
              
            cMap.put
            (
                replaceComment, 
                "<span CLASS=\"" + COMMENT_CLASS + "\">" +
                comment.replace("<", "&lt;").replace(">","&gt;") + 
                "</span>"
            );
            
        }//fim do for
        
        content = new StringBuffer
                      (
                          editedContent.replace("\\\"","\\\uff02").
                          replace("'\"'","'\uff02'").replace("<","&lt;").
                          replace(">","&gt;")
                      ); 
        
     
    }//createCMap()
    
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
        
        String line;
        
        while ((line = htmlFile.readLine()) != null)
        {
            countLinesReaded++;
            content.append(line).append("\n");
        }
        
        htmlFile.close();
        
        createCMap();
       
        
        logFile.write
        (
            "\nExpressoes regulares:\n\n" +
            COMMENT_REGEX  + "\n" + 
            FUNCTION_REGEX  + "\n" + 
            LITERAL_REGEX    + "\n" + 
            CHAR_REGEX      + "\n" +
            CONST_REGEX + "\n" + 
            CLASS_REGEX + "\n" + 
            RESERVED_REGEX  + 
            "\n\n=====CONTEUDO ORIGINAL COM COMENTARIOS SUBSTITUIDOS=====\n\n" +
            content + 
            "\n----------------------------------------------------\n"
        );
  
    }//readFile()
    
    /*[03]---------------------------------------------------------------------
 
    -------------------------------------------------------------------------*/
    private void createMap(final Pattern pattern, final String classe) 
        throws IOException
    {
        Matcher matcher = pattern.matcher(content);
         
        logFile.write("\nPesquisando " + classe + "\n\n");
             
        while (matcher.find())
        { 
            String key;
            
            if (classe.equals(FUNCTION_CLASS))
                key = matcher.group(1);
            else
                key = matcher.group();
            
           
            if (!map.containsKey(key))
            {
                String value = 
                    "<span CLASS=\"" + classe + "\">" + key +"</span>"; 
                
                map.put(key, value);
            
                logFile.write(String.format("%-80s | %s\n", key, value));
            }//if
            
        }//while
      
    }//createMap()
      
    /*[04]---------------------------------------------------------------------
        
    -------------------------------------------------------------------------*/
    /**
     * Grava um novo arquivo. Neste arquivo os emojis sao renderizados.
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
        
               
        createMap(CHAR_PATTERN, CHAR_CLASS);
        createMap(LITERAL_PATTERN, LITERAL_CLASS);
        createMap(RESERVED_PATTERN, RESERVED_CLASS);
        createMap(FUNCTION_PATTERN, FUNCTION_CLASS);
        createMap(CONSTANT_PATTERN, CONST_CLASS);
        createMap(CLASS_PATTERN, CLASS_CLASS);
        
        countLinesReaded = countLinesReaded + 3;
        int padding = getNumberOfDigits(countLinesReaded);
        
        content.append("\n</code></pre>\n<div CLASS=\"linenumber\">\n");
        
        for (int line = 0; line <= countLinesReaded; line++)
            content.append(String.format("%0" + padding + "d</br>", line));
     
        content.append("\n</div></div>");
        
        editedContent = content.toString();
        
        logFile.write("\n[Editando o arquivo...]\n");
        
        for(String old: map.keySet())
        {
            logFile.write("\nEditando " + old);
            
            if (old.matches(LITERAL_REGEX) || old.matches(CHAR_REGEX))
                editedContent = editedContent.replace(old, map.get(old));
            else
                editedContent =
                    editedContent.replaceAll("\\b" + old + "\\b", map.get(old));
            
            logFile.write
            (
                "\n-------------------------------------------------------\n\n"
                + editedContent +
                "\n\n-------------------------------------------------------\n"
            );
  
        }
        
        for (String comment: cMap.keySet())
            editedContent = editedContent.replace(comment, cMap.get(comment));
     
        
        editedContent = 
            "\n<div CLASS=\"javacode\" style=\"width:" +
            (630 + padding * 9) +
            "px;\"><pre><code>\n" +
            editedContent.replace("\\\uff02","\\\"").replace("'\uff02'","'\"'");
        
                
        htmlFile.write(editedContent);
        
        htmlFile.close();
        
        logFile.close();
        
    }//createNewFile()
    
    /*[05]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    public static void main(String[] args) 
    {
        try
        {
            Formatter f = new Formatter(new File("src/teste.jv"));
            f.readFile();
            f.createNewFile();
        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
        
    }//main()
    
}//classe Formatter

