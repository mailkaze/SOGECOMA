/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author Manuel
 */
public class AsignarMinimo {
    public void asigMin (double min) throws FileNotFoundException, IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook libro = new HSSFWorkbook(fs);
        HSSFSheet mat = libro.getSheetAt(3);
        int numMats = mat.getLastRowNum();
        
        for (int m=1;m<=numMats;m++){
            if ((int)mat.getRow(m).getCell(0).getNumericCellValue()==clases.SOGECOMA.ID_Material){
                mat.getRow(m).getCell(4).setCellValue(min);
                break;
            }
        }
        //Guardamos el archivo.
        try {
            FileOutputStream elFichero = new FileOutputStream("SOGECOMA.xls");
            libro.write(elFichero);
            elFichero.close();
//            clases.SumaRestaStock restaStock=new clases.SumaRestaStock();
//            restaStock.operaStock(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
