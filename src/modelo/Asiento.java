package modelo;

public class Asiento {
    private final int numero;
    private boolean ocupado;
    private final String clase; // "ejecutiva" o "economica"
    private final String ubicacion; // "ventana", "pasillo", "centro"
    private String pasajeroNombre;
    private String pasajeroCedula;
    
    public Asiento(int numero, String clase, String ubicacion) {
        this.numero = numero;
        this.clase = clase;
        this.ubicacion = ubicacion;
        this.ocupado = false;
    }
    
    // Getters y setters
    public int getNumero() { return numero; }
    public boolean isOcupado() { return ocupado; }
    public String getClase() { return clase; }
    public String getUbicacion() { return ubicacion; }
    public String getPasajeroNombre() { return pasajeroNombre; }
    public String getPasajeroCedula() { return pasajeroCedula; }
    
    public void setOcupado(boolean ocupado) { this.ocupado = ocupado; }
    public void setPasajeroNombre(String nombre) { this.pasajeroNombre = nombre; }
    public void setPasajeroCedula(String cedula) { this.pasajeroCedula = cedula; }
    
    public void ocupar(String nombre, String cedula) {
        this.ocupado = true;
        this.pasajeroNombre = nombre;
        this.pasajeroCedula = cedula;
    }
    
    public void desocupar() {
        this.ocupado = false;
        this.pasajeroNombre = null;
        this.pasajeroCedula = null;
    }
    
}