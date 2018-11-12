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
public class EliminarEntrega {
    public static void eliminarEntrega() throws IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook libro = new HSSFWorkbook(fs);
        HSSFSheet ent = libro.getSheetAt(1);
        int NumEnts = ent.getLastRowNum();
        //recorremos todos los registros buscando el seleccionado.
        for (int i=1;i<=NumEnts;i++){
            if (SOGECOMA.ID_Ent == (int)ent.getRow(i).getCell(0).getNumericCellValue()){
                //Cuando lo encontramos sustituimos todas las celdas por las de abajo.
                for (int j=i;j<NumEnts;j++){ //Para ello recorremos la hoja desde el registro seleccionado hasta el final.
                    for (int k=0;k<=8;k++){
                            if (k==0 || k==2 || k==7){ //Si es Integer.
                                int valor = (int)ent.getRow(j+1).getCell(k).getNumericCellValue();
                                ent.getRow(j).getCell(k).setCellValue(valor);
                            }
                            else if (k==1 || k==4 || k==5 || k==6 || k==8){//Si es String.
                                String valor = ent.getRow(j+1).getCell(k).getStringCellValue();
                                HSSFRichTextString Cvalor = new HSSFRichTextString(valor);
                                ent.getRow(j).getCell(k).setCellValue(Cvalor);
                            }
                            else if (k==3){//Si es Double.
                                Double valor = (Double)ent.getRow(j+1).getCell(k).getNumericCellValue();
                                ent.getRow(j).getCell(k).setCellValue(valor);
                            }
                        }
                }
                ent.removeRow(ent.getRow(NumEnts));
                break;
            }
        }
        try {
            FileOutputStream elFichero = new FileOutputStream("SOGECOMA.xls");
            libro.write(elFichero);
            elFichero.close();
            JOptionPane.showMessageDialog(null, "La Entrega se ha eliminado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se ha podido acceder a la base de datos\nporque está siendo utilizada en este momento.");
        }
    }
}
