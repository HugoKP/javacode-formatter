package br.com.hkp.javacode_styler.tokens;

import static br.com.hkp.javacode_styler.global.Global.DIF;
import java.util.HashMap;

/******************************************************************************
 * Cada subclasse dessa classe abstrata eh responsavel por coletar no codigo
 * fonte um tipo de elemento que serah estilizado no HTML.
 * 
 * @since 27 de dezembro de 2020 v1.0
 * @version 1.0
 * @author Hugo Kaulino Pereira
 *****************************************************************************/
public abstract class Tokens
{
    /*
    O conteudo do arquivo com o codigo fonte java que serah convertido para HTML
    */
    public static String javaSourceCode;
    
    /*
    A capacidade inicial de um objeto HashMap
    */
    protected static final int HASH_CAPACITY = 2000;
    
    /*
    Um mapa associando os identificadores unicos as tags que estilizam os 
    elementos
    */
    protected static final HashMap<String, String> MAP = 
        new HashMap<>(HASH_CAPACITY);
    
    protected static final String FAKE_SLASH = "\u20e0";
    protected static final String TAG = "<span class=\"" + DIF;
    
    private static int countElements = 0;
    
    /*[01]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    /**
     * Uma implementacao deste metodo deve mapear todas as ocorrencias de um
     * determinado tipo de elemento.
     * 
     * Para isto sera utilizado o HashMap MAP, que deve armazenar pares de 
     * chave/valor onde a chave serah uma string retornada pelo metodo getMark()
     * desta classe, e o valor sera a tag que devera substituir essa chave na 
     * edicao final a ser realizada na String javaSourceCode.
     */
    public abstract void map();
    
    /*[02]---------------------------------------------------------------------
    Retorna um identificador unico para marcar a ocorrencia de um elemento que
    serah estilizado
    -------------------------------------------------------------------------*/
    protected String getMark()
    {
        return (DIF + "[" + countElements++ + "]");
    }//getMark()
    
    /*[03]---------------------------------------------------------------------
    Troca todos os identificadores unicos pelas tags que estilizam os elementos
    -------------------------------------------------------------------------*/
    public static void insertSpanTags()
    {
        for(String mark: MAP.keySet())
            javaSourceCode = javaSourceCode.replace(mark, MAP.get(mark));
    }//insertSpanTags()
  
   
}//classe Tokens
