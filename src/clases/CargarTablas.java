/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JProgressBar;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author Kaze
 */
public class CargarTablas {
    
    public void cargarTablaCompras() throws FileNotFoundException, IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook libro = new HSSFWorkbook(fs);
        HSSFSheet rec = libro.getSheetAt(0);
        HSSFSheet mat = libro.getSheetAt(3);
        HSSFSheet cua = libro.getSheetAt(6);
        int numRecs = rec.getLastRowNum();
        int numMats = mat.getLastRowNum();
        int numCuas = cua.getLastRowNum();
        
        clases.SOGECOMA.ModeloTablaCompras.addColumn("Material");
        clases.SOGECOMA.ModeloTablaCompras.addColumn("Unidad");
        clases.SOGECOMA.ModeloTablaCompras.addColumn("Cantidad Comprada");
        clases.SOGECOMA.ModeloTablaCompras.addColumn("Total a comprar");
        clases.SOGECOMA.ModeloTablaCompras.addColumn("Restante");
        clases.SOGECOMA.ModeloTablaCompras.addColumn("Progreso");
        
        Object Datos[]=new Object[6]; //Numero de columnas de la tabla, es un Array de objetos.
        
        clases.Redondear redondear =new clases.Redondear();
        double sumaCant=0;
        
        for (int m=1;m<=numMats;m++){//recorremos lista de materiales recogiendo sus nombres y Uds
            Datos[0]=mat.getRow(m).getCell(1).getStringCellValue();
            Datos[1]=mat.getRow(m).getCell(2).getStringCellValue();
            for (int r=1;r<=numRecs;r++){//Nos vamos a recepciones para sumar la cant de cada compra del material actual
             if (rec.getRow(r).getCell(2).getNumericCellValue()==mat.getRow(m).getCell(0).getNumericCellValue()){
                 sumaCant+=(double)rec.getRow(r).getCell(3).getNumericCellValue();
             }
            }
            Datos[2]=redondear.redondearDouble(sumaCant);//Aquí sumaCant es el total de lo comprado
            sumaCant=0;//Reseteamos la variable para reutilizarla a continuación.
            for (int c=1;c<=numCuas;c++){//nos vamos a CUADRO para sumar cuanto hace falta en total para el proyecto
                try{//Lo metemos en un try para que se trague las celdas vacías sin error
                    if (cua.getRow(c).getCell(1).getStringCellValue().equals(Datos[0])){
                        sumaCant+=(double)cua.getRow(c).getCell(4).getNumericCellValue();
                        //Recoge de la columna que multiplica la cant de material x cant de bloques
                    }
                }catch (Exception e){}
            }
            Datos[3]=redondear.redondearDouble(sumaCant);//Aquí sumaCant es el total de lo necesario por todos los bloques.
            sumaCant=0;//Reseteamos sumaCant para reutilizarlo en la siguiente vuelta del FOR
            Datos[4]=redondear.redondearDouble((double)Datos[3]-(double)Datos[2]);
            Double porcentaje = redondear.redondearDouble((Double.parseDouble(Datos[2].toString())*100)/Double.parseDouble(Datos[3].toString()));
            Datos[5]=porcentaje+"%";
            clases.SOGECOMA.ModeloTablaCompras.addRow(Datos); //Añadimos una fila con el Array Datos.
        }
    }
    public void cargarTablaItems(String bloque) throws FileNotFoundException, IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook libro = new HSSFWorkbook(fs);
        HSSFSheet ent = libro.getSheetAt(1);
        HSSFSheet mat = libro.getSheetAt(3);
        HSSFSheet ite = libro.getSheetAt(4);
        HSSFSheet cua = libro.getSheetAt(6);
        int numEnts = ent.getLastRowNum();
        int numMats = mat.getLastRowNum();
        int numItes = ite.getLastRowNum();
        int numCuas = cua.getLastRowNum();
        
        clases.SOGECOMA.ModeloTablaTodosItems.addColumn("Ítem");
        clases.SOGECOMA.ModeloTablaTodosItems.addColumn("Nombre del Ítem");
        clases.SOGECOMA.ModeloTablaTodosItems.addColumn("Material");
        clases.SOGECOMA.ModeloTablaTodosItems.addColumn("Unidad");
        clases.SOGECOMA.ModeloTablaTodosItems.addColumn("Entregado");
        clases.SOGECOMA.ModeloTablaTodosItems.addColumn("Total a Entregar");
        clases.SOGECOMA.ModeloTablaTodosItems.addColumn("Falta por Entregar");
        clases.SOGECOMA.ModeloTablaTodosItems.addColumn("Progreso");

        Object Datos[]=new Object[8]; //Numero de columnas de la tabla, es un Array de objetos.
        
        Boolean seguir=true;
        clases.Redondear redondear =new clases.Redondear();
        double sumaCant=0;
        JProgressBar barra=new JProgressBar();
            
        for (int i=1;i<=numItes;i++){//recorre hoja Items
            for (int d=0;d<=Datos.length-1;d++){
                Datos[d]=""; //Reseteamos Datos[] para evitar datos residuales
            }
            Datos[0]=ite.getRow(i).getCell(1).getStringCellValue();//Almacena en el array el numero de item
            Datos[1]=ite.getRow(i).getCell(2).getStringCellValue();//Almacena en el array el nombre del item
            clases.SOGECOMA.ModeloTablaTodosItems.addRow(Datos); //Añadimos una fila con el Array Datos.
            Datos[0]="";//para no repetir el numero y nombre de item en cada fila
            Datos[1]="";
            for (int c=1;c<=numCuas;c++){//recorre hoja CUADRO
                try{ //usamos try para que trague los espacios en blanco entre items en la hoja CUADRO
                    if (cua.getRow(c).getCell(0).getStringCellValue().equals(ite.getRow(i).getCell(1).getStringCellValue())){ //Si encuentra el Item actual
                        while (seguir){
                            for (int suma=1;suma<=20;suma++){//recorre los materiales de ese item, suponemos un máximo de 20 materiales
                                try{//usamos try para que trague los espacios en blanco entre items en la hoja CUADRO
                                    if (cua.getRow(c+suma).getCell(1).getStringCellValue().equals("CONTRATISTA")||
                                        cua.getRow(c+suma).getCell(1).getStringCellValue().equals("")){
                                        seguir=false;
                                        break;
                                    }else{//encontró un vacío o 'CONTRATISTA', deja de buscar materiales en este item
                                        //Si el campo del nombre del material no está vacío ni es 'CONTRATISTA'
                                        Datos[2]=cua.getRow(c+suma).getCell(1).getStringCellValue();//Almacena en el array el nombre del material
                                        Datos[3]=cua.getRow(c+suma).getCell(2).getStringCellValue();//Almacena en el array la Ud. del material
                                        Datos[5]=redondear.redondearDouble((double)cua.getRow(c+suma).getCell(3).getNumericCellValue());//Almacena en el array el total a entregar
                                        for (int m=1;m<=numMats;m++){//nos vamos a la hoja materiales a recoger el ID del material
                                            if (mat.getRow(m).getCell(1).getStringCellValue().equals(Datos[2])){
                                                clases.SOGECOMA.ID_Material=(int)mat.getRow(m).getCell(0).getNumericCellValue();
                                            }
                                        }
                                        for (int e=1;e<=numEnts;e++){//ahora buscamos el material en entregas y sumamos todas las entregas
                                            if((int)ent.getRow(e).getCell(2).getNumericCellValue()==clases.SOGECOMA.ID_Material &&
                                               (int)ent.getRow(e).getCell(7).getNumericCellValue()==(int)ite.getRow(i).getCell(0).getNumericCellValue() && 
                                               ent.getRow(e).getCell(6).getStringCellValue().equals(bloque)){
                                                sumaCant+=(double)ent.getRow(e).getCell(3).getNumericCellValue();
                                            }
                                        }
                                        double test=sumaCant;
                                        sumaCant+=transferir(clases.SOGECOMA.ID_Material,(int)ite.getRow(i).getCell(0).getNumericCellValue(),bloque);
                                        if (test!=sumaCant){
                                            System.out.println("La cantidad sin transferencias es "+test);
                                            System.out.println("La cantidad contando las transferencias es "+sumaCant);
                                        }
                                        Datos[4]=redondear.redondearDouble(sumaCant);
                                        Datos[6]=redondear.redondearDouble((double)Datos[5]-(double)Datos[4]);
                                        sumaCant=0;
//                                        barra.setMaximum((int)Datos[5]);
//                                        barra.setValue((int)Datos[4]);
//                                        
//                                        Datos[7]=barra;
                                        Double porcentaje = redondear.redondearDouble((Double.parseDouble(Datos[4].toString())*100)/Double.parseDouble(Datos[5].toString()));
                                        Datos[7]=porcentaje+"%";
                                        clases.SOGECOMA.ModeloTablaTodosItems.addRow(Datos); //Añadimos una fila con el Array Datos.
                                    }
                                }catch(Exception e){}    
                            }
                        }
                        seguir=true;
                        //break;
                    }
                }catch (Exception e){}
            }
            
        }
    }
