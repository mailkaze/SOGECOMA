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
 * @author Kaze
 */
public class SumaRestaStock {
    public void operaStock(Boolean sumaOResta) throws FileNotFoundException, IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook sogecoma = new HSSFWorkbook(fs);
        HSSFSheet materiales = sogecoma.getSheetAt(3);
        int numRegistros=materiales.getLastRowNum();
        double stock=0;
        for (int i=1;i<=numRegistros;i++){
            if ((int)materiales.getRow(i).getCell(0).getNumericCellValue()==clases.SOGECOMA.ID_Material){
                stock=(double)materiales.getRow(i).getCell(3).getNumericCellValue();
                if (sumaOResta==true){ //Si viene de recepciones suma stock
                    stock=stock+clases.SOGECOMA.cantRec;
                }else{ //Si viene de entregas resta stock
                    stock=stock-clases.SOGECOMA.cantEnt;
                }
                materiales.getRow(i).getCell(3).setCellValue(stock);
                break;
            }
        }
        FileOutputStream elFichero = new FileOutputStream("SOGECOMA.xls");
        sogecoma.write(elFichero);
        elFichero.close();
    }
    public double hallaStock(int idMat) throws FileNotFoundException, IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook sogecoma = new HSSFWorkbook(fs);
        HSSFSheet rec=sogecoma.getSheetAt(0);
        HSSFSheet ent=sogecoma.getSheetAt(1);
        HSSFSheet mat = sogecoma.getSheetAt(3);
        int numRecs=rec.getLastRowNum();
        int numEnts=ent.getLastRowNum();
        int numMats=mat.getLastRowNum();
        double stock=0;
        
        for (int r=1;r<=numRecs;r++){
            if ((int)rec.getRow(r).getCell(2).getNumericCellValue()==idMat){
                //Encuentra en recepciones un registro con ese material
                stock+=(double)rec.getRow(r).getCell(3).getNumericCellValue();
                //suma la cantidad al stock
            }
        }
        for (int e=1;e<=numEnts;e++){
            if ((int)ent.getRow(e).getCell(2).getNumericCellValue()==idMat){
                //Encuentra en entregas un registro con ese material
                stock-=(double)ent.getRow(e).getCell(3).getNumericCellValue();
                //resta la cantidad al stock
            }
        }
        return stock;
    }
}

