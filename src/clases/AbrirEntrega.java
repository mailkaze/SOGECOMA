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
public class AbrirEntrega {
    public static void abrirEntrega() throws FileNotFoundException, IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook libro = new HSSFWorkbook(fs);
        HSSFSheet ent = libro.getSheetAt(1);
        HSSFSheet mat = libro.getSheetAt(3);
        HSSFSheet ite = libro.getSheetAt(4);
        //Buscamos el item con ese ID en el archivo
        int numEnts = ent.getLastRowNum();
        int numMats = mat.getLastRowNum();
        int numItes = ite.getLastRowNum();
        for (int e=1;e<= numEnts;e++){
            if (SOGECOMA.ID_Ent==(int)ent.getRow(e).getCell(0).getNumericCellValue()){
                //Se guardan los datos en variables globales para pasarlas al JDialog.
                SOGECOMA.fechaHoraEnt=ent.getRow(e).getCell(1).getStringCellValue();
                for (int m=1;m<=numMats;m++){
                    if ((int)ent.getRow(e).getCell(2).getNumericCellValue()==(int)mat.getRow(m).getCell(0).getNumericCellValue()){
                        SOGECOMA.ID_Material=(int)mat.getRow(m).getCell(0).getNumericCellValue();
                        SOGECOMA.nomMaterial=mat.getRow(m).getCell(1).getStringCellValue();
                        SOGECOMA.udMaterial=mat.getRow(m).getCell(2).getStringCellValue();
                    }
                }
                SOGECOMA.cantEnt=(double)ent.getRow(e).getCell(3).getNumericCellValue();
                SOGECOMA.almaceneroEnt=ent.getRow(e).getCell(4).getStringCellValue();
                SOGECOMA.contratistaEnt=ent.getRow(e).getCell(5).getStringCellValue();
                SOGECOMA.bloqueEnt=ent.getRow(e).getCell(6).getStringCellValue();
                for (int i=1;i<=numItes;i++){
                    if ((int)ent.getRow(e).getCell(7).getNumericCellValue()==(int)ite.getRow(i).getCell(0).getNumericCellValue()){
                        SOGECOMA.ID_Item=(int)ite.getRow(i).getCell(0).getNumericCellValue();
                        SOGECOMA.numItem=ite.getRow(i).getCell(1).getStringCellValue();
                        SOGECOMA.nomItem=ite.getRow(i).getCell(2).getStringCellValue();
                    }
                }
                SOGECOMA.almacenEnt=ent.getRow(e).getCell(8).getStringCellValue();
            }
        }
        SOGECOMA.cargarDatos=true;
    }
}
