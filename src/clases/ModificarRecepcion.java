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
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author Kaze
 */
public class ModificarRecepcion {
    public static void modificarRecepcion() throws FileNotFoundException, IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook    libro = new HSSFWorkbook(fs);
        HSSFSheet rec = libro.getSheetAt(0);
        int numRecs = rec.getLastRowNum();
        for (int r=1;r<=numRecs;r++){
            if ((int)rec.getRow(r).getCell(0).getNumericCellValue()==SOGECOMA.ID_Rec){
                rec.getRow(r).getCell(1).setCellValue(SOGECOMA.fechaHoraRec);
                rec.getRow(r).getCell(2).setCellValue(SOGECOMA.ID_Material);
                rec.getRow(r).getCell(3).setCellValue(SOGECOMA.cantRec);
                rec.getRow(r).getCell(4).setCellValue(SOGECOMA.proveedorRec);
                rec.getRow(r).getCell(5).setCellValue(SOGECOMA.almaceneroRec);
                rec.getRow(r).getCell(6).setCellValue(SOGECOMA.almacenRec);
                break;
            }
        }
        try {
            FileOutputStream elFichero = new FileOutputStream("SOGECOMA.xls");
            libro.write(elFichero);
            elFichero.close();
            JOptionPane.showMessageDialog(null, "La Recepción se modificó correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se ha podido acceder a la base de datos\nporque está siendo utilizada en este momento.");
        }
    }
}
