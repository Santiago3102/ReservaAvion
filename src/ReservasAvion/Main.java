package main;

import modelo.Vuelo;
import vista.VueloVista;
import controlador.VueloController;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Vuelo modelo = new Vuelo();
            VueloVista vista = new VueloVista();
            VueloController controlador = new VueloController(modelo, vista);
            vista.setControlador(controlador);
            vista.setVisible(true);
        });
    }
}