package clases;

import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorearTablas extends DefaultTableCellRenderer{
    private int columnaExistencias;
    private int columnaMinimo;

    public ColorearTablas(int colExis, int colMin)//int Colpatron, boolean esPendiente
    {
        this.columnaExistencias=colExis;
        this.columnaMinimo=colMin;
    }

    @Override
    public Component getTableCellRendererComponent ( JTable table, Object value, boolean selected, boolean focused, int row, int column )
    {        
        setBackground(Color.white);//color de fondo
        table.setForeground(Color.black);//color de texto
        //Si las existencias son menores que el mínimo, se cambia el color de fondo a rojo
            double existencias=Double.valueOf(String.valueOf(table.getValueAt(row, columnaExistencias)));
            double minimo=Double.valueOf(String.valueOf(table.getValueAt(row, columnaMinimo)));
            if (existencias<minimo){
                setBackground(Color.red);
            }else{
                setBackground(Color.green);
            }
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
 }

}