package vista;

import controlador.VueloController;
import modelo.Asiento;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.border.EmptyBorder;

public class VueloVista extends JFrame {
    private VueloController controlador;
    private JButton[][] asientos;
    private JComboBox<String> claseCombo;
    private JComboBox<String> ubicacionCombo;
    private JTextField nombreField;
    private JTextField cedulaField;

    public VueloVista() {
        setTitle("Sistema de Reservas - Avión Maravilla");
        setSize(800, 800); // Increased height to accommodate new layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel superior para el título
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 144, 255));
        JLabel titleLabel = new JLabel("Avión Maravilla - Sistema de Reservas");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Panel de interacción (datos + botones)
        JPanel interactionPanel = new JPanel(new BorderLayout(10, 10));

        // Panel de datos
        JPanel datosPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        datosPanel.setBorder(BorderFactory.createTitledBorder("Datos del Pasajero"));

        nombreField = new JTextField();
        cedulaField = new JTextField();
        claseCombo = new JComboBox<>(new String[]{"ejecutiva", "economica"});
        ubicacionCombo = new JComboBox<>(new String[]{"ventana", "pasillo", "centro"});

        datosPanel.add(new JLabel("Nombre:"));
        datosPanel.add(nombreField);
        datosPanel.add(new JLabel("Cédula:"));
        datosPanel.add(cedulaField);
        datosPanel.add(new JLabel("Clase:"));
        datosPanel.add(claseCombo);
        datosPanel.add(new JLabel("Ubicación:"));
        datosPanel.add(ubicacionCombo);

        // Panel de botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton reservarBtn = new JButton("Reservar");
        JButton buscarBtn = new JButton("Buscar Reserva");
        JButton eliminarBtn = new JButton("Eliminar Reserva");

        reservarBtn.addActionListener(e -> realizarReserva());
        buscarBtn.addActionListener(e -> buscarPorCedula());
        eliminarBtn.addActionListener(e -> eliminarReserva());

        botonesPanel.add(reservarBtn);
        botonesPanel.add(buscarBtn);
        botonesPanel.add(eliminarBtn);

        // Agregar datos y botones al panel de interacción
        interactionPanel.add(datosPanel, BorderLayout.CENTER);
        interactionPanel.add(botonesPanel, BorderLayout.SOUTH);

        // Panel de asientos con fondo
        AsientosConFondoPanel asientosPanel = new AsientosConFondoPanel();
        asientosPanel.setLayout(new GridBagLayout());
        asientosPanel.setBorder(BorderFactory.createTitledBorder("Mapa de Asientos"));

        // Inicializar matriz de asientos
        asientos = new JButton[9][6];
        GridBagConstraints gbc = new GridBagConstraints();

        // Asientos ejecutivos (2 filas de 4)
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                JButton btn = new JButton(String.valueOf(i * 4 + j + 1));
                btn.setPreferredSize(new Dimension(50, 50));
                btn.setMargin(new Insets(2, 2, 2, 2));
                btn.setBackground(Color.YELLOW);
                
                gbc.gridx = j + 1;
                gbc.gridy = i + 1;
                gbc.insets = new Insets(5, 5, 5, 5);
                asientosPanel.add(btn, gbc);
                asientos[i][j] = btn;
            }
        }

        // Espacio entre clases
        gbc.gridy = 3;
        asientosPanel.add(new JLabel(" "), gbc);

        // Asientos económicos (7 filas de 6)
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                JButton btn = new JButton(String.valueOf(8 + i * 6 + j + 1));
                btn.setPreferredSize(new Dimension(50, 50));
                btn.setMargin(new Insets(2, 2, 2, 2));
                btn.setBackground(Color.CYAN);
                gbc.gridx = j;
                gbc.gridy = i + 4; // Aumentado para dejar más espacio
                gbc.insets = new Insets(5, 5, 5, 5);
                asientosPanel.add(btn, gbc);
                asientos[i + 2][j] = btn;
            }
        }

        // Agregar todos los paneles al panel principal
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(interactionPanel, BorderLayout.CENTER);
        mainPanel.add(asientosPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private class AsientosConFondoPanel extends JPanel {
        private Image imagen;

        public AsientosConFondoPanel() {
            URL imagenURL = getClass().getResource("/vista/volar.png");
            if (imagenURL != null) {
                imagen = new ImageIcon(imagenURL).getImage();
            } else {
                System.out.println("La imagen no se encuentra en la ruta especificada.");
            }
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagen != null) {
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 400);
        }
    }

    private void realizarReserva() {
        String nombre = nombreField.getText();
        String cedula = cedulaField.getText();
        String clase = (String) claseCombo.getSelectedItem();
        String ubicacion = (String) ubicacionCombo.getSelectedItem();

        if (nombre.isEmpty() || cedula.isEmpty()) {
            mostrarMensaje("Por favor complete todos los campos");
            return;
        }

        controlador.reservarAsiento(nombre, cedula, clase, ubicacion);
    }
    
    private void buscarPorCedula() {
        String cedula = JOptionPane.showInputDialog(
            this,
            "Ingrese la cédula del pasajero:",
            "Buscar Reserva",
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (cedula != null && !cedula.trim().isEmpty()) {
            Asiento asiento = controlador.buscarPorCedula(cedula);
            if (asiento != null) {
                String mensaje = String.format(
                    "Reserva encontrada:\nNombre: %s\nAsiento: %d\nClase: %s\nUbicación: %s",
                    asiento.getPasajeroNombre(),
                    asiento.getNumero(),
                    asiento.getClase(),
                    asiento.getUbicacion()
                );
                JOptionPane.showMessageDialog(this, mensaje, "Información de Reserva", JOptionPane.INFORMATION_MESSAGE);
            } else {
                mostrarMensaje("No se encontró ninguna reserva con esa cédula");
            }
        }
    }
    
    private void eliminarReserva() {
        String cedula = JOptionPane.showInputDialog(
            this,
            "Ingrese la cédula del pasajero para eliminar la reserva:",
            "Eliminar Reserva",
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (cedula != null && !cedula.trim().isEmpty()) {
            if (controlador.eliminarReserva(cedula)) {
                mostrarMensaje("Reserva eliminada exitosamente");
            } else {
                mostrarMensaje("No se encontró ninguna reserva con esa cédula");
            }
        }
    }
    
    public void actualizarVistaAsientos() {
        for (int i = 0; i < asientos.length; i++) {
            for (int j = 0; j < asientos[i].length; j++) {
                if (asientos[i][j] != null) {
                    int numeroAsiento = Integer.parseInt(asientos[i][j].getText());
                    boolean ocupado = controlador.esAsientoOcupado(numeroAsiento);
                    String clase = controlador.obtenerClaseAsiento(numeroAsiento);
                    
                    if (ocupado) {
                        asientos[i][j].setBackground(Color.RED);
                        asientos[i][j].setEnabled(false);
                    } else {
                        asientos[i][j].setEnabled(true);
                        if (clase.equals("ejecutiva")) {
                            asientos[i][j].setBackground(Color.YELLOW);
                        } else {
                            asientos[i][j].setBackground(Color.CYAN);
                        }
                    }
                }
            }
        }
    }
    
    public void mostrarTiquete(String nombre, String cedula, int numeroAsiento, 
                             String clase, String ubicacion, LocalDateTime horaSalida, 
                             LocalDateTime horaLlegada, double precio) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String mensaje = String.format("""
            ========= TIQUETE DE VUELO =========
            Pasajero: %s
            Cédula: %s
            Asiento: %d
            Clase: %s
            Ubicación: %s
            Hora de Salida: %s
            Hora de Llegada: %s
            Precio: $%.2f
            ===================================""",
            nombre, cedula, numeroAsiento, clase, ubicacion,
            horaSalida.format(formatter),
            horaLlegada.format(formatter),
            precio
        );
        
        JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Tiquete de Vuelo",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Aviso",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    public void setControlador(VueloController controlador) {
        this.controlador = controlador;
    }
}