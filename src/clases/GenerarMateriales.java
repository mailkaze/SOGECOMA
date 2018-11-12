/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author Kaze
 */
public class GenerarMateriales {
    public void generadorMateriales() throws IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook sogecoma = new HSSFWorkbook(fs);
        HSSFSheet materiales = sogecoma.getSheetAt(3);
        int posicion = 0;
        
        String ruta="";
        JFileChooser fileChooser = new JFileChooser();              
        int result = fileChooser.showOpenDialog(null);  
        if ( result == JFileChooser.APPROVE_OPTION ){            
            ruta = fileChooser.getSelectedFile().getAbsolutePath(); 
        }
        POIFSFileSystem fs1 = new POIFSFileSystem(new FileInputStream(ruta));
        HSSFWorkbook cuadro = new HSSFWorkbook(fs1);
        HSSFSheet hoja1 = cuadro.getSheetAt(0);
        int numRegistrosCuadro = hoja1.getLastRowNum();
        
        for (int i=2;i<=numRegistrosCuadro;i++){
            try{
                if (hoja1.getRow(i).getCell(0).getStringCellValue().equals("")&&
               !hoja1.getRow(i).getCell(1).getStringCellValue().equals("")){
                    Boolean repe=false;
                    String mat=hoja1.getRow(i).getCell(1).getStringCellValue();
                    for (int j=1;j<=materiales.getLastRowNum();j++){
                        if (materiales.getRow(j).getCell(1).getStringCellValue().equals(mat)){
                            repe=true;
                        }
                    }
                    if (repe==false){
                        HSSFRow Fila = materiales.createRow(posicion+1);    
                        HSSFCell CeldaA = Fila.createCell(0);
                        try{
                            int ID = (int) materiales.getRow(posicion).getCell(0).getNumericCellValue()+1;
                            CeldaA.setCellValue(ID);
                        }
                        catch(IllegalStateException n){
                            CeldaA.setCellValue(1);
                        }
                        HSSFCell CeldaB = Fila.createCell(1);
                        CeldaB.setCellValue(hoja1.getRow(i).getCell(1).getStringCellValue());

                        HSSFCell CeldaC = Fila.createCell(2);
                        CeldaC.setCellValue(hoja1.getRow(i).getCell(2).getStringCellValue());

                        HSSFCell CeldaD = Fila.createCell(3);
                        CeldaD.setCellValue(0.0);

                        HSSFCell CeldaE = Fila.createCell(4);
                        CeldaE.setCellValue(0.0);

                        posicion++;
                    }
                }
            }catch(Exception e){}
        }
        //Guardamos el archivo.
        try {
            FileOutputStream elFichero = new FileOutputStream("SOGECOMA.xls");
            sogecoma.write(elFichero);
            elFichero.close();
            JOptionPane.showMessageDialog(null, "La nueva lista de materiales se generó correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se ha podido acceder a la base de datos\nporque está siendo utilizada en este momento.");
        }
    }
}
