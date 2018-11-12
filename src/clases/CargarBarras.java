package clases;

import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class CargarBarras extends JProgressBar implements TableCellRenderer{//DefaultTableCellRenderer{
    private int columnaEntregado;
    private int columnaMaximo;

    public CargarBarras(int colEnt, int colMax)//int Colpatron, boolean esPendiente
    {
        this.columnaEntregado=colEnt;
        this.columnaMaximo=colMax;
    }

//    @Override
    public Component getTableCellRendererComponent ( JTable table, Object value, boolean selected, boolean focused, int row, int column )
    {        
        //Si las existencias son menores que el mínimo, se cambia el color de fondo a rojo
        double existencias=Double.valueOf(String.valueOf(table.getValueAt(row, columnaEntregado)));
        double minimo=Double.valueOf(String.valueOf(table.getValueAt(row, columnaMaximo)));
        this.setMaximum((int)existencias);
        this.setValue((int)minimo);
//        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
 }

}