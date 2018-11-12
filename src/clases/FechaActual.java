/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.Calendar;

/**
 *
 * @author Kaze
 */
public class FechaActual {
    public String obtenerFecha(){
        Calendar fecha=Calendar.getInstance();
        String fechaSalida=String.valueOf(fecha.get(Calendar.DAY_OF_MONTH))+
                           "/"+String.valueOf(1+fecha.get(Calendar.MONTH))+
                           "/"+String.valueOf(fecha.get(Calendar.YEAR));
        return fechaSalida;
    }
}
