/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import javax.swing.JOptionPane;

/**
 *
 * @author Manuel
 */
public class ValidacionCampos {
    public Boolean validarCampos(String dato, int tipo){
        boolean correcto=false;
        if (dato.equals("")||dato.equals(null)){//si el campo no está vacío.
                        JOptionPane.showMessageDialog(null, "El dato no puede estar en blanco.");
        }else{
            switch (tipo){
                case 1://Integer
                    try{
                        Integer.parseInt(dato);
                        correcto=true;
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, "Número incorrecto.");
                        correcto=false;
                    }
                case 2://Double
                    try{
                        Double.parseDouble(dato);
                        correcto=true;
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, "Número incorrecto.");
                        correcto=false;
                    }
                case 3://hora
                    boolean formatoCorrecto=false;
                    for (int i=0;i<=dato.length()-1;i++){
                        if (dato.substring(i, i+1).equals(":")){
                            formatoCorrecto=true;
                            System.out.println("hora: "+dato.substring(0, i));
                            System.out.println("minuto: "+dato.substring(i+1));
                            try{
                                int hora=Integer.parseInt(dato.substring(0, i));
                                int minuto=Integer.parseInt(dato.substring(i+1));
                                if (hora>=0 && hora<=23 && minuto>=0 && minuto <=59){
                                    correcto=true;
                                }else{
                                    JOptionPane.showMessageDialog(null, "Hora incorrecta.");
                                    correcto=false;
                                }
                            }catch(Exception e){
                                JOptionPane.showMessageDialog(null, "Hora incorrecta.");
                                correcto=false;
                            }
                        }
                    }
                    if (!formatoCorrecto){
//                        JOptionPane.showMessageDialog(null, "Hora incorrecta 3.");
                        correcto=false;
                    }
                case 4:    
                case 0://String
                    //solo comprueba si está en blanco.
                    correcto=true;
            }
        }
        return correcto;
    }
}
