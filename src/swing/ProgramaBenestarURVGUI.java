package swing;

import javax.swing.*;
import java.awt.*;

import Data.*;                
import programa.GestorInscripcionsFitxer; 
import programa.Persistencia;

public class ProgramaBenestarURVGUI {

    private static final String FITXER_INSCRIPCIONS = "fitxers/inscripcions.ser";

    public static void main(String[] args) {

        // Carregar dades
        LlistaActivitats llistaActivitats = new LlistaActivitats(100);
        LlistaUsuaris llistaUsuaris = new LlistaUsuaris();
        Llistainscripcions llistaInscripcions;

        Persistencia.carregarActivitats(llistaActivitats);
        Persistencia.carregarUsuaris(llistaUsuaris);

        // Carregar inscripcions serialitzades 
        try {
            llistaInscripcions = GestorInscripcionsFitxer.carregar(FITXER_INSCRIPCIONS, 100);
        } catch (Exception e) {
            llistaInscripcions = new Llistainscripcions(100);
        }

        // Data inicial: setembre 2025 (on hi ha activitats)
        Data avui = new Data(1, 9, 2025);

        Llistainscripcions finalIns = llistaInscripcions;
        SwingUtilities.invokeLater(() -> {
            FinestraBenestar f = new FinestraBenestar(avui, llistaActivitats, llistaUsuaris, finalIns);
            f.setVisible(true);
        });
    }

    // --- mateixos exemples que a consola (placeholder) ---
}

// Ventana principal
class FinestraBenestar extends JFrame {

    private Data avui;
    private final LlistaActivitats activitats;
    @SuppressWarnings("unused")
    private final LlistaUsuaris usuaris;
    @SuppressWarnings("unused")
    private final Llistainscripcions inscripcions;

    private JComboBox<String> comboMes;
    private JComboBox<String> comboTipus;
    private JPanel panelCalendari;

    private DefaultListModel<String> modelLlista;
    private JList<String> llistaActDia;
    private JTextArea areaDetall;

    private int mesSeleccionat;
    private int anySeleccionat;

