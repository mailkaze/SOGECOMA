/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class GuardarNuevaRecepcion {
    public void guardarRecepcion() throws FileNotFoundException, IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook sogecoma = new HSSFWorkbook(fs);
        HSSFSheet recs = sogecoma.getSheetAt(0);
        int numRegistros=recs.getLastRowNum();
        
        HSSFRow Fila = recs.createRow(numRegistros+1);    
        HSSFCell CeldaA = Fila.createCell(0);
        try{
            int ID = (int) recs.getRow(numRegistros).getCell(0).getNumericCellValue()+1;
            CeldaA.setCellValue(ID);
        }
        catch(IllegalStateException n){
            CeldaA.setCellValue(1);
        }
        HSSFCell CeldaB = Fila.createCell(1);
        CeldaB.setCellValue(clases.SOGECOMA.fechaHoraRec);

        HSSFCell CeldaC = Fila.createCell(2);
        CeldaC.setCellValue(clases.SOGECOMA.ID_Material);

        HSSFCell CeldaD = Fila.createCell(3);
        CeldaD.setCellValue(clases.SOGECOMA.cantRec);
        
        HSSFCell CeldaE = Fila.createCell(4);
        CeldaE.setCellValue(clases.SOGECOMA.proveedorRec);
        
        HSSFCell CeldaF = Fila.createCell(5);
        CeldaF.setCellValue(clases.SOGECOMA.almaceneroRec);
        
        HSSFCell CeldaG = Fila.createCell(6);
        CeldaG.setCellValue(clases.SOGECOMA.almacenRec);

        //Guardamos el archivo.
        try {
            FileOutputStream elFichero = new FileOutputStream("SOGECOMA.xls");
            sogecoma.write(elFichero);
            elFichero.close();
//            clases.SumaRestaStock sumaStock=new clases.SumaRestaStock();
//            sumaStock.operaStock(true);
            JOptionPane.showMessageDialog(null, "La Nueva Recepción se guardó correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se ha podido acceder a la base de datos\nporque está siendo utilizada en este momento.");
        }
    }
}
