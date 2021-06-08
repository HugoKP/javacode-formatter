package br.com.hkp.javadoc;

import static br.com.hkp.javacode_styler.global.Global.readTextFile;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author userhugo
 */
public class Javadoc {
    
    //Comentario de linha iniciando com 3 barras (///)
    private static final String TAG = "\\/{3}.*\\n";
    
    private static final Pattern ATRIB_REGEXP = 
       Pattern.compile(TAG + "([^(]+?(=(.|\\n)+?)?;)"); 
    
    private static final Pattern METHOD_REGEXP =
        Pattern.compile(TAG + "([^;=]+?\\))");
    
    private static final Pattern PACKAGE_REGEXP =
        Pattern.compile("package [a-z_.]+;");
    
    private static final Pattern CLASS_REGEXP =
        Pattern.compile("(class [A-Z][^\"\\*]+?)\\{");
  
  
    public static void main(String[] args) throws IOException {
        
        
              
        String s = readTextFile(new File("Styler.java"));
        
        Matcher m = ATRIB_REGEXP.matcher(s);
        
        System.out.println("Localizando campos...\n");
        
        while (m.find()) {
             System.out.println(m.group(1) + "\n");
        }
        
        
        m = METHOD_REGEXP.matcher(s);
        
        System.out.println("\nLocalizando metodos...\n");
        
        while (m.find()) System.out.println(m.group(1) + "\n");
        
        m = PACKAGE_REGEXP.matcher(s);
        
        System.out.println("\nLocalizando package...\n");
        
        while (m.find()) System.out.println(m.group() + "\n");
        
        m = CLASS_REGEXP.matcher(s);
        
        System.out.println("\nLocalizando class...\n");
        
        while (m.find()) System.out.println(m.group(1) + "\n");
        
    }
    
    
}
