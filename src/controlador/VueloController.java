package controlador;

import modelo.Vuelo;
import modelo.Asiento;
import vista.VueloVista;
import java.util.ArrayList;

public class VueloController {
    private final Vuelo modelo;
    private final VueloVista vista;
    
    public VueloController(Vuelo modelo, VueloVista vista) {
        this.modelo = modelo;
        this.vista = vista;
    }
    
    public void reservarAsiento(String nombre, String cedula, String clase, String ubicacion) {
        ArrayList<Asiento> disponibles = modelo.getAsientosDisponibles(clase, ubicacion);
        
        if (disponibles.isEmpty()) {
            vista.mostrarMensaje("No hay asientos disponibles con esas características");
            return;
        }
        
        Asiento asiento = disponibles.get(0);
        if (modelo.reservarAsiento(asiento.getNumero(), nombre, cedula)) {
            double precio = modelo.calcularPrecio(asiento.getNumero());
            vista.mostrarTiquete(nombre, cedula, asiento.getNumero(), 
                               clase, ubicacion, modelo.getHoraSalida(), 
                               modelo.getHoraLlegada(), precio);
            vista.actualizarVistaAsientos();
        } else {
            vista.mostrarMensaje("Error al realizar la reserva");
        }
    }
    
    public boolean eliminarReserva(String cedula) {
        boolean eliminado = modelo.eliminarReservaPorCedula(cedula);
        if (eliminado) {
            vista.actualizarVistaAsientos();
        }
        return eliminado;
    }
    
    public Asiento buscarPorCedula(String cedula) {
        return modelo.buscarAsientoPorCedula(cedula);
    }
    
    public boolean esAsientoOcupado(int numero) {
        Asiento asiento = modelo.obtenerAsiento(numero);
        return asiento != null && asiento.isOcupado();
    }
    
    public String obtenerClaseAsiento(int numero) {
        Asiento asiento = modelo.obtenerAsiento(numero);
        return asiento != null ? asiento.getClase() : "";
    }
}
