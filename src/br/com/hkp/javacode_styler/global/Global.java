package br.com.hkp.javacode_styler.global;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

/******************************************************************************
 * A classe fornece metodos estaticos de inicializacao, navegacao no sistema de
 * arquivo e leitura e gravacao de arquivos.
 * 
 * @since 27 de dezembro de 2020 v1.0
 * @version 1.0
 * @author Hugo Kaulino Pereira
 *****************************************************************************/
public final class Global
{
    /*[01]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    public static void fileChooserSettings()
    {
        UIManager.put("FileChooser.lookInLabelText", "Selecionar"); 
        UIManager.put("FileChooser.openButtonText", "Abrir"); 
        UIManager.put("FileChooser.cancelButtonText", "Cancelar");
        UIManager.put("FileChooser.fileNameLabelText", "Nome do Arquivo"); 
        UIManager.put("FileChooser.filesOfTypeLabelText", "Tipo de Arquivo"); 
        UIManager.put("FileChooser.folderNameLabelText", "Selecionado"); 
        UIManager.put
        (
            "FileChooser.openButtonToolTipText", "Abrir o Arquivo Selecionado"
        ); 
        UIManager.put("FileChooser.cancelButtonToolTipText","Cancelar"); 
        UIManager.put("FileChooser.fileNameHeaderText","Nome"); 
        UIManager.put("FileChooser.upFolderToolTipText", "Acima");
        UIManager.put
        (
            "FileChooser.homeFolderToolTipText",
            "\u00c1rea de Trabalho"
        ); 
        UIManager.put("FileChooser.newFolderToolTipText","Nova Pasta");
        UIManager.put("FileChooser.listViewButtonToolTipText","Lista"); 
        UIManager.put("FileChooser.newFolderButtonText","Nova Pasta"); 
        UIManager.put("FileChooser.renameFileButtonText", "Renomear");
        UIManager.put("FileChooser.deleteFileButtonText", "Eliminar");
        UIManager.put("FileChooser.filterLabelText", "Tipo");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Detalhes");
        UIManager.put("FileChooser.fileSizeHeaderText","Tamanho"); 
        UIManager.put
        (
            "FileChooser.fileDateHeaderText", 
            "Data de Altera\u00e7\u00e3o."
        );
      
    }//FileChooserSettings()
    
    /*[02]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    public static File choose
    (
        final String title,
        final FileNameExtensionFilter filter, 
        final boolean chooseDir
    )
    {
        UIManager.put("FileChooser.openDialogTitleText", title);
        
        JFileChooser fc = new JFileChooser();
        
        fc.setFileFilter(filter);
        
        if (chooseDir)
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        else
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int res = fc.showOpenDialog(null);

        if(res == JFileChooser.APPROVE_OPTION)
            return fc.getSelectedFile();
        else
            return null;
        
    }//choose()
    
    /*[03]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
    public static String readTextFile(final File file) throws IOException
    {
        return 
            new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
       
    }//readTextFile()
    
    /*[04]---------------------------------------------------------------------
    
    -------------------------------------------------------------------------*/
   @SuppressWarnings("ConvertToTryWithResources")
   public static void writeTextFile(final File file, final String content)
        throws IOException
    {
        FileWriter fw = new FileWriter(file, StandardCharsets.UTF_8);
               
        fw.write(content);
        
        fw.close();
  
    }//writeTextFile()
    
}//classe Global