    public FinestraBenestar(Data avui, LlistaActivitats activitats, LlistaUsuaris usuaris, Llistainscripcions inscripcions) {
        this.avui = avui;
        this.activitats = activitats;
        this.usuaris = usuaris;
        this.inscripcions = inscripcions;

        setTitle("Benestar URV - Calendari");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Top (mes + filtre)
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboMes = new JComboBox<>(new String[]{
                "01/2025","02/2025","03/2025","04/2025","05/2025","06/2025",
                "07/2025","08/2025","09/2025","10/2025","11/2025","12/2025"
        });
        comboTipus = new JComboBox<>(new String[]{"Totes", "ActivitatDia", "ActivitatPeriodica", "ActivitatOnline"});

        top.add(new JLabel("Mes:"));
        top.add(comboMes);
        top.add(new JLabel("Tipus:"));
        top.add(comboTipus);

        add(top, BorderLayout.NORTH);

        // Centro (calendario)
        panelCalendari = new JPanel(new GridLayout(0, 7, 6, 6));
        add(panelCalendari, BorderLayout.CENTER);

        // Derecha (lista + detalle)
        JPanel dreta = new JPanel(new BorderLayout(6, 6));
        modelLlista = new DefaultListModel<>();
        llistaActDia = new JList<>(modelLlista);
        areaDetall = new JTextArea();
        areaDetall.setEditable(false);

        dreta.add(new JLabel("Activitats del dia:"), BorderLayout.NORTH);
        dreta.add(new JScrollPane(llistaActDia), BorderLayout.CENTER);
        dreta.add(new JScrollPane(areaDetall), BorderLayout.SOUTH);
        dreta.setPreferredSize(new Dimension(350, 600));

        add(dreta, BorderLayout.EAST);

        // Estado inicial: usar mes/año de "avui"
        mesSeleccionat = avui.getMes();
        anySeleccionat = avui.getAny();
        comboMes.setSelectedIndex(mesSeleccionat - 1);

        // Eventos
        comboMes.addActionListener(e -> {
            String val = (String) comboMes.getSelectedItem(); // "MM/YYYY"
            mesSeleccionat = Integer.parseInt(val.substring(0, 2));
            anySeleccionat = Integer.parseInt(val.substring(3));
            refrescarCalendari();
        });

        comboTipus.addActionListener(e -> refrescarCalendari());

        llistaActDia.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String nom = llistaActDia.getSelectedValue();
                if (nom != null) {
                    Activitat a = activitats.cercar(nom);
                    if (a != null) areaDetall.setText(detallActivitat(a));
                }
            }
        });

        refrescarCalendari();
    }

    private void refrescarCalendari() {
        panelCalendari.removeAll();
        modelLlista.clear();
        areaDetall.setText("");

        // Cabecera días semana (simple)
        String[] dies = {"Dl","Dt","Dc","Dj","Dv","Ds","Dg"};
        for (int i = 0; i < 7; i++) {
            JLabel lab = new JLabel(dies[i], SwingConstants.CENTER);
            lab.setFont(lab.getFont().deriveFont(Font.BOLD));
            panelCalendari.add(lab);
        }

        int diesMes = diesDelMes(mesSeleccionat, anySeleccionat);

        // (Opcional) huecos para alinear día 1; si no quieres complicarte, pon 0
        int offset = diaSetmana(new Data(1, mesSeleccionat, anySeleccionat)); // 0=dl .. 6=dg
        for (int i = 0; i < offset; i++) {
            panelCalendari.add(new JLabel(""));
        }

        for (int d = 1; d <= diesMes; d++) {
            Data dia = new Data(d, mesSeleccionat, anySeleccionat);

            JButton b = new JButton(String.valueOf(d));

            boolean teAlguna = hiHaActivitatsEnDia(dia);
            
            if (teAlguna) {
                b.setFont(b.getFont().deriveFont(Font.BOLD, 14f));
                b.setForeground(Color.BLUE);
                b.setToolTipText("Hi ha activitats aquest dia");
            } else {
                b.setEnabled(true);
            }

            b.addActionListener(e -> mostrarDia(dia));
            panelCalendari.add(b);
        }

        panelCalendari.revalidate();
        panelCalendari.repaint();
    }

    private boolean hiHaActivitatsEnDia(Data dia) {
        String filtre = (String) comboTipus.getSelectedItem();

        // Si tienes el método, mejor:
        LlistaActivitats actives = activitats.activitatsActivesElDia(dia);

        for (int i = 0; i < actives.getSize(); i++) {
            Activitat a = actives.get(i);
            if (filtre.equals("Totes") || a.getClass().getSimpleName().equals(filtre)) {
                return true;
            }
        }
        return false;
    }

    private void mostrarDia(Data dia) {
        modelLlista.clear();
        areaDetall.setText("Dia seleccionat: " + dia + "\n");

        String filtre = (String) comboTipus.getSelectedItem();
        LlistaActivitats actives = activitats.activitatsActivesElDia(dia);

        for (int i = 0; i < actives.getSize(); i++) {
            Activitat a = actives.get(i);
            if (filtre.equals("Totes") || a.getClass().getSimpleName().equals(filtre)) {
                modelLlista.addElement(a.getNom());
            }
        }

        if (modelLlista.isEmpty()) {
            areaDetall.append("\n(cap activitat)");
        }
    }

    private String detallActivitat(Activitat a) {
        StringBuilder sb = new StringBuilder();
        sb.append("---- Detall activitat ----\n");
        sb.append("Nom: ").append(a.getNom()).append("\n");
        sb.append("Inscripció: ").append(a.getDataInscripcioInicial())
          .append(" - ").append(a.getDataInscripcioFinal()).append("\n");
        sb.append("Inici: ").append(a.getDataIniciActivitat()).append("\n");
        sb.append("Fi: ").append(a.getDataFiActivitat()).append("\n");
        sb.append("Tipus: ").append(a.getClass().getSimpleName()).append("\n");

        if (a instanceof ActivitatDia) {
            ActivitatDia ad = (ActivitatDia) a;
            sb.append("\n--- ActivitatDia ---\n");
            sb.append("Data: ").append(ad.getData()).append("\n");
            sb.append("Ciutat: ").append(ad.getCiutat()).append("\n");
            sb.append("Places: ").append(ad.getLimitPlaces()).append("\n");
            sb.append("Preu: ").append(ad.getPreu()).append("\n");
        }

        return sb.toString();
    }

    // ====== utilitats Data  ======

    private int diesDelMes(int mes, int any) {
        switch (mes) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12: return 31;
            case 4: case 6: case 9: case 11: return 30;
            case 2:
                return (esBixest(any) ? 29 : 28);
            default:
                return 30;
        }
    }

    private boolean esBixest(int any) {
        if (any % 400 == 0) return true;
        if (any % 100 == 0) return false;
        return any % 4 == 0;
    }

    // 0=dl .. 6=dg (Zeller adaptat)
    private int diaSetmana(Data d) {
        int q = d.getDia();
        int m = d.getMes();
        int y = d.getAny();

        if (m < 3) {
            m += 12;
            y -= 1;
        }
        int K = y % 100;
        int J = y / 100;

        int h = (q + (13*(m+1))/5 + K + (K/4) + (J/4) + (5*J)) % 7;
        // h: 0=ds,1=dg,2=dl,3=dt,4=dc,5=dj,6=dv
        int[] map = {5,6,0,1,2,3,4}; // -> dl=0..dg=6
        return map[h];
    }
}
