/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author Kaze
 */
public class FormatearTablas {
    public void formatearTablaTodosItems(){
        clases.SOGECOMA.ModeloTablaTodosItems.addColumn("Ítem");
        clases.SOGECOMA.ModeloTablaTodosItems.addColumn("Nombre del Ítem");
        clases.SOGECOMA.ModeloTablaTodosItems.addColumn("Material");
        clases.SOGECOMA.ModeloTablaTodosItems.addColumn("Unidad");
        clases.SOGECOMA.ModeloTablaTodosItems.addColumn("Entregado");
        clases.SOGECOMA.ModeloTablaTodosItems.addColumn("Total a Entregar");
        clases.SOGECOMA.ModeloTablaTodosItems.addColumn("Falta por Entregar");
        clases.SOGECOMA.ModeloTablaTodosItems.addColumn("Progreso");
    }
    public void formatearTablaBuscarBloque(){
        clases.SOGECOMA.ModeloTablaBuscarBloque.addColumn("Material");
        clases.SOGECOMA.ModeloTablaBuscarBloque.addColumn("Unidad");
        clases.SOGECOMA.ModeloTablaBuscarBloque.addColumn("Entregado");
        clases.SOGECOMA.ModeloTablaBuscarBloque.addColumn("Total a Entregar");
        clases.SOGECOMA.ModeloTablaBuscarBloque.addColumn("Falta por entregar");
        clases.SOGECOMA.ModeloTablaBuscarBloque.addColumn("Progreso");
    }
    public void formatearTablaBuscarItem(){
        clases.SOGECOMA.ModeloTablaBuscarItem.addColumn("Material");
        clases.SOGECOMA.ModeloTablaBuscarItem.addColumn("Unidad");
        clases.SOGECOMA.ModeloTablaBuscarItem.addColumn("Entregado");
        clases.SOGECOMA.ModeloTablaBuscarItem.addColumn("Total a Entregar");
        clases.SOGECOMA.ModeloTablaBuscarItem.addColumn("Falta por entregar");
        clases.SOGECOMA.ModeloTablaBuscarItem.addColumn("Progreso");
    }
    public void formatearTablaBuscarMaterial(){
        clases.SOGECOMA.ModeloTablaBuscarMaterial.addColumn("Bloque");
        clases.SOGECOMA.ModeloTablaBuscarMaterial.addColumn("Ítem");
        clases.SOGECOMA.ModeloTablaBuscarMaterial.addColumn("Nombre del Ítem");
        clases.SOGECOMA.ModeloTablaBuscarMaterial.addColumn("Material");
        clases.SOGECOMA.ModeloTablaBuscarMaterial.addColumn("Unidad");
        clases.SOGECOMA.ModeloTablaBuscarMaterial.addColumn("Entregado");
        clases.SOGECOMA.ModeloTablaBuscarMaterial.addColumn("Total a Entregar");
        clases.SOGECOMA.ModeloTablaBuscarMaterial.addColumn("Falta por Entregar");
        clases.SOGECOMA.ModeloTablaBuscarMaterial.addColumn("Progreso");
    }
    public void formatearTablaComparativaConsumo(){
        clases.SOGECOMA.ModeloTablaComparativaConsumo.addColumn("Bloque");
        clases.SOGECOMA.ModeloTablaComparativaConsumo.addColumn("Material");
        clases.SOGECOMA.ModeloTablaComparativaConsumo.addColumn("Unidad");
        clases.SOGECOMA.ModeloTablaComparativaConsumo.addColumn("Entregado");
        clases.SOGECOMA.ModeloTablaComparativaConsumo.addColumn("Total a Entregar");
        clases.SOGECOMA.ModeloTablaComparativaConsumo.addColumn("Falta por entregar");
        clases.SOGECOMA.ModeloTablaComparativaConsumo.addColumn("Progreso");
    }
    public void formatearTablaBuscarItemEnTodos(){
        clases.SOGECOMA.ModeloTablaBuscarItem.addColumn("Material");
        clases.SOGECOMA.ModeloTablaBuscarItem.addColumn("Unidad");
        clases.SOGECOMA.ModeloTablaBuscarItem.addColumn("Entregado");
        clases.SOGECOMA.ModeloTablaBuscarItem.addColumn("Total a Entregar");
        clases.SOGECOMA.ModeloTablaBuscarItem.addColumn("Falta por entregar");
        clases.SOGECOMA.ModeloTablaBuscarItem.addColumn("Progreso");
    }
}
