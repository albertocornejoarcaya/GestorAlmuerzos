package Gui;

import modelo.*;
import repositorio.*;
import logica.*;
import excepcion.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

/**
 * GUI principal que integra Personas, Fundos, Menús y Solicitudes.
 * Cumple con los requisitos académicos de registro, búsqueda, eliminación
 * y manejo de deshacer/rehacer para la pestaña de Personas,
 * además de mostrar y atender la cola de solicitudes.
 */
public class FormularioPrincipal extends JFrame {

    /* ------------------------- Repositorios y lógica ------------------------- */
    private final RepositorioPersona repoPersonas         = new RepositorioPersona();
    private final RepositorioFundos  repoFundos            = new RepositorioFundos();
    private final RepositorioMenus   repoMenus             = new RepositorioMenus();
    private final RepositorioSolicitudesAlmuerzo repoSolicitudes = new RepositorioSolicitudesAlmuerzo();

    private final ColaAtencion<SolicitudAlmuerzo> colaAtencion = new ColaAtencion<>();
    private final GestorUndoRedo gestorUndoRedo = new GestorUndoRedo();

    /* ------------------------- Modelos de tablas ---------------------------- */
    private DefaultTableModel modeloPersonas;
    private DefaultTableModel modeloFundos;
    private DefaultTableModel modeloMenus;
    private DefaultTableModel modeloSolicitudes;

    /* ------------------------- Controles de pestaña Solicitudes -------------- */
    private JComboBox<String> cbxDni;
    private JComboBox<String> cbxFundo;
    private JComboBox<String> cbxMenu;
    private JTextField        txtMenuNombre;

    /* ------------------------------------------------------------------------ */
    public FormularioPrincipal() {
        setTitle("Gestión de Solicitudes de Almuerzos – Danper Trujillo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(920, 620);
        setLocationRelativeTo(null);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("Personas",     crearPanelPersonas());
        pestañas.addTab("Fundos",       crearPanelFundos());
        pestañas.addTab("Menús",        crearPanelMenus());
        pestañas.addTab("Solicitudes",  crearPanelSolicitudes());

        getContentPane().add(pestañas, BorderLayout.CENTER);
    }

    /* ====================================================================== */
    /*                               PERSONAS                                */
    /* ====================================================================== */
    private JPanel crearPanelPersonas() {
        JPanel panel = new JPanel(new BorderLayout());

        /* -------- Formulario -------- */
        JPanel form = new JPanel(new GridLayout(3, 2));
        JTextField txtDni    = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtArea   = new JTextField();

        form.add(new JLabel("DNI:"));      form.add(txtDni);
        form.add(new JLabel("Nombre:"));   form.add(txtNombre);
        form.add(new JLabel("Área:"));     form.add(txtArea);

        /* -------- Botones -------- */
        JPanel botones = new JPanel(new FlowLayout());
        JButton btnAgregar   = new JButton("Agregar");
        JButton btnEliminar  = new JButton("Eliminar");
        JButton btnBuscar    = new JButton("Buscar");
        JButton btnDeshacer  = new JButton("Deshacer");
        JButton btnRehacer   = new JButton("Rehacer");
        botones.add(btnAgregar);
        botones.add(btnEliminar);
        botones.add(btnBuscar);
        botones.add(btnDeshacer);
        botones.add(btnRehacer);

        /* -------- Tabla -------- */
        modeloPersonas = new DefaultTableModel(new String[]{"DNI", "Nombre", "Área"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modeloPersonas);
        JScrollPane sp = new JScrollPane(tabla);

        /* -------- Acciones -------- */
        btnAgregar.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String dni = txtDni.getText().trim();
                String nom = txtNombre.getText().trim();
                String area= txtArea.getText().trim();

                if(dni.isEmpty()||nom.isEmpty()||area.isEmpty()) {
                    JOptionPane.showMessageDialog(FormularioPrincipal.this,"Complete todos los campos.");
                    return;
                }
                if(repoPersonas.buscarPorDni(dni).isPresent()) {
                    JOptionPane.showMessageDialog(FormularioPrincipal.this,"DNI ya registrado.");
                    return;
                }

                Persona p = new Persona(dni, nom, area);
                Runnable hacer = new Runnable() {
                    @Override public void run() {
                        repoPersonas.agregar(p);
                        modeloPersonas.addRow(new Object[]{dni, nom, area});
                        actualizarCombosSolicitudes();
                    }
                };
                Runnable deshacer = new Runnable() {
                    @Override public void run() {
                        repoPersonas.eliminar(dni);
                        for(int i=0;i<modeloPersonas.getRowCount();i++) {
                            if(dni.equals(modeloPersonas.getValueAt(i,0))) {
                                modeloPersonas.removeRow(i);
                                break;
                            }
                        }
                        actualizarCombosSolicitudes();
                    }
                };
                gestorUndoRedo.ejecutar(hacer,deshacer);
                actualizarEstadoBotones(btnDeshacer,btnRehacer);
                txtDni.setText(""); txtNombre.setText(""); txtArea.setText("");
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String dni = txtDni.getText().trim();
                Optional<Persona> per = repoPersonas.buscarPorDni(dni);
                if(per.isEmpty()) {
                    JOptionPane.showMessageDialog(FormularioPrincipal.this,"No existe el DNI ingresado.");
                    return;
                }
                Persona p = per.get();
                Object[] fila = {p.getDni(), p.getNombreCompleto(), p.getArea()};
                Runnable hacer = new Runnable() {
                    @Override public void run() {
                        repoPersonas.eliminar(dni);
                        for(int i=0;i<modeloPersonas.getRowCount();i++) {
                            if(dni.equals(modeloPersonas.getValueAt(i,0))) {
                                modeloPersonas.removeRow(i);
                                break;
                            }
                        }
                        actualizarCombosSolicitudes();
                    }
                };
                Runnable deshacer = new Runnable() {
                    @Override public void run() {
                        repoPersonas.agregar(p);
                        modeloPersonas.addRow(fila);
                        actualizarCombosSolicitudes();
                    }
                };
                gestorUndoRedo.ejecutar(hacer, deshacer);
                actualizarEstadoBotones(btnDeshacer,btnRehacer);
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                Optional<Persona> per = repoPersonas.buscarPorDni(txtDni.getText().trim());
                per.ifPresent(persona -> JOptionPane.showMessageDialog(FormularioPrincipal.this, persona.toString()) );
            }
        });

