package modelo;

import java.util.HashMap;
import java.util.Map;

public class AsientoFactory {
    private static final Map<String, AsientoPrototype> prototipos = new HashMap<>();
    
    static {
        // Inicializar prototipos de asientos
        prototipos.put("ejecutiva_ventana", new Asiento(0, "ejecutiva", "ventana"));
        prototipos.put("ejecutiva_pasillo", new Asiento(0, "ejecutiva", "pasillo"));
        prototipos.put("economica_ventana", new Asiento(0, "economica", "ventana"));
        prototipos.put("economica_pasillo", new Asiento(0, "economica", "pasillo"));
        prototipos.put("economica_centro", new Asiento(0, "economica", "centro"));
    }
    
    public static Asiento getAsiento(String tipo, int numero) {
        AsientoPrototype prototipo = prototipos.get(tipo);
        if (prototipo == null) {
            throw new IllegalArgumentException("Tipo de asiento no válido: " + tipo);
        }
        Asiento nuevoAsiento = (Asiento) prototipo.clonar();
        nuevoAsiento.setNumero(numero);
        return nuevoAsiento;
    }
}
