/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.interkont.cobra.planoperativo.client;

import com.google.gwt.i18n.client.Messages;

/**
 *
 * @author Daniela
 */
public interface GwtMensajes extends Messages {

    @DefaultMessage("Planificacion de proyectos")
    String planificacionpry();

    @DefaultMessage("Nombre del proyecto")
    String namepry();

    @DefaultMessage("//INFORMACIÓN BASICA")
    String infoBasica();

    @DefaultMessage("Fecha inicio")
    String fechaInicio();

    @DefaultMessage("Fecha fin")
    String fechaFin();

    @DefaultMessage("Entidad")
    String entidad();

    @DefaultMessage("*ANADIR ROLES Y ENTIDADES")
    String addRol();

    @DefaultMessage("Tipo recurso")
    String tipoRecurso();

    @DefaultMessage("General")
    String objGeneral();

    @DefaultMessage("OBJETIVOS")
    String objetivos();

    @DefaultMessage("Especifico")
    String objespecifico();

    @DefaultMessage("Seleccione Rubro")
    String selecRubro();

    @DefaultMessage("Monto aportado")
    String montAportado();

    @DefaultMessage("*META O PRODUCTO ESPERADO")
    String meta();

    @DefaultMessage("*MACROACTIVIDADES")
    String macro();

    @DefaultMessage("* Añadir Proyecto")
    String addPry();
}