        btnDeshacer.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                try {
                    gestorUndoRedo.deshacer();
                    actualizarEstadoBotones(btnDeshacer,btnRehacer);
                } catch (ExcepcionPilaVacia ex) {
                    JOptionPane.showMessageDialog(FormularioPrincipal.this, ex.getMessage());
                }
            }
        });
        btnRehacer.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                try {
                    gestorUndoRedo.rehacer();
                    actualizarEstadoBotones(btnDeshacer,btnRehacer);
                } catch (ExcepcionPilaVacia ex) {
                    JOptionPane.showMessageDialog(FormularioPrincipal.this, ex.getMessage());
                }
            }
        });
        actualizarEstadoBotones(btnDeshacer,btnRehacer);

        panel.add(form, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        panel.add(sp, BorderLayout.SOUTH);
        return panel;
    }

    /* ====================================================================== */
    /*                                 FUNDOS                                */
    /* ====================================================================== */
    private JPanel crearPanelFundos() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(2, 2));
        JTextField txtNombre = new JTextField();
        JTextField txtUbic   = new JTextField();

        form.add(new JLabel("Nombre:"));    form.add(txtNombre);
        form.add(new JLabel("Ubicación:")); form.add(txtUbic);

        JPanel botones = new JPanel(new FlowLayout());
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEliminar= new JButton("Eliminar");
        JButton btnBuscar  = new JButton("Buscar");
        botones.add(btnAgregar); botones.add(btnEliminar); botones.add(btnBuscar);

        modeloFundos = new DefaultTableModel(new String[]{"Nombre", "Ubicación"},0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        JTable tabla = new JTable(modeloFundos);
        JScrollPane sp = new JScrollPane(tabla);

        /* cargar inicial */
        for(Fundo f: repoFundos.todos()) {
            modeloFundos.addRow(new Object[]{f.getNombre(), f.getUbicacion()});
        }

        btnAgregar.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String nom = txtNombre.getText().trim();
                String ubi = txtUbic.getText().trim();
                if(nom.isEmpty()||ubi.isEmpty()) {
                    JOptionPane.showMessageDialog(FormularioPrincipal.this,"Complete ambos campos.");
                    return;
                }
                Fundo f = new Fundo(nom, nom, ubi);
                repoFundos.agregar(f);
                modeloFundos.addRow(new Object[]{nom.toUpperCase(), ubi.toUpperCase()});
                actualizarCombosSolicitudes();
                txtNombre.setText(""); txtUbic.setText("");
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String nom = txtNombre.getText().trim();
                Optional<Fundo> fun = repoFundos.buscarPorNombre(nom);
                if(fun.isEmpty()) {
                    JOptionPane.showMessageDialog(FormularioPrincipal.this,"No existe ese fundo.");
                    return;
                }
                repoFundos.eliminar(fun.get());
                for(int i=0;i<modeloFundos.getRowCount();i++) {
                    if(nom.equalsIgnoreCase(modeloFundos.getValueAt(i,0).toString())) {
                        modeloFundos.removeRow(i);
                        break;
                    }
                }
                actualizarCombosSolicitudes();
            }
        });
        btnBuscar.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                Optional<Fundo> fun = repoFundos.buscarPorNombre(txtNombre.getText().trim());
                fun.ifPresent(f -> JOptionPane.showMessageDialog(FormularioPrincipal.this, f.toString()) );
            }
        });

        panel.add(form, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        panel.add(sp, BorderLayout.SOUTH);
        return panel;
    }

    /* ====================================================================== */
    /*                                  MENÚS                                */
    /* ====================================================================== */
    private JPanel crearPanelMenus() {
        JPanel panel = new JPanel(null);

        JPanel form = new JPanel(new GridLayout(4,2));
        form.setBounds(0,0,880,100);
        JTextField txtId          = new JTextField();
        JTextField txtDescripcion = new JTextField();
        JComboBox<TipoMenu> cbxTipo = new JComboBox<>(TipoMenu.values());
        JTextField txtPrecio      = new JTextField();

        form.add(new JLabel("ID:"));           form.add(txtId);
        form.add(new JLabel("Descripción:"));  form.add(txtDescripcion);
        form.add(new JLabel("Tipo:"));         form.add(cbxTipo);
        form.add(new JLabel("Precio:"));       form.add(txtPrecio);

        JPanel botones = new JPanel(new FlowLayout());
        botones.setBounds(0,105,880,35);
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEliminar= new JButton("Eliminar");
        JButton btnBuscar  = new JButton("Buscar");
        botones.add(btnAgregar); botones.add(btnEliminar); botones.add(btnBuscar);

        modeloMenus = new DefaultTableModel(new String[]{"ID","Descripción","Tipo","Precio"},0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        JTable tabla = new JTable(modeloMenus);
        JScrollPane sp = new JScrollPane(tabla);
        sp.setBounds(0,145,880,430);

        btnAgregar.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String id = txtId.getText().trim();
                String des= txtDescripcion.getText().trim();
                String pre= txtPrecio.getText().trim();
                if(id.isEmpty()||des.isEmpty()||pre.isEmpty()) {
                    JOptionPane.showMessageDialog(FormularioPrincipal.this,"Complete todos los campos.");
                    return;
                }
                double precio;
                try { precio = Double.parseDouble(pre); }
                catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(FormularioPrincipal.this,"Precio inválido.");
                    return;
                }
                Menus m = new Menus(id, des, (TipoMenu)cbxTipo.getSelectedItem(), precio);
                repoMenus.agregar(m);
                modeloMenus.addRow(new Object[]{id, des, m.getTipo(), precio});
                actualizarCombosSolicitudes();
                txtId.setText(""); txtDescripcion.setText(""); txtPrecio.setText("");
            }
        });
        btnEliminar.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String id = txtId.getText().trim();
                Optional<Menus> men = repoMenus.buscarPorId(id);
                if(men.isEmpty()) {
                    JOptionPane.showMessageDialog(FormularioPrincipal.this,"No existe ese ID.");
                    return;
                }
                repoMenus.eliminar(men.get());
                for(int i=0;i<modeloMenus.getRowCount();i++) {
                    if(id.equals(modeloMenus.getValueAt(i,0))) {
                        modeloMenus.removeRow(i);
                        break;
                    }
                }
                actualizarCombosSolicitudes();
            }
        });
        btnBuscar.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                Optional<Menus> men = repoMenus.buscarPorId(txtId.getText().trim());
                men.ifPresent(m -> JOptionPane.showMessageDialog(FormularioPrincipal.this, m.toString()) );
            }
        });

        panel.add(form);
        panel.add(botones);
        panel.add(sp);
        return panel;
    }

    /* ====================================================================== */
    /*                              SOLICITUDES                              */
    /* ====================================================================== */
    private JPanel crearPanelSolicitudes() {
        JPanel panel = new JPanel(null);

        JLabel lblDni   = new JLabel("DNI Persona:");   lblDni.setBounds(40,20,120,25);
        JLabel lblFundo = new JLabel("Nombre Fundo:");  lblFundo.setBounds(40,60,120,25);
        JLabel lblMenu  = new JLabel("ID Menú:");       lblMenu.setBounds(40,100,120,25);
        JLabel lblNom   = new JLabel("Nombre Menú:");   lblNom.setBounds(40,140,120,25);

        cbxDni   = new JComboBox<>(); cbxDni.setBounds(170,20,180,25);
        cbxFundo = new JComboBox<>(); cbxFundo.setBounds(170,60,180,25);
        cbxMenu  = new JComboBox<>(); cbxMenu.setBounds(170,100,100,25);
        txtMenuNombre = new JTextField(); txtMenuNombre.setEditable(false); txtMenuNombre.setBounds(170,140,300,25);

        actualizarCombosSolicitudes();

        cbxMenu.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String idSel = (String)cbxMenu.getSelectedItem();
                if(idSel!=null) {
                    Optional<Menus> m = repoMenus.buscarPorId(idSel);
                    if(m.isPresent()) txtMenuNombre.setText(m.get().getDescripcion());
                }
            }
        });

        JButton btnRegistrar = new JButton("Registrar Solicitud"); btnRegistrar.setBounds(170,190,180,30);
        JButton btnAtender   = new JButton("Atender Siguiente");  btnAtender .setBounds(370,190,180,30);

        modeloSolicitudes = new DefaultTableModel(new String[]{"DNI","Nombre","Fundo","Menú","Fecha‑Hora"},0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        JTable tabla = new JTable(modeloSolicitudes);
        JScrollPane sp = new JScrollPane(tabla); sp.setBounds(40,240,820,300);

        btnRegistrar.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String dniSel   = (String) cbxDni.getSelectedItem();
                String funSel   = (String) cbxFundo.getSelectedItem();
                String idMenu   = (String) cbxMenu.getSelectedItem();

                if(dniSel==null||funSel==null||idMenu==null) {
                    JOptionPane.showMessageDialog(FormularioPrincipal.this,"Seleccione DNI, Fundo y Menú.");
                    return;
                }
                Optional<Persona> opPer = repoPersonas.buscarPorDni(dniSel);
                Optional<Fundo>   opFun = repoFundos.buscarPorNombre(funSel);
                Optional<Menus>   opMen = repoMenus.buscarPorId(idMenu);

                if(opPer.isEmpty()||opFun.isEmpty()||opMen.isEmpty()) {
                    JOptionPane.showMessageDialog(FormularioPrincipal.this,"Datos inválidos.");
                    return;
                }
                SolicitudAlmuerzo sol = new SolicitudAlmuerzo(opPer.get(), opFun.get(), opMen.get(), LocalDate.now());
                repoSolicitudes.agregar(sol);
                colaAtencion.encolar(sol);

                modeloSolicitudes.addRow(new Object[]{
                        opPer.get().getDni(),
                        opPer.get().getNombreCompleto(),
                        opFun.get().getNombre(),
                        opMen.get().getDescripcion(),
                        LocalDate.now()+" "+LocalTime.now().withNano(0)
                });

                JOptionPane.showMessageDialog(FormularioPrincipal.this,"Solicitud registrada.");
                cbxDni.setSelectedIndex(-1); cbxFundo.setSelectedIndex(-1); cbxMenu.setSelectedIndex(-1); txtMenuNombre.setText("");
            }
        });

        btnAtender.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                try {
                    SolicitudAlmuerzo at = colaAtencion.atender();
                    JOptionPane.showMessageDialog(FormularioPrincipal.this,"Atendiendo: "+at.toString());
                    if(modeloSolicitudes.getRowCount()>0) modeloSolicitudes.removeRow(0);
                } catch (ExcepcionColaVacia ex) {
                    JOptionPane.showMessageDialog(FormularioPrincipal.this, "No hay solicitudes en cola.");
                }
            }
        });

        panel.add(lblDni); panel.add(cbxDni);
        panel.add(lblFundo); panel.add(cbxFundo);
        panel.add(lblMenu); panel.add(cbxMenu);
        panel.add(lblNom); panel.add(txtMenuNombre);
        panel.add(btnRegistrar); panel.add(btnAtender);
        panel.add(sp);

        return panel;
    }

    /* ====================================================================== */
    /*                           MÉTODOS AUXILIARES                           */
    /* ====================================================================== */
    private void actualizarEstadoBotones(JButton undo, JButton redo) {
        undo.setEnabled(gestorUndoRedo.sePuedeDeshacer());
        redo.setEnabled(gestorUndoRedo.sePuedeRehacer());
    }

    private void actualizarCombosSolicitudes() {
        if(cbxDni==null) return; // aún no creados
        cbxDni.removeAllItems();
        for(Persona p: repoPersonas.obtenerTodas()) cbxDni.addItem(p.getDni());
        cbxFundo.removeAllItems();
        for(Fundo f: repoFundos.todos()) cbxFundo.addItem(f.getNombre());
        cbxMenu.removeAllItems();
        for(Menus m: repoMenus.todos()) cbxMenu.addItem(m.getId());
        txtMenuNombre.setText("");
    }

    /* ----------------------------- main ----------------------------------- */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                new FormularioPrincipal().setVisible(true);
            }
        });
    }
}
