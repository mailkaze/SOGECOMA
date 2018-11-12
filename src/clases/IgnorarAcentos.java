/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author Kaze
 */
public class IgnorarAcentos {
    public String sinAcentos(String Cadena){
        Cadena=Cadena.replaceAll("á", "a");
        Cadena=Cadena.replaceAll("é", "e");
        Cadena=Cadena.replaceAll("í", "i");
        Cadena=Cadena.replaceAll("ó", "o");
        Cadena=Cadena.replaceAll("ú", "u");
        return Cadena;
    }
}
