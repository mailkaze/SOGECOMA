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
public class GuardarNuevaEntrega {
    public void guardarEntrega() throws FileNotFoundException, IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook sogecoma = new HSSFWorkbook(fs);
        HSSFSheet ents = sogecoma.getSheetAt(1);
        HSSFSheet cons= sogecoma.getSheetAt(2);
        HSSFSheet cua= sogecoma.getSheetAt(6);
        
        int numEnts=ents.getLastRowNum();
        int numCons=cons.getLastRowNum();
        int numCua=cua.getLastRowNum();
        
        boolean itemOK=false, contratistaOK=false;
        
        for (int co=1;co<=numCons;co++){//comprueba si el contratista tiene ese bloque.
            if (cons.getRow(co).getCell(2).getStringCellValue().equals(clases.SOGECOMA.contratistaEnt)&&
                cons.getRow(co).getCell(1).getStringCellValue().equals(clases.SOGECOMA.bloqueEnt)){
                contratistaOK=true;    
            }
        }
        for (int cu=1;cu<=numCua;cu++){//comprueba que el item tiene ese material
            try{ //usamos try para que trague los espacios en blanco entre items en la hoja CUADRO
                if (cua.getRow(cu).getCell(1).getStringCellValue().equals(clases.SOGECOMA.nomItem)){ //Si encuentra el Item actual
                    System.out.println("Encontro el item.");
                    for (int suma=1;suma<=20;suma++){//recorre los materiales de ese item, suponemos un máximo de 20 materiales
                        try{//usamos try para que trague los espacios en blanco entre items en la hoja CUADRO
                            if (cua.getRow(cu+suma).getCell(1).getStringCellValue().equals("CONTRATISTA")||
                                cua.getRow(cu+suma).getCell(1).getStringCellValue().equals("")){
                                break;
                            }else{//encontró un vacío o 'CONTRATISTA', deja de buscar materiales en este item
                                if (cua.getRow(cu+suma).getCell(1).getStringCellValue().equals(clases.SOGECOMA.nomMaterial)){
                                    itemOK=true;
                                    System.out.println("Encontro el material y pone itemOK a true.");
                                    break;
                                }
                            }
                        }catch(Exception e){}
                    } 
                }
            }catch(Exception e){}
        }
        
        if (itemOK){
            if (contratistaOK){//Solo ejecuta el guardado si el item y el contratista son correctos.               
                HSSFRow Fila = ents.createRow(numEnts+1);    
                HSSFCell CeldaA = Fila.createCell(0);
                try{
                    int ID = (int) ents.getRow(numEnts).getCell(0).getNumericCellValue()+1;
                    CeldaA.setCellValue(ID);
                }
                catch(IllegalStateException n){
                    CeldaA.setCellValue(1);
                }
                HSSFCell CeldaB = Fila.createCell(1);
                CeldaB.setCellValue(clases.SOGECOMA.fechaHoraEnt);

                HSSFCell CeldaC = Fila.createCell(2);
                CeldaC.setCellValue(clases.SOGECOMA.ID_Material);

                HSSFCell CeldaD = Fila.createCell(3);
                CeldaD.setCellValue(clases.SOGECOMA.cantEnt);

                HSSFCell CeldaE = Fila.createCell(4);
                CeldaE.setCellValue(clases.SOGECOMA.almaceneroEnt);

                HSSFCell CeldaF = Fila.createCell(5);
                CeldaF.setCellValue(clases.SOGECOMA.contratistaEnt);

                HSSFCell CeldaG = Fila.createCell(6);
                CeldaG.setCellValue(clases.SOGECOMA.bloqueEnt);

                HSSFCell CeldaH = Fila.createCell(7);
                CeldaH.setCellValue(clases.SOGECOMA.ID_Item);

                HSSFCell CeldaI = Fila.createCell(8);
                CeldaI.setCellValue(clases.SOGECOMA.almacenEnt);

                //Guardamos el archivo.
                try {
                    FileOutputStream elFichero = new FileOutputStream("SOGECOMA.xls");
                    sogecoma.write(elFichero);
                    elFichero.close();
//                    clases.SumaRestaStock restaStock=new clases.SumaRestaStock();
//                    restaStock.operaStock(false);
                    JOptionPane.showMessageDialog(null, "La Nueva Entrega se guardó correctamente.");
                    clases.SOGECOMA.cerrarVentana=true;
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "No se ha podido acceder a la base de datos\nporque está siendo utilizada en este momento.");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Este contratísta no trabaja en ese bloque.");
                clases.SOGECOMA.cerrarVentana=false;
            }
        }else{
            JOptionPane.showMessageDialog(null,"Este Ítem no tiene ese material.");
            clases.SOGECOMA.cerrarVentana=false;
        }
        
        
    }
}
