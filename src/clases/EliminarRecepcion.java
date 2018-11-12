/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author Manuel
 */
public class EliminarRecepcion {
    public static void eliminarRecepcion() throws IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook libro = new HSSFWorkbook(fs);
        HSSFSheet rec = libro.getSheetAt(0);
        int NumRecs = rec.getLastRowNum();
        //recorremos todos los registros buscando el seleccionado.
        for (int i=1;i<=NumRecs;i++){
            if (SOGECOMA.ID_Rec == (int)rec.getRow(i).getCell(0).getNumericCellValue()){
                //Cuando lo encontramos sustituimos todas las celdas por las de abajo.
                for (int j=i;j<NumRecs;j++){ //Para ello recorremos la hoja desde el registro seleccionado hasta el final.
                    for (int k=0;k<=6;k++){
                            if (k==0 || k==2){ //Si es Integer.
                                int valor = (int)rec.getRow(j+1).getCell(k).getNumericCellValue();
                                rec.getRow(j).getCell(k).setCellValue(valor);
                            }
                            else if (k==1 || k==4 || k==5 || k==6){//Si es String.
                                String valor = rec.getRow(j+1).getCell(k).getStringCellValue();
                                HSSFRichTextString Cvalor = new HSSFRichTextString(valor);
                                rec.getRow(j).getCell(k).setCellValue(Cvalor);
                            }
                            else if (k==3){//Si es Double.
                                Double valor = (Double)rec.getRow(j+1).getCell(k).getNumericCellValue();
                                rec.getRow(j).getCell(k).setCellValue(valor);
                            }
                        }
                }
                rec.removeRow(rec.getRow(NumRecs));
                break;
            }
        }
        try {
            FileOutputStream elFichero = new FileOutputStream("SOGECOMA.xls");
            libro.write(elFichero);
            elFichero.close();
            JOptionPane.showMessageDialog(null, "La Recepción se ha eliminado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se ha podido acceder a la base de datos\nporque está siendo utilizada en este momento.");
        }
    }
}
