/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author Kaze
 */
public class AbrirRecepcion {
    public static void abrirRecepcion() throws FileNotFoundException, IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook libro = new HSSFWorkbook(fs);
        HSSFSheet rec = libro.getSheetAt(0);
        HSSFSheet mat = libro.getSheetAt(3);
        //Buscamos el item con ese ID en el archivo
        int numRecs = rec.getLastRowNum();
        int numMats = mat.getLastRowNum();
        for (int r=1;r<= numRecs;r++){
            if (SOGECOMA.ID_Rec==(int)rec.getRow(r).getCell(0).getNumericCellValue()){
                //Se guardan los datos en variables globales para pasarlas al JDialog.
                SOGECOMA.fechaHoraRec=rec.getRow(r).getCell(1).getStringCellValue();
                for (int m=1;m<=numMats;m++){
                    if ((int)rec.getRow(r).getCell(2).getNumericCellValue()==(int)mat.getRow(m).getCell(0).getNumericCellValue()){
                        SOGECOMA.ID_Material=(int)mat.getRow(m).getCell(0).getNumericCellValue();
                        SOGECOMA.nomMaterial=mat.getRow(m).getCell(1).getStringCellValue();
                        SOGECOMA.udMaterial=mat.getRow(m).getCell(2).getStringCellValue();
                    }
                }
                SOGECOMA.cantRec=(double)rec.getRow(r).getCell(3).getNumericCellValue();
                SOGECOMA.proveedorRec=rec.getRow(r).getCell(4).getStringCellValue();
                SOGECOMA.almaceneroRec=rec.getRow(r).getCell(5).getStringCellValue();
                SOGECOMA.almacenRec=rec.getRow(r).getCell(6).getStringCellValue();
            }
        }
        SOGECOMA.cargarDatos=true;
    }
}