//Anulado buscar totales por bloque, por innecesario. Ahora 'Buscar por Bloque enlaza a cargarTablaItems
    public void cargarTablaBuscarBloque(String bloque) throws FileNotFoundException, IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook libro = new HSSFWorkbook(fs);
        HSSFSheet ent = libro.getSheetAt(1);
        HSSFSheet mat = libro.getSheetAt(3);
        HSSFSheet cua = libro.getSheetAt(6);
        int numEnts = ent.getLastRowNum();
        int numMats = mat.getLastRowNum();
        int numCuas = cua.getLastRowNum();
        
        clases.SOGECOMA.ModeloTablaBuscarBloque.addColumn("Material");
        clases.SOGECOMA.ModeloTablaBuscarBloque.addColumn("Unidad");
        clases.SOGECOMA.ModeloTablaBuscarBloque.addColumn("Entregado");
        clases.SOGECOMA.ModeloTablaBuscarBloque.addColumn("Total a Entregar");
        clases.SOGECOMA.ModeloTablaBuscarBloque.addColumn("Falta por entregar");
        clases.SOGECOMA.ModeloTablaBuscarBloque.addColumn("Progreso");

        Object Datos[]=new Object[8]; //Numero de columnas de la tabla, es un Array de objetos.
        
        clases.Redondear redondear =new clases.Redondear();
        double sumaCant=0;
        
        for (int m=1;m<=numMats;m++){//recorremos lista de materiales recogiendo sus nombres y Uds
            Datos[0]=mat.getRow(m).getCell(1).getStringCellValue();
            Datos[1]=mat.getRow(m).getCell(2).getStringCellValue();
            for (int e=1;e<=numEnts;e++){//Nos vamos a entregas para sumar la cant de cada compra del material actual
             if (ent.getRow(e).getCell(2).getNumericCellValue()==mat.getRow(m).getCell(0).getNumericCellValue()&&
                     ent.getRow(e).getCell(6).getStringCellValue().equals(bloque)){
                 sumaCant+=(double)ent.getRow(e).getCell(3).getNumericCellValue();
                 
             }
            }
//            sumaCant+=transferir(,,bloque);
            Datos[2]=redondear.redondearDouble(sumaCant);//Aquí sumaCant es el total de lo comprado
            sumaCant=0;//Reseteamos la variable para reutilizarla a continuación.
            for (int c=1;c<=numCuas;c++){//nos vamos a CUADRO para sumar cuanto hace falta en total para el proyecto
                try{//Lo metemos en un try para que se trague las celdas vacías sin error
                    if (cua.getRow(c).getCell(1).getStringCellValue().equals(Datos[0])){
                        sumaCant+=(double)cua.getRow(c).getCell(3).getNumericCellValue();
                        //Recoge de la columna cant de material
                    }
                }catch (Exception e){}
            }
            Datos[3]=redondear.redondearDouble(sumaCant);//Aquí sumaCant es el total de lo necesario por todos los bloques.
            sumaCant=0;//Reseteamos sumaCant para reutilizarlo en la siguiente vuelta del FOR
            Datos[4]=redondear.redondearDouble((double)Datos[3]-(double)Datos[2]);
            Double porcentaje = redondear.redondearDouble((Double.parseDouble(Datos[2].toString())*100)/Double.parseDouble(Datos[3].toString()));
            Datos[5]=porcentaje+"%";
            clases.SOGECOMA.ModeloTablaBuscarBloque.addRow(Datos); //Añadimos una fila con el Array Datos.
        }
    }   
    public void cargarTablaBuscarItem(String bloque,String item) throws FileNotFoundException, IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook libro = new HSSFWorkbook(fs);
        HSSFSheet ent = libro.getSheetAt(1);
        HSSFSheet mat = libro.getSheetAt(3);
        HSSFSheet cua = libro.getSheetAt(6);
        int numEnts = ent.getLastRowNum();
        int numMats = mat.getLastRowNum();
        int numCuas = cua.getLastRowNum();
        
        clases.SOGECOMA.ModeloTablaBuscarItem.addColumn("Material");
        clases.SOGECOMA.ModeloTablaBuscarItem.addColumn("Unidad");
        clases.SOGECOMA.ModeloTablaBuscarItem.addColumn("Entregado");
        clases.SOGECOMA.ModeloTablaBuscarItem.addColumn("Total a Entregar");
        clases.SOGECOMA.ModeloTablaBuscarItem.addColumn("Falta por entregar");
        clases.SOGECOMA.ModeloTablaBuscarItem.addColumn("Progreso");

        Object Datos[]=new Object[8]; //Numero de columnas de la tabla, es un Array de objetos.
        clases.Redondear redondear =new clases.Redondear();
        double sumaCant=0;
        int idMaterial=0;
        for (int c=1;c<=numCuas;c++){//recorre hoja CUADRO
            try{ //usamos try para que trague los espacios en blanco entre items en la hoja CUADRO
                if (cua.getRow(c).getCell(0).getStringCellValue().equals(item)){ //Si encuentra el Item actual
                    for (int suma=1;suma<=20;suma++){//recorre los materiales de ese item, suponemos un máximo de 20 materiales
                        try{//usamos try para que trague los espacios en blanco entre items en la hoja CUADRO
                            if (cua.getRow(c+suma).getCell(1).getStringCellValue().equals("CONTRATISTA")||
                                cua.getRow(c+suma).getCell(1).getStringCellValue().equals("")){
                                break;
                            }else{//encontró un vacío o 'CONTRATISTA', deja de buscar materiales en este item
                                //Si el campo del nombre del material no está vacío ni es 'CONTRATISTA'
                                Datos[0]=cua.getRow(c+suma).getCell(1).getStringCellValue();
                                Datos[1]=cua.getRow(c+suma).getCell(2).getStringCellValue();
                                try{//usamos try para que trague los espacios en blanco entre cants en la hoja CUADRO
                                    Datos[3]=redondear.redondearDouble((double)cua.getRow(c+suma).getCell(3).getNumericCellValue());
                                    //Recoge de la columna cant de material
                                }catch(Exception e){}
                                for (int m=1;m<=numMats;m++){
                                    if (mat.getRow(m).getCell(1).getStringCellValue().equals(Datos[0])){
                                        idMaterial=(int)mat.getRow(m).getCell(0).getNumericCellValue();
                                    }
                                }
                                for (int e=1;e<=numEnts;e++){//Nos vamos a entregas para sumar la cant de cada entrega del material actual
                                 if ((int)ent.getRow(e).getCell(2).getNumericCellValue()==idMaterial&&
                                         ent.getRow(e).getCell(6).getStringCellValue().equals(bloque)&&
                                         (int)ent.getRow(e).getCell(7).getNumericCellValue()==clases.SOGECOMA.ID_Item){
                                     sumaCant+=(double)ent.getRow(e).getCell(3).getNumericCellValue();
                                 }
                                }
                                double test=sumaCant;
                                sumaCant+=transferir(idMaterial,clases.SOGECOMA.ID_Item,bloque);
                                if (test!=sumaCant){
                                    System.out.println("La cantidad sin transferencias es "+test);
                                    System.out.println("La cantidad contando las transferencias es "+sumaCant);
                                }
                                
                                Datos[2]=redondear.redondearDouble(sumaCant);//Aquí sumaCant es el total de lo entregado                                
                                sumaCant=0;//Reseteamos sumaCant para reutilizarlo en la siguiente vuelta del FOR
                                Datos[4]=redondear.redondearDouble((double)Datos[3]-(double)Datos[2]);
                                Double porcentaje = redondear.redondearDouble((Double.parseDouble(Datos[2].toString())*100)/Double.parseDouble(Datos[3].toString()));
                                Datos[5]=porcentaje+"%";
                                clases.SOGECOMA.ModeloTablaBuscarItem.addRow(Datos); //Añadimos una fila con el Array Datos.
                            }
                        }catch(Exception e){}
                    } 
                }
            }catch(Exception e){}
        }
    } 
    public void cargarTablaBuscarMaterial() throws FileNotFoundException, IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook libro = new HSSFWorkbook(fs);
        HSSFSheet ent = libro.getSheetAt(1);
        HSSFSheet mat = libro.getSheetAt(3);
        HSSFSheet ite = libro.getSheetAt(4);
        HSSFSheet trans = libro.getSheetAt(7);
        int numTrans = trans.getLastRowNum();
        int numEnts = ent.getLastRowNum();
        int numMats = mat.getLastRowNum();
        int numItes = ite.getLastRowNum();
        
        clases.SOGECOMA.ModeloTablaBuscarMaterial.addColumn("Bloque");
        clases.SOGECOMA.ModeloTablaBuscarMaterial.addColumn("Ítem");
        clases.SOGECOMA.ModeloTablaBuscarMaterial.addColumn("Nombre del Ítem");
        clases.SOGECOMA.ModeloTablaBuscarMaterial.addColumn("Material");
        clases.SOGECOMA.ModeloTablaBuscarMaterial.addColumn("Unidad");
        clases.SOGECOMA.ModeloTablaBuscarMaterial.addColumn("Entregado");
        clases.SOGECOMA.ModeloTablaBuscarMaterial.addColumn("Total a Entregar");
        clases.SOGECOMA.ModeloTablaBuscarMaterial.addColumn("Falta por Entregar");
        clases.SOGECOMA.ModeloTablaBuscarMaterial.addColumn("Progreso");
        
        
        String bloques[]=new String[35]; //Cargamos un array con los bloques
        bloques[0]="A";
        bloques[1]="B";
        bloques[2]="C";
        bloques[3]="D";
        bloques[4]="E";
        bloques[5]="F";
        bloques[6]="G";
        bloques[7]="H";
        bloques[8]="I";
        bloques[9]="J";
        bloques[10]="K";
        bloques[11]="L";
        bloques[12]="M";
        bloques[13]="N";
        bloques[14]="Ñ";
        bloques[15]="O";
        bloques[16]="P";
        bloques[17]="Q";
        bloques[18]="R";
        bloques[19]="S";
        bloques[20]="T";
        bloques[21]="U";
        bloques[22]="V";
        bloques[23]="W";
        bloques[24]="X";
        bloques[25]="Y";
        bloques[26]="Z";
        bloques[27]="AA";
        bloques[28]="BB";
        bloques[29]="CC";
        bloques[30]="DD";
        bloques[31]="EE";
        bloques[32]="FF";
        bloques[33]="GG";
        bloques[34]="HH";
        Object Datos[]=new Object[9];
        double suma=0;
        clases.Redondear redondear=new clases.Redondear();
        boolean encontrado=false;
        for (int b=0;b<=bloques.length-1;b++){//recorremos los nombres de los bloques por orden
            for (int i=1;i<=numItes;i++){//recorremos los items por orden
                for (int e=1;e<=numEnts;e++){//recorremos las entregas
                    //debemos comprobar si la entrega coincide con el bloque actual, el item actual y el material buscado:
                    if (clases.SOGECOMA.ID_Material==(int)ent.getRow(e).getCell(2).getNumericCellValue()&&
                        ent.getRow(e).getCell(6).getStringCellValue().equals(bloques[b])&&
                        (int)ent.getRow(e).getCell(7).getNumericCellValue()==(int)ite.getRow(i).getCell(0).getNumericCellValue()){
                            //acumulamos todas las cantidades de ese material a ese item:
                            suma+=(double)ent.getRow(e).getCell(3).getNumericCellValue();
                            encontrado=true;
                    }
                }
                if (encontrado){
                    Datos[0]=bloques[b];
                    clases.SOGECOMA.ModeloTablaBuscarMaterial.addRow(Datos);
                    Datos[0]="";
                    Datos[1]=ite.getRow(i).getCell(1).getStringCellValue();
                    clases.SOGECOMA.numItem=(String)Datos[1];
                    Datos[2]=ite.getRow(i).getCell(2).getStringCellValue();
                    clases.SOGECOMA.ModeloTablaBuscarMaterial.addRow(Datos);
                    Datos[1]="";
                    Datos[2]="";
                    Datos[3]=clases.SOGECOMA.nomMaterial;
                    Datos[4]=clases.SOGECOMA.udMaterial;
                    
                    double test=suma;
                    suma+=transferir(clases.SOGECOMA.ID_Material,(int)ite.getRow(i).getCell(0).getNumericCellValue(),bloques[b]);        
                    if (test!=suma){
                        System.out.println("La cantidad sin transferencias es "+test);
                        System.out.println("La cantidad contando las transferencias es "+suma);
                    }
                    Datos[5]=redondear.redondearDouble(suma);
                    Datos[6]=consultarCuadro(clases.SOGECOMA.numItem,clases.SOGECOMA.nomMaterial);
                    suma=0;//reseteamos suma para usarlo en el siguiente item.
                    Datos[7]=redondear.redondearDouble((double)Datos[6]-(double)Datos[5]);
                    Double porcentaje = redondear.redondearDouble((Double.parseDouble(Datos[5].toString())*100)/Double.parseDouble(Datos[6].toString()));
                    Datos[8]=porcentaje+"%";
                    clases.SOGECOMA.ModeloTablaBuscarMaterial.addRow(Datos);
                    Datos[3]="";
                    Datos[4]="";
                    Datos[5]="";
                    Datos[6]="";
                    Datos[7]="";
                    encontrado=false;
                }
            }
        }
    }
    public void cargarTablaComparativaConsumo(String item) throws FileNotFoundException, IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook libro = new HSSFWorkbook(fs);
        HSSFSheet ent = libro.getSheetAt(1);
        HSSFSheet mat = libro.getSheetAt(3);
        HSSFSheet cua = libro.getSheetAt(6);
        int numEnts = ent.getLastRowNum();
        int numMats = mat.getLastRowNum();
        int numCuas = cua.getLastRowNum();
        
        clases.SOGECOMA.ModeloTablaComparativaConsumo.addColumn("Bloque");
        clases.SOGECOMA.ModeloTablaComparativaConsumo.addColumn("Material");
        clases.SOGECOMA.ModeloTablaComparativaConsumo.addColumn("Unidad");
        clases.SOGECOMA.ModeloTablaComparativaConsumo.addColumn("Entregado");
        clases.SOGECOMA.ModeloTablaComparativaConsumo.addColumn("Total a Entregar");
        clases.SOGECOMA.ModeloTablaComparativaConsumo.addColumn("Falta por entregar");
        clases.SOGECOMA.ModeloTablaComparativaConsumo.addColumn("Progreso");

        String bloques[]=new String[35]; //Cargamos un array con los bloques
        bloques[0]="A";
        bloques[1]="B";
        bloques[2]="C";
        bloques[3]="D";
        bloques[4]="E";
        bloques[5]="F";
        bloques[6]="G";
        bloques[7]="H";
        bloques[8]="I";
        bloques[9]="J";
        bloques[10]="K";
        bloques[11]="L";
        bloques[12]="M";
        bloques[13]="N";
        bloques[14]="Ñ";
        bloques[15]="O";
        bloques[16]="P";
        bloques[17]="Q";
        bloques[18]="R";
        bloques[19]="S";
        bloques[20]="T";
        bloques[21]="U";
        bloques[22]="V";
        bloques[23]="W";
        bloques[24]="X";
        bloques[25]="Y";
        bloques[26]="Z";
        bloques[27]="AA";
        bloques[28]="BB";
        bloques[29]="CC";
        bloques[30]="DD";
        bloques[31]="EE";
        bloques[32]="FF";
        bloques[33]="GG";
        bloques[34]="HH";
        
        Object Datos[]=new Object[7]; //Numero de columnas de la tabla, es un Array de objetos.
        clases.Redondear redondear =new clases.Redondear();
        double sumaCant=0;
        for (int b=0;b<=bloques.length-1;b++){
            Datos[0]=bloques[b];
            clases.SOGECOMA.ModeloTablaComparativaConsumo.addRow(Datos);
            for (int c=1;c<=numCuas;c++){//recorre hoja CUADRO
                try{ //usamos try para que trague los espacios en blanco entre items en la hoja CUADRO
                    if (cua.getRow(c).getCell(0).getStringCellValue().equals(item)){ //Si encuentra el Item actual
                        for (int suma=1;suma<=20;suma++){//recorre los materiales de ese item, suponemos un máximo de 20 materiales
                            try{//usamos try para que trague los espacios en blanco entre items en la hoja CUADRO
                                if (cua.getRow(c+suma).getCell(1).getStringCellValue().equals("CONTRATISTA")||
                                    cua.getRow(c+suma).getCell(1).getStringCellValue().equals("")){
                                    break;
                                }else{//encontró un vacío o 'CONTRATISTA', deja de buscar materiales en este item
                                    //Si el campo del nombre del material no está vacío ni es 'CONTRATISTA'
                                    Datos[1]=cua.getRow(c+suma).getCell(1).getStringCellValue();
                                    Datos[2]=cua.getRow(c+suma).getCell(2).getStringCellValue();
                                    try{//usamos try para que trague los espacios en blanco entre cants en la hoja CUADRO
                                        Datos[4]=redondear.redondearDouble((double)cua.getRow(c+suma).getCell(3).getNumericCellValue());
                                        //Recoge de la columna cant de material
                                    }catch(Exception e){}
                                    for (int m=1;m<=numMats;m++){
                                        if (mat.getRow(m).getCell(1).getStringCellValue().equals(Datos[1])){
                                            clases.SOGECOMA.ID_Material=(int)mat.getRow(m).getCell(0).getNumericCellValue();
                                        }
                                    }
                                    for (int e=1;e<=numEnts;e++){//Nos vamos a recepciones para sumar la cant de cada compra del material actual
                                     if ((int)ent.getRow(e).getCell(2).getNumericCellValue()==clases.SOGECOMA.ID_Material&&
                                             ent.getRow(e).getCell(6).getStringCellValue().equals(bloques[b])&&
                                             (int)ent.getRow(e).getCell(7).getNumericCellValue()==clases.SOGECOMA.ID_Item){
                                         sumaCant+=(double)ent.getRow(e).getCell(3).getNumericCellValue();
                                     }
                                    }
                                    double test=sumaCant;
                                    sumaCant+=transferir(clases.SOGECOMA.ID_Material,clases.SOGECOMA.ID_Item,bloques[b]);
                                    if (test!=sumaCant){
                                        System.out.println("La cantidad sin transferencias es "+test);
                                        System.out.println("La cantidad contando las transferencias es "+sumaCant);
                                    }
                                    Datos[3]=redondear.redondearDouble(sumaCant);//Aquí sumaCant es el total de lo entregado
//                                    System.out.println("Suma las cantidades entregadas y las guarda en el array.");
                                    sumaCant=0;//Reseteamos la variable para reutilizarla a continuación.
                                    Datos[5]=redondear.redondearDouble((double)Datos[4]-(double)Datos[3]);
                                    Double porcentaje = redondear.redondearDouble((Double.parseDouble(Datos[3].toString())*100)/Double.parseDouble(Datos[4].toString()));
                                    Datos[6]=porcentaje+"%";
                                    clases.SOGECOMA.ModeloTablaComparativaConsumo.addRow(Datos); //Añadimos una fila con el Array Datos.
//                                    System.out.println("Agrega una nueva fila en la tabla para el material encontrado.");
                                }
                            }catch(Exception e){}
                        } 
                    }
                }catch(Exception e){}
                for (int d=0;d<=6;d++){//resetamos el array para la siguiente vuelta
                                        Datos[d]="";
                }
            }
        }
    } 
    private double consultarCuadro(String item,String mat) throws IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook libro = new HSSFWorkbook(fs);
        HSSFSheet cua = libro.getSheetAt(6);
        int numCuas = cua.getLastRowNum();
        double cant=0;
        clases.Redondear redondear=new clases.Redondear();
        
        for (int c=1;c<=numCuas;c++){//recorremos el cuadro para ver cuanto de ese material es necesario en ese item
            try{                  
                if (cua.getRow(c).getCell(0).getStringCellValue().equals(item)){//Buscamos el item:
                    for (int sum=1;sum<=20;sum++){//recorremos los materiales de ese item
                        try{
                            if (cua.getRow(c+sum).getCell(1).getStringCellValue().equals(mat)){
                                //si encontramos ese material:
                                cant=redondear.redondearDouble((double)cua.getRow(c+sum).getCell(3).getNumericCellValue());
                                break;
                            }
                        }catch (Exception ex){}
                    }
                    break;
                }
            }catch(Exception ex){}
        }
        return cant;
    }
    public void cargarTablaStock(Boolean buscar,Boolean minimos) throws IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook libro = new HSSFWorkbook(fs);
        HSSFSheet ent = libro.getSheetAt(1);
        HSSFSheet rec = libro.getSheetAt(0);
        HSSFSheet mat = libro.getSheetAt(3);
        int numEnts = ent.getLastRowNum();
        int numMats = mat.getLastRowNum();
        int numRecs = rec.getLastRowNum();
        
        Object Datos[]= new Object[6];
        
        double recibido=0.0;
        double entregado=0.0;
        Redondear red=new Redondear();
        
        clases.SOGECOMA.ModeloTablaStock.addColumn("Material");
        clases.SOGECOMA.ModeloTablaStock.addColumn("Unidad");
        clases.SOGECOMA.ModeloTablaStock.addColumn("Existencias");
        clases.SOGECOMA.ModeloTablaStock.addColumn("Mínimo permitido");
        clases.SOGECOMA.ModeloTablaStock.addColumn("Recibido");
        clases.SOGECOMA.ModeloTablaStock.addColumn("Entregado");
        clases.SumaRestaStock srStock=new clases.SumaRestaStock();
        
        for (int m=1;m<=numMats;m++){//recorremos materiales
            if (buscar){//si se pulsó el botón buscar material
                if ((int)clases.SOGECOMA.ID_Material==(int)mat.getRow(m).getCell(0).getNumericCellValue()){
                    Datos[0]=mat.getRow(m).getCell(1).getStringCellValue();
                    Datos[1]=mat.getRow(m).getCell(2).getStringCellValue();
//                    Datos[2]=(double)mat.getRow(m).getCell(3).getNumericCellValue();
                    Datos[3]=(double)mat.getRow(m).getCell(4).getNumericCellValue();
                    for (int r=1;r<=numRecs;r++){//recorremos recepciones
                        if (rec.getRow(r).getCell(2).getNumericCellValue()==mat.getRow(m).getCell(0).getNumericCellValue()){
                            recibido+=(double)rec.getRow(r).getCell(3).getNumericCellValue();
                        }
                    }
                    Datos[4]=red.redondearDouble(recibido);
                    for (int e=1;e<=numEnts;e++){//recorremos entregas
                        if (ent.getRow(e).getCell(2).getNumericCellValue()==mat.getRow(m).getCell(0).getNumericCellValue()){
                            entregado+=(double)ent.getRow(e).getCell(3).getNumericCellValue();
                        }
                    }
                    
                    Datos[5]=red.redondearDouble(entregado);
                    Datos[2]=recibido-entregado;
                    recibido=0;
                    entregado=0;
                    clases.SOGECOMA.ModeloTablaStock.addRow(Datos);
                    break;
                }
            }else{
                if (minimos){//si se pulsó el botón mostrar solo materiales bajo mínimos
                    
                        Datos[0]=mat.getRow(m).getCell(1).getStringCellValue();
                        Datos[1]=mat.getRow(m).getCell(2).getStringCellValue();
//                        Datos[2]=(double)mat.getRow(m).getCell(3).getNumericCellValue();
                        Datos[3]=(double)mat.getRow(m).getCell(4).getNumericCellValue();
                        for (int r=1;r<=numRecs;r++){//recorremos recepciones
                            if (rec.getRow(r).getCell(2).getNumericCellValue()==mat.getRow(m).getCell(0).getNumericCellValue()){
                                recibido+=(double)rec.getRow(r).getCell(3).getNumericCellValue();
                            }
                        }
                        Datos[4]=red.redondearDouble(recibido);
                        for (int e=1;e<=numEnts;e++){//recorremos entregas
                            if (ent.getRow(e).getCell(2).getNumericCellValue()==mat.getRow(m).getCell(0).getNumericCellValue()){
                                entregado+=(double)ent.getRow(e).getCell(3).getNumericCellValue();
                            }
                        }
                        Datos[5]=red.redondearDouble(entregado);
                        Datos[2]=recibido-entregado;
                        recibido=0;
                        entregado=0;
                        if (Double.valueOf(Datos[2].toString())<Double.valueOf(Datos[3].toString())){
                            clases.SOGECOMA.ModeloTablaStock.addRow(Datos);
                        }
                    
                }else{//Mostrar todos
                    Datos[0]=mat.getRow(m).getCell(1).getStringCellValue();
                    Datos[1]=mat.getRow(m).getCell(2).getStringCellValue();
//                    Datos[2]=(double)mat.getRow(m).getCell(3).getNumericCellValue();
                    Datos[3]=(double)mat.getRow(m).getCell(4).getNumericCellValue();
                    for (int r=1;r<=numRecs;r++){//recorremos recepciones
                        if (rec.getRow(r).getCell(2).getNumericCellValue()==mat.getRow(m).getCell(0).getNumericCellValue()){
                            recibido+=(double)rec.getRow(r).getCell(3).getNumericCellValue();
                        }
                    }
                    Datos[4]=red.redondearDouble(recibido);
                    for (int e=1;e<=numEnts;e++){//recorremos entregas
                        if (ent.getRow(e).getCell(2).getNumericCellValue()==mat.getRow(m).getCell(0).getNumericCellValue()){
                            entregado+=(double)ent.getRow(e).getCell(3).getNumericCellValue();
                        }
                    }
                    Datos[5]=red.redondearDouble(entregado);
                    Datos[2]=recibido-entregado;
                    recibido=0;
                    entregado=0;
                    clases.SOGECOMA.ModeloTablaStock.addRow(Datos);
                }
            }
        }    
    }
    public void cargarTablaBuscarItemEnTodos(String item) throws FileNotFoundException, IOException{//sumatoria
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook libro = new HSSFWorkbook(fs);
        HSSFSheet ent = libro.getSheetAt(1);
        HSSFSheet mat = libro.getSheetAt(3);
        HSSFSheet cua = libro.getSheetAt(6);
        int numEnts = ent.getLastRowNum();
        int numMats = mat.getLastRowNum();
        int numCuas = cua.getLastRowNum();
        
        clases.SOGECOMA.ModeloTablaBuscarItemEnTodos.addColumn("Material");
        clases.SOGECOMA.ModeloTablaBuscarItemEnTodos.addColumn("Unidad");
        clases.SOGECOMA.ModeloTablaBuscarItemEnTodos.addColumn("Entregado");
        clases.SOGECOMA.ModeloTablaBuscarItemEnTodos.addColumn("Total a Entregar");
        clases.SOGECOMA.ModeloTablaBuscarItemEnTodos.addColumn("Falta por entregar");
        clases.SOGECOMA.ModeloTablaBuscarItemEnTodos.addColumn("Progreso");

        Object Datos[]=new Object[8]; //Numero de columnas de la tabla, es un Array de objetos.
        clases.Redondear redondear =new clases.Redondear();
        double sumaCant=0;
        
        for (int c=1;c<=numCuas;c++){//recorre hoja CUADRO
            try{ //usamos try para que trague los espacios en blanco entre items en la hoja CUADRO
                if (cua.getRow(c).getCell(0).getStringCellValue().equals(item)){ //Si encuentra el Item actual
                    for (int suma=1;suma<=20;suma++){//recorre los materiales de ese item, suponemos un máximo de 20 materiales
                        try{//usamos try para que trague los espacios en blanco entre items en la hoja CUADRO
                            if (cua.getRow(c+suma).getCell(1).getStringCellValue().equals("CONTRATISTA")||
                                cua.getRow(c+suma).getCell(1).getStringCellValue().equals("")){
                                break;
                            }else{//encontró un vacío o 'CONTRATISTA', deja de buscar materiales en este item
                                //Si el campo del nombre del material no está vacío ni es 'CONTRATISTA'
                                Datos[0]=cua.getRow(c+suma).getCell(1).getStringCellValue();
                                Datos[1]=cua.getRow(c+suma).getCell(2).getStringCellValue();
                                try{//usamos try para que trague los espacios en blanco entre cants en la hoja CUADRO
                                    Datos[3]=redondear.redondearDouble((double)cua.getRow(c+suma).getCell(3).getNumericCellValue()*33);
                                    //Recoge de la columna cant de material
                                }catch(Exception e){}
                                for (int m=1;m<=numMats;m++){
                                    if (mat.getRow(m).getCell(1).getStringCellValue().equals(Datos[0])){
                                        clases.SOGECOMA.ID_Material=(int)mat.getRow(m).getCell(0).getNumericCellValue();
                                    }
                                }
                                for (int e=1;e<=numEnts;e++){//Nos vamos a recepciones para sumar la cant de cada compra del material actual
                                 if ((int)ent.getRow(e).getCell(2).getNumericCellValue()==clases.SOGECOMA.ID_Material&&
                                         (int)ent.getRow(e).getCell(7).getNumericCellValue()==clases.SOGECOMA.ID_Item){
                                     sumaCant+=(double)ent.getRow(e).getCell(3).getNumericCellValue();
                                 }
                                }
//                                sumaCant+=transferir(clases.SOGECOMA.ID_Material,clases.SOGECOMA.ID_Item);
                                Datos[2]=redondear.redondearDouble(sumaCant);//Aquí sumaCant es el total de lo entregado
                                sumaCant=0;//Reseteamos la variable para reutilizarla a continuación.
                                
                                sumaCant=0;//Reseteamos sumaCant para reutilizarlo en la siguiente vuelta del FOR
                                Datos[4]=redondear.redondearDouble((double)Datos[3]-(double)Datos[2]);
                                Double porcentaje = redondear.redondearDouble((Double.parseDouble(Datos[2].toString())*100)/Double.parseDouble(Datos[3].toString()));
                                Datos[5]=porcentaje+"%";
                                clases.SOGECOMA.ModeloTablaBuscarItemEnTodos.addRow(Datos); //Añadimos una fila con el Array Datos.
                            }
                        }catch(Exception e){}
                    } 
                }
            }catch(Exception e){}
        }
    } 
    public void cargarTablaVerRecepciones() throws FileNotFoundException, IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook libro = new HSSFWorkbook(fs);
        HSSFSheet rec = libro.getSheetAt(0);
        HSSFSheet mat = libro.getSheetAt(3);
        int numRecs = rec.getLastRowNum();
        int numMats = mat.getLastRowNum();
        Object Datos[]=new Object[6];
        clases.SOGECOMA.ModeloTablaVerRecepciones.addColumn("ID Recepción");
        clases.SOGECOMA.ModeloTablaVerRecepciones.addColumn("Fecha y Hora");
        clases.SOGECOMA.ModeloTablaVerRecepciones.addColumn("Almacén");
        clases.SOGECOMA.ModeloTablaVerRecepciones.addColumn("Material");
        clases.SOGECOMA.ModeloTablaVerRecepciones.addColumn("Unidad");
        clases.SOGECOMA.ModeloTablaVerRecepciones.addColumn("Cantidad");
        
        for (int r=1;r<=numRecs;r++){
            Datos[0]=(int)rec.getRow(r).getCell(0).getNumericCellValue();
            Datos[1]=rec.getRow(r).getCell(1).getStringCellValue();
            Datos[2]=rec.getRow(r).getCell(6);
            for (int m=1;m<=numMats;m++){
                if (mat.getRow(m).getCell(0).getNumericCellValue()==rec.getRow(r).getCell(2).getNumericCellValue()){
                    Datos[3]=mat.getRow(m).getCell(1).getStringCellValue();
                    Datos[4]=mat.getRow(m).getCell(2).getStringCellValue();
                    break;
                }
            }
            Datos[5]=rec.getRow(r).getCell(3).getNumericCellValue();
            clases.SOGECOMA.ModeloTablaVerRecepciones.addRow(Datos);
        }
    }
    public void cargarTablaVerEntregas() throws FileNotFoundException, IOException{
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook libro = new HSSFWorkbook(fs);
        HSSFSheet ent = libro.getSheetAt(1);
        HSSFSheet mat = libro.getSheetAt(3);
        HSSFSheet ite = libro.getSheetAt(4);
        int numEnts = ent.getLastRowNum();
        int numMats = mat.getLastRowNum();
        int numItes = ite.getLastRowNum();
        Object Datos[]=new Object[8];
        clases.SOGECOMA.ModeloTablaVerEntregas.addColumn("ID Entrega");
        clases.SOGECOMA.ModeloTablaVerEntregas.addColumn("Fecha y Hora");
        clases.SOGECOMA.ModeloTablaVerEntregas.addColumn("Almacén");
        clases.SOGECOMA.ModeloTablaVerEntregas.addColumn("Material");
        clases.SOGECOMA.ModeloTablaVerEntregas.addColumn("Unidad");
        clases.SOGECOMA.ModeloTablaVerEntregas.addColumn("Cantidad");
        clases.SOGECOMA.ModeloTablaVerEntregas.addColumn("Bloque");
        clases.SOGECOMA.ModeloTablaVerEntregas.addColumn("Ítem");
        
        for (int e=1;e<=numEnts;e++){
            Datos[0]=(int)ent.getRow(e).getCell(0).getNumericCellValue();
            Datos[1]=ent.getRow(e).getCell(1).getStringCellValue();
            Datos[2]=ent.getRow(e).getCell(8);
            for (int m=1;m<=numMats;m++){
                if (mat.getRow(m).getCell(0).getNumericCellValue()==ent.getRow(e).getCell(2).getNumericCellValue()){
                    Datos[3]=mat.getRow(m).getCell(1).getStringCellValue();
                    Datos[4]=mat.getRow(m).getCell(2).getStringCellValue();
                    break;
                } 
            }
            Datos[5]=ent.getRow(e).getCell(3).getNumericCellValue();
            Datos[6]=ent.getRow(e).getCell(6).getStringCellValue();
            for (int i=1;i<=numItes;i++){
                if (ite.getRow(i).getCell(0).getNumericCellValue()==ent.getRow(e).getCell(7).getNumericCellValue()){
                    Datos[7]=ite.getRow(i).getCell(2).getStringCellValue();
                    break;
                }
            }
            clases.SOGECOMA.ModeloTablaVerEntregas.addRow(Datos);
        }
    }
    
    private double transferir(int ID_Material,int ID_Item,String bloque) throws FileNotFoundException, IOException{
        //Si no se encuentran transferencias de ese material en ese item en ese bloque, devuelve cero.
        double resultado=0;
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
        HSSFWorkbook libro = new HSSFWorkbook(fs);
        HSSFSheet trans = libro.getSheetAt(7);
        int numTrans = trans.getLastRowNum();
        for (int i=1;i<=numTrans;i++){//recorremos las transferencias
            //comprobamos si ese material en ese item en ese bloque perdió en una transferencia
            if ((int)trans.getRow(i).getCell(2).getNumericCellValue()==ID_Material &&
                (int)trans.getRow(i).getCell(4).getNumericCellValue()==ID_Item &&
                trans.getRow(i).getCell(5).getStringCellValue().equals(bloque)){//
                    resultado=trans.getRow(i).getCell(3).getNumericCellValue();
                    resultado=resultado*(-1);//convertimos lo transferido a un número negativo.
                    System.out.println("Restó");
            //comprobamos si ese material en ese item en ese bloque ganó en una transferencia
            }else if ((int)trans.getRow(i).getCell(2).getNumericCellValue()==ID_Material &&
                (int)trans.getRow(i).getCell(6).getNumericCellValue()==ID_Item &&
                trans.getRow(i).getCell(7).getStringCellValue().equals(bloque)){
                    resultado=trans.getRow(i).getCell(3).getNumericCellValue();
                    System.out.println("Sumó");
            }
        }
        return resultado;
    }
}
