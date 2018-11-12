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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author Kaze
 */
public class ModificarEntrega {
    public static void modificarEntrega() throws FileNotFoundException, IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook    libro = new HSSFWorkbook(fs);
        HSSFSheet ent = libro.getSheetAt(1);
        int numEnts = ent.getLastRowNum();
        for (int e=1;e<=numEnts;e++){
            if ((int)ent.getRow(e).getCell(0).getNumericCellValue()==SOGECOMA.ID_Ent){
                ent.getRow(e).getCell(1).setCellValue(SOGECOMA.fechaHoraEnt);
                ent.getRow(e).getCell(2).setCellValue(SOGECOMA.ID_Material);
                ent.getRow(e).getCell(3).setCellValue(SOGECOMA.cantEnt);
                ent.getRow(e).getCell(4).setCellValue(SOGECOMA.almaceneroEnt);
                ent.getRow(e).getCell(5).setCellValue(SOGECOMA.contratistaEnt);
                ent.getRow(e).getCell(6).setCellValue(SOGECOMA.bloqueEnt);
                ent.getRow(e).getCell(7).setCellValue(SOGECOMA.ID_Item);
                ent.getRow(e).getCell(8).setCellValue(SOGECOMA.almacenEnt);
                break;
            }
        }
        try {
            FileOutputStream elFichero = new FileOutputStream("SOGECOMA.xls");
            libro.write(elFichero);
            elFichero.close();
            JOptionPane.showMessageDialog(null, "La Entrega se modificó correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se ha podido acceder a la base de datos\nporque está siendo utilizada en este momento.");
        }
    }
}
