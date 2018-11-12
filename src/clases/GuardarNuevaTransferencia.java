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
public class GuardarNuevaTransferencia {
    public void guardarTransferencia() throws FileNotFoundException, IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook sogecoma = new HSSFWorkbook(fs);
        HSSFSheet trans = sogecoma.getSheetAt(7);
        
        int numTrans=trans.getLastRowNum();
         
        HSSFRow Fila = trans.createRow(numTrans+1);    
        HSSFCell CeldaA = Fila.createCell(0);
        try{
            int ID = (int) trans.getRow(numTrans).getCell(0).getNumericCellValue()+1;
            CeldaA.setCellValue(ID);
        }
        catch(IllegalStateException n){
            CeldaA.setCellValue(1);
        }
        HSSFCell CeldaB = Fila.createCell(1);
        CeldaB.setCellValue(clases.SOGECOMA.fechaHoraTrans);

        HSSFCell CeldaC = Fila.createCell(2);
        CeldaC.setCellValue(clases.SOGECOMA.ID_Material);

        HSSFCell CeldaD = Fila.createCell(3);
        CeldaD.setCellValue(clases.SOGECOMA.cantTrans);

        HSSFCell CeldaE = Fila.createCell(4);
        CeldaE.setCellValue(clases.SOGECOMA.ID_ItemOrigen);

        HSSFCell CeldaF = Fila.createCell(5);
        CeldaF.setCellValue(clases.SOGECOMA.bloqueOrigen);

        HSSFCell CeldaG = Fila.createCell(6);
        CeldaG.setCellValue(clases.SOGECOMA.ID_ItemDestino);

        HSSFCell CeldaH = Fila.createCell(7);
        CeldaH.setCellValue(clases.SOGECOMA.bloqueDestino);

        //Guardamos el archivo.
        try {
            FileOutputStream elFichero = new FileOutputStream("SOGECOMA.xls");
            sogecoma.write(elFichero);
            elFichero.close();
//                    clases.SumaRestaStock restaStock=new clases.SumaRestaStock();
//                    restaStock.operaStock(false);
            JOptionPane.showMessageDialog(null, "La Transferencia se realizó correctamente.");
            clases.SOGECOMA.cerrarVentana=true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se ha podido acceder a la base de datos\nporque está siendo utilizada en este momento.");
        }
    }
}
