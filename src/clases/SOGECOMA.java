/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import ventanas.FormularioPrincipal;

/**
 *
 * @author Kaze
 */
public class SOGECOMA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FormularioPrincipal FPrincipal = new FormularioPrincipal();
        FPrincipal.setVisible(true);
        comprobarArchivo();
    }
    public static void comprobarArchivo() throws FileNotFoundException, IOException{
        //Comprobar si ya existe el archivo xls, sino, se crea.
        try{
            FileInputStream f1 = new FileInputStream("SOGECOMA.xls");
            InputStreamReader f2 = new InputStreamReader(f1);
            BufferedReader linea = new BufferedReader(f2);
        }
        catch (IOException ioe){
            if (JOptionPane.showConfirmDialog(null, "No se encuentra el archivo SOGECOMA.xls, ¿Desea crearlo ahora?") == 0){
                HSSFWorkbook libro = new HSSFWorkbook();
                
                HSSFSheet Recepciones = libro.createSheet("Recepciones");
                String[] RCabeceras = new String[]{ //Array dinámico con los nombres de las cabeceras.
                     "ID_REC","FECHA Y HORA","ID_MATERIAL","CANTIDAD","PROVEEDOR","ALMACENERO","INT O EXT"
                };                
                HSSFRow CabeceraRecepciones = Recepciones.createRow(0); //Crea la cabecera de la tabla Clientes.
                for (int i=0;i<=6;i++){
                    CabeceraRecepciones.createCell(i).setCellValue(RCabeceras[i]); //Se asignan a las cabeceras las posiciones del Array.
                }
                
                HSSFSheet Entregas = libro.createSheet("Entregas"); //Crea la cabecera de la tabla Alquileres.
                String[] ECabeceras = new String[]{
                    "ID_ENT","FECHA Y HORA","ID_MATERIAL","CANTIDAD","ALMACENERO","CONTRATÍSTA","BLOQUE","ID_ITEM","INT O EXT"
                };
                HSSFRow CabeceraEntregas = Entregas.createRow(0);
                for (int i=0;i<=8;i++){
                    CabeceraEntregas.createCell(i).setCellValue(ECabeceras[i]);
                }
                
                HSSFSheet Contratistas = libro.createSheet("Contratístas"); //Crea la cabecera de la tabla Items.
                String[] CCabeceras = new String[]{
                    "ID_CONT","BLOQUE","NOMBRE","FECHA INICIO"
                };
                HSSFRow CabeceraContratistas = Contratistas.createRow(0);
                for (int i=0;i<=3;i++){
                    CabeceraContratistas.createCell(i).setCellValue(CCabeceras[i]);
                }
                
                HSSFSheet Materiales = libro.createSheet("Materiales");
                String[] MCabeceras = new String[]{ //Array dinámico con los nombres de las cabeceras.
                     "ID_MATERIAL","NOMBRE MATERIAL","UNIDAD","STOCK","MINIMO"
                };                
                HSSFRow CabeceraMateriales = Materiales.createRow(0); //Crea la cabecera de la tabla Clientes.
                for (int i=0;i<=4;i++){
                    CabeceraMateriales.createCell(i).setCellValue(MCabeceras[i]); //Se asignan a las cabeceras las posiciones del Array.
                }
                
                HSSFSheet Items = libro.createSheet("Ítems"); //Crea la cabecera de la tabla Alquileres.
                String[] ICabeceras = new String[]{
                    "ID_ITEM","NUM. ITEM","NOMBRE ITEM"
                };
                HSSFRow CabeceraItems = Items.createRow(0);
                for (int i=0;i<=2;i++){
                    CabeceraItems.createCell(i).setCellValue(ICabeceras[i]);
                }
                
                HSSFSheet Avance = libro.createSheet("Avance"); //Crea la cabecera de la tabla Items.
                String[] ACabeceras = new String[]{
                    "Bloque","Nombre Contratísta","ID_Item","Fecha Inicio","Fecha Entrega"
                };
                HSSFRow CabeceraAvance = Avance.createRow(0);
                for (int i=0;i<=4;i++){
                    CabeceraAvance.createCell(i).setCellValue(ACabeceras[i]);
                }
                
                String Ruta = "SOGECOMA.xls";
                File Archivo = new File(Ruta);
                try (FileOutputStream archivosalida = new FileOutputStream(Archivo)) {
                    libro.write(archivosalida);
                }
                catch (IOException e){
                    JOptionPane.showMessageDialog(null, "No se pudo crear el archivo");
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "La aplicación no puede funcionar sin este archivo y se cerrará.");
                System.exit(1);
            }
        }
    }
    public static void subirArchivoPorFTP(){
        String server="mailkaze.99k.org/", user="mailkaze_99k", pass="kiheitai2004", localPath="SOGECOMA.xls", remotePath="SOGECOMA.xls";
        try {
            URL url = new URL("ftp://" + user + ":" + pass + "@" + server + remotePath + ";type=i");
            URLConnection urlc = url.openConnection();
            System.out.println("Conexión establecida.");
            OutputStream destino = urlc.getOutputStream();
            InputStream origen = null;
            File fichero = new File(localPath);
            origen = new FileInputStream(fichero);
            byte bytes[] = new byte[1024];
            int readCount = 0;
            while ((readCount = origen.read(bytes)) > 0) {
                destino.write(bytes, 0, readCount);
                System.out.println(".");
            }
            destino.flush();
            destino.close();
            origen.close();
            bajarArchivoPorFTP("Test_SOGECOMA");//Descargamos el archivo para comprobar que se subió correctamente.
            String localPath2="Test_SOGECOMA";
            File fichero2 =new File(localPath2);
            if (fichero.length()==fichero2.length()){
                System.out.println("Tamaño archivo original:"+fichero.length()+" Tamaño archivo de comprobación:"+fichero2.length());
                    JOptionPane.showMessageDialog(null, "El archivo SOGECOMA.xls se copió correctamente en el servidor.");
            }else {
                System.out.println("Tamaño archivo original:"+fichero.length()+" Tamaño archivo de comprobación:"+fichero2.length());
                JOptionPane.showMessageDialog(null, "Parece que el archivo no se copió correctamente,\npor favor, vuelva a intentarlo.");
            }
            fichero2.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ha ocurrido un problema al intentar acceder al servidor.\nPor favor, vuelva a intentarlo.");
        }
    }    
    public static void bajarArchivoPorFTP(String localPath){
        String server="mailkaze.99k.org/", user="mailkaze_99k", pass="kiheitai2004", remotePath="SOGECOMA.xls";
        try {
            URL url = new URL("ftp://" + user + ":" + pass + "@" + server + remotePath + ";type=i");
            URLConnection urlc = url.openConnection();
            System.out.println("Conexión establecida.");
            InputStream origen = urlc.getInputStream();
            File fichero = new File(localPath);
            OutputStream destino = new FileOutputStream(fichero);
            byte bytes[] = new byte[1024];
            int readCount = 0;
            while ((readCount = origen.read(bytes)) > 0) {
                destino.write(bytes, 0, readCount);
                System.out.println(".");
            }
            destino.flush();
            destino.close();
            origen.close();
            if (localPath.equals("SOGECOMA.xls")){
                JOptionPane.showMessageDialog(null, "La copia de seguridad se ha descargado correctamente del servidor.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            if (localPath.equals("SOGECOMA.xls")){
                JOptionPane.showMessageDialog(null, "Ha ocurrido un problema al intentar acceder al servidor.\nPor favor, vuelva a intentarlo.");
            }
        }
    }
    //Declaración de variables globales:
    public static boolean cerrarVentana,RoE,cargarDatos; //true es Recepcion, false es Entrega
    public static int ID_Material,ID_Item,ID_Contratista,ID_Rec,ID_Ent,ID_Trans,ID_ItemOrigen,ID_ItemDestino;
    public static String nomMaterial,udMaterial,nomItem,numItem,nomContratista,
                         bloqueContratista,almaceneroRec,proveedorRec,almacenRec,
                         almaceneroEnt,contratistaEnt,almacenEnt,bloqueEnt,
                         inicioContratista,fechaHoraRec,fechaHoraEnt,fechaHoraTrans,
                         bloqueOrigen,bloqueDestino,numItemOrigen,nomItemOrigen,
                         numItemDestino,nomItemDestino;
    public static Double cantRec,cantEnt,stockMaterial,minMaterial,cantTrans;
    public static DefaultTableModel ModeloTablaTodosItems = new DefaultTableModel();
    public static DefaultTableModel ModeloTablaCompras = new DefaultTableModel();
    public static DefaultTableModel ModeloTablaBuscarBloque = new DefaultTableModel();
    public static DefaultTableModel ModeloTablaBuscarItem = new DefaultTableModel();
    public static DefaultTableModel ModeloTablaBuscarMaterial = new DefaultTableModel();
    public static DefaultTableModel ModeloTablaComparativaConsumo = new DefaultTableModel();
    public static DefaultTableModel ModeloTablaStock = new DefaultTableModel();
    public static DefaultTableModel ModeloTablaBuscarItemEnTodos = new DefaultTableModel();
    public static DefaultTableModel ModeloTablaVerRecepciones = new DefaultTableModel();
    public static DefaultTableModel ModeloTablaVerEntregas = new DefaultTableModel();
}
