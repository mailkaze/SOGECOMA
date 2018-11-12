/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author Manuel
 */
public class DialogoVerER extends javax.swing.JDialog {
    private void verID(){
        int registro = tablaVerER.getSelectedRow();
        if (clases.SOGECOMA.RoE){//es recepcion
            int ID=(int)clases.SOGECOMA.ModeloTablaVerRecepciones.getValueAt(registro, 0);
            clases.SOGECOMA.ID_Rec=ID;
        }else{//es entrega
            int ID=(int)clases.SOGECOMA.ModeloTablaVerEntregas.getValueAt(registro, 0);
            clases.SOGECOMA.ID_Ent=ID;
        }
    }
    private void buscar() throws FileNotFoundException, IOException{
        boolean material=false,item=false,bloque=false,fecha=false;
        if (!txtMaterial.getText().equals("")){//si el campo de búsqueda no está vacío
            material=true;
        }
        if (!txtNombreItem.getText().equals("")){
            item=true;
        }
        if (cboBloque.getSelectedIndex()!=0){
            bloque=true;
        }
        if (chkFecha.isSelected()){
            fecha=true;
        }
//        boolean encontrado=false;
        if (clases.SOGECOMA.RoE){//Es Recepción
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
            HSSFWorkbook libro = new HSSFWorkbook(fs);
            HSSFSheet rec = libro.getSheetAt(0);
            HSSFSheet mat = libro.getSheetAt(3);
            int numRecs = rec.getLastRowNum();
            int numMats = mat.getLastRowNum();
            Object Datos[]=new Object[6];
            clases.SOGECOMA.ModeloTablaVerRecepciones.setRowCount(0);
            clases.SOGECOMA.ModeloTablaVerRecepciones.setColumnCount(0);
            tablaVerER.setModel(clases.SOGECOMA.ModeloTablaVerRecepciones);
            clases.SOGECOMA.ModeloTablaVerRecepciones.addColumn("ID Recepción");
            clases.SOGECOMA.ModeloTablaVerRecepciones.addColumn("Fecha y Hora");
            clases.SOGECOMA.ModeloTablaVerRecepciones.addColumn("Almacén");
            clases.SOGECOMA.ModeloTablaVerRecepciones.addColumn("Material");
            clases.SOGECOMA.ModeloTablaVerRecepciones.addColumn("Unidad");
            clases.SOGECOMA.ModeloTablaVerRecepciones.addColumn("Cantidad");
            if (material){//busca por material.
                for (int r=1;r<=numRecs;r++){
                    if ((int)rec.getRow(r).getCell(2).getNumericCellValue()==clases.SOGECOMA.ID_Material){
                        //carga los datos para mostrar en el cuadro
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
            }
            if (fecha){//busca por fecha
                
            }
//*******************************************************************************************
//*******************************************************************************************
        }else{//Es Entrega
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("SOGECOMA.xls"));
            HSSFWorkbook libro = new HSSFWorkbook(fs);
            HSSFSheet ent = libro.getSheetAt(1);
            HSSFSheet mat = libro.getSheetAt(3);
            HSSFSheet ite = libro.getSheetAt(4);
            int numEnts = ent.getLastRowNum();
            int numMats = mat.getLastRowNum();
            int numItes = ite.getLastRowNum();
            Object Datos[]=new Object[8];
            clases.SOGECOMA.ModeloTablaVerEntregas.setRowCount(0);
            clases.SOGECOMA.ModeloTablaVerEntregas.setColumnCount(0);
            tablaVerER.setModel(clases.SOGECOMA.ModeloTablaVerEntregas);
            clases.SOGECOMA.ModeloTablaVerEntregas.addColumn("ID Entrega");
            clases.SOGECOMA.ModeloTablaVerEntregas.addColumn("Fecha y Hora");
            clases.SOGECOMA.ModeloTablaVerEntregas.addColumn("Almacén");
            clases.SOGECOMA.ModeloTablaVerEntregas.addColumn("Material");
            clases.SOGECOMA.ModeloTablaVerEntregas.addColumn("Unidad");
            clases.SOGECOMA.ModeloTablaVerEntregas.addColumn("Cantidad");
            clases.SOGECOMA.ModeloTablaVerEntregas.addColumn("Bloque");
            clases.SOGECOMA.ModeloTablaVerEntregas.addColumn("Ítem");
            if (material){//busca por material.
                for (int e=1;e<=numEnts;e++){
                    if ((int)ent.getRow(e).getCell(2).getNumericCellValue()==clases.SOGECOMA.ID_Material){
                        //carga los datos para mostrar en el cuadro;
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
            }
            if (item){//busca por item
                for (int e=1;e<=numEnts;e++){
                    if ((int)ent.getRow(e).getCell(7).getNumericCellValue()==clases.SOGECOMA.ID_Item){
                        //carga los datos para mostrar en el cuadro;
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
            }
            if (bloque){//busca por bloque
                for (int e=1;e<=numEnts;e++){
                    if (ent.getRow(e).getCell(6).getStringCellValue().equals(cboBloque.getSelectedItem().toString())){
                        //carga los datos para mostrar en el cuadro;
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
            }
            if (fecha){//busca por fecha
                String soloFecha;
                for (int e=1;e<=numEnts;e++){
                    soloFecha=ent.getRow(e).getCell(1).getStringCellValue().substring(0, 10);
                    if (txtFecha.getText().equals(soloFecha)){
                        //carga los datos para mostrar en el cuadro;
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
            }
        }
    }
    private void cargarTabla() throws FileNotFoundException, IOException{
        if (clases.SOGECOMA.RoE){//Es Recepcion
            clases.SOGECOMA.ModeloTablaVerRecepciones.setRowCount(0);
            clases.SOGECOMA.ModeloTablaVerRecepciones.setColumnCount(0);
            tablaVerER.setModel(clases.SOGECOMA.ModeloTablaVerRecepciones);
            this.setTitle("Ver Recepciones");
            cmdSeleccionarItem.setVisible(false);
            txtNombreItem.setVisible(false);
            txtCodigoItem.setVisible(false);
            cboBloque.setVisible(false);
            jLabel9.setVisible(false);
            System.out.println("Es recepción.");
            clases.CargarTablas carTab=new clases.CargarTablas();
            carTab.cargarTablaVerRecepciones();
            tablaVerER.setModel(clases.SOGECOMA.ModeloTablaVerRecepciones);
        }else{//Es entrega
            clases.SOGECOMA.ModeloTablaVerEntregas.setRowCount(0);
            clases.SOGECOMA.ModeloTablaVerEntregas.setColumnCount(0);
            tablaVerER.setModel(clases.SOGECOMA.ModeloTablaVerEntregas);
            this.setTitle("Ver Entregas");
            cmdSeleccionarItem.setVisible(true);
            txtNombreItem.setVisible(true);
            txtCodigoItem.setVisible(true);
            cboBloque.setVisible(true);
            cboBloque.setVisible(true);
            System.out.println("Es entrega.");
            clases.CargarTablas carTab=new clases.CargarTablas();
            carTab.cargarTablaVerEntregas();
            tablaVerER.setModel(clases.SOGECOMA.ModeloTablaVerEntregas);
            clases.FechaActual fecha=new clases.FechaActual();
            txtFecha.setText(fecha.obtenerFecha());
            txtFecha.setEnabled(false);
        }
    }
    /**
     * Creates new form DialogoVerRecepciones
     */
    public DialogoVerER(java.awt.Frame parent, boolean modal) throws FileNotFoundException, IOException {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        cargarTabla();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVerER = new javax.swing.JTable();
        txtFecha = new javax.swing.JFormattedTextField();
        txtMaterial = new javax.swing.JTextField();
        cmdSeleccionarMarerial = new javax.swing.JButton();
        cmdSeleccionarItem = new javax.swing.JButton();
        txtCodigoItem = new javax.swing.JTextField();
        txtNombreItem = new javax.swing.JTextField();
        cmdBuscar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        cboBloque = new javax.swing.JComboBox();
        chkFecha = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tablaVerER.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaVerER.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaVerERMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaVerER);

        txtFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));

        txtMaterial.setEditable(false);

        cmdSeleccionarMarerial.setText("Buscar por Material");
        cmdSeleccionarMarerial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSeleccionarMarerialActionPerformed(evt);
            }
        });

        cmdSeleccionarItem.setText("Buscar por Ítem");
        cmdSeleccionarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSeleccionarItemActionPerformed(evt);
            }
        });

        txtCodigoItem.setEditable(false);

        txtNombreItem.setEditable(false);

        cmdBuscar.setText("Buscar");
        cmdBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBuscarActionPerformed(evt);
            }
        });

        jLabel9.setText("Buscar por Bloque:");

        cboBloque.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "BB", "CC", "DD", "EE", "FF", "GG", "HH" }));

        chkFecha.setText("Buscar por Fecha:");
        chkFecha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chkFechaMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmdSeleccionarItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmdSeleccionarMarerial, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCodigoItem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreItem))
                            .addComponent(txtMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(chkFecha, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboBloque, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdBuscar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmdSeleccionarItem)
                        .addComponent(txtCodigoItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNombreItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(cboBloque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(chkFecha))
                                .addGap(24, 24, 24))
                            .addComponent(cmdBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmdSeleccionarMarerial)
                                .addComponent(txtMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(26, 26, 26))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdSeleccionarMarerialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSeleccionarMarerialActionPerformed
        try {
            DialogoSelectorMaterial selMat = new DialogoSelectorMaterial(null,true);
            selMat.setVisible(true);
            txtMaterial.setText(clases.SOGECOMA.nomMaterial);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DialogoNuevaEntrega.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DialogoNuevaEntrega.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cmdSeleccionarMarerialActionPerformed

    private void cmdSeleccionarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSeleccionarItemActionPerformed
        try {
            DialogoSelectorItem selIt = new DialogoSelectorItem(null,true);
            selIt.setVisible(true);
            txtNombreItem.setText(clases.SOGECOMA.nomItem);
            txtCodigoItem.setText(clases.SOGECOMA.numItem);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DialogoNuevaEntrega.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DialogoNuevaEntrega.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cmdSeleccionarItemActionPerformed

    private void cmdBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBuscarActionPerformed
        try {
            buscar();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DialogoVerER.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DialogoVerER.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cmdBuscarActionPerformed

    private void chkFechaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkFechaMouseReleased
        if (chkFecha.isSelected()){
            txtFecha.setEnabled(true);
        }else{
            txtFecha.setEnabled(false);
        }
    }//GEN-LAST:event_chkFechaMouseReleased

    private void tablaVerERMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVerERMouseClicked
        verID();
        try{
            if (clases.SOGECOMA.RoE){//es recepcion
                clases.AbrirRecepcion.abrirRecepcion();
                ventanas.DialogoNuevaRecepcion diaRec = new ventanas.DialogoNuevaRecepcion(null, true);
                diaRec.setVisible(true);
            }else{//es entrega
                clases.AbrirEntrega.abrirEntrega();
                ventanas.DialogoNuevaEntrega diaEnt = new ventanas.DialogoNuevaEntrega(null, true);
                diaEnt.setVisible(true);
            }
            cargarTabla();
        }catch(Exception e){}
    }//GEN-LAST:event_tablaVerERMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DialogoVerER.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogoVerER.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogoVerER.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogoVerER.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DialogoVerER dialog = new DialogoVerER(new javax.swing.JFrame(), true);
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    dialog.setVisible(true);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(DialogoVerER.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(DialogoVerER.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboBloque;
    private javax.swing.JCheckBox chkFecha;
    private javax.swing.JButton cmdBuscar;
    private javax.swing.JButton cmdSeleccionarItem;
    private javax.swing.JButton cmdSeleccionarMarerial;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaVerER;
    private javax.swing.JTextField txtCodigoItem;
    private javax.swing.JFormattedTextField txtFecha;
    private javax.swing.JTextField txtMaterial;
    private javax.swing.JTextField txtNombreItem;
    // End of variables declaration//GEN-END:variables
}
