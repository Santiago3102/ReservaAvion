package modelo;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Vuelo {
    private final ArrayList<Asiento> asientos;
    private LocalDateTime horaSalida;
    private LocalDateTime horaLlegada;
    private final double precioEjecutiva;
    private final double precioEconomica;
    
    public Vuelo() {
        asientos = new ArrayList<>();
        inicializarAsientos();
        precioEjecutiva = 300000;
        precioEconomica = 150000;
        horaSalida = LocalDateTime.now().plusHours(2);
        horaLlegada = LocalDateTime.now().plusHours(4);
    }
    
    private void inicializarAsientos() {
        // Asientos ejecutivos (8 asientos, 2 filas de 4)
        for (int i = 0; i < 8; i++) {
            String tipo = (i % 4 == 0 || i % 4 == 3) ? 
                "ejecutiva_ventana" : "ejecutiva_pasillo";
            asientos.add(AsientoFactory.getAsiento(tipo, i + 1));
        }
        
        // Asientos económicos (42 asientos, 7 filas de 6)
        for (int i = 8; i < 50; i++) {
            int pos = (i - 8) % 6;
            String tipo;
            if (pos == 0 || pos == 5) {
                tipo = "economica_ventana";
            } else if (pos == 2 || pos == 3) {
                tipo = "economica_pasillo";
            } else {
                tipo = "economica_centro";
            }
            asientos.add(AsientoFactory.getAsiento(tipo, i + 1));
        }
    }
    
    public boolean reservarAsiento(int numero, String nombre, String cedula) {
        for (Asiento asiento : asientos) {
            if (asiento.getNumero() == numero && !asiento.isOcupado()) {
                asiento.ocupar(nombre, cedula);
                return true;
            }
        }
        return false;
    }
    
    public Asiento buscarAsientoPorCedula(String cedula) {
        for (Asiento asiento : asientos) {
            if (asiento.isOcupado() && 
                asiento.getPasajeroCedula() != null && 
                asiento.getPasajeroCedula().equals(cedula)) {
                return asiento;
            }
        }
        return null;
    }
    
    public boolean eliminarReservaPorCedula(String cedula) {
        Asiento asiento = buscarAsientoPorCedula(cedula);
        if (asiento != null) {
            asiento.desocupar();
            return true;
        }
        return false;
    }
    
    public ArrayList<Asiento> getAsientosDisponibles(String clase, String ubicacion) {
        ArrayList<Asiento> disponibles = new ArrayList<>();
        for (Asiento asiento : asientos) {
            if (!asiento.isOcupado() && 
                asiento.getClase().equals(clase) && 
                asiento.getUbicacion().equals(ubicacion)) {
                disponibles.add(asiento);
            }
        }
        return disponibles;
    }
    
    public double calcularPrecio(int numeroAsiento) {
        Asiento asiento = obtenerAsiento(numeroAsiento);
        if (asiento == null) return 0;
        
        double precio = asiento.getClase().equals("ejecutiva") ? precioEjecutiva : precioEconomica;
        
        // Promoción nocturna
        LocalDateTime ahora = LocalDateTime.now();
        if (ahora.getHour() >= 22 || ahora.getHour() <= 4) {
            precio *= 0.8; // 20% descuento
        }
        
        return precio;
    }
    
    public Asiento obtenerAsiento(int numero) {
        for (Asiento asiento : asientos) {
            if (asiento.getNumero() == numero) return asiento;
        }
        return null;
    }
    
    // Getters y setters para horarios
    public LocalDateTime getHoraSalida() { return horaSalida; }
    public LocalDateTime getHoraLlegada() { return horaLlegada; }
    public void setHoraSalida(LocalDateTime horaSalida) { this.horaSalida = horaSalida; }
    public void setHoraLlegada(LocalDateTime horaLlegada) { this.horaLlegada = horaLlegada; }
}