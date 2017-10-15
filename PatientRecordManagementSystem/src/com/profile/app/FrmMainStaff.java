/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profile.app;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import static java.sql.DriverManager.getConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.ListSelectionListener;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Tomambid
 */
public final class FrmMainStaff extends javax.swing.JFrame {

    private byte[] imgdata;
    private String gender;
    private String med;
    private String contraceptive;
    ButtonGroup bg = new ButtonGroup();
    ImageIcon icon = new ImageIcon("C:\\Users\\Tomambid\\Documents\\NetBeansProjects\\PatientRecordManagementSystem\\src\\com\\profile\\img\\icons8-Female User-128.png");

    public FrmMainStaff() {
        initComponents();
        this.setTitle("Main Menu");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(screenSize);
        this.setVisible(true);
        this.setResizable(false);
        btnHome.setBorderPainted(false);
        btnHome.setBorder(null);
        btnHome.setMargin(new Insets(0, 0, 0, 0));
        btnHome.setContentAreaFilled(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setBorder(null);
        btnAdd.setMargin(new Insets(0, 0, 0, 0));
        btnAdd.setContentAreaFilled(false);
        btnPrint.setBorderPainted(false);
        btnPrint.setBorder(null);
        btnPrint.setMargin(new Insets(0, 0, 0, 0));
        btnPrint.setContentAreaFilled(false);
        btnHelp.setBorderPainted(false);
        btnHelp.setBorder(null);
        btnHelp.setMargin(new Insets(0, 0, 0, 0));
        btnHelp.setContentAreaFilled(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setBorder(null);
        btnLogout.setMargin(new Insets(0, 0, 0, 0));
        btnLogout.setContentAreaFilled(false);
        showDate();
        showTime();
        bg.add(rdoBtnMale);
        bg.add(rdoBtnFemale);
        bg.add(rdoBtnMALE);
        bg.add(rdoBtnFEMALE);
        ImageIcon img = new ImageIcon("src\\com\\profile\\img\\home.png");
        this.setIconImage(img.getImage());
        this.showTablePersonnel();
        this.showTableAudit();
        this.comboPatientFill();
        this.comboServiceFill();
        this.showTablePatient();
        this.showTableMedAvail();
        this.showTableServiceAvail();
       
        

    }
    
     public void showTableServiceAvail(){
        String[] columns = {"p_id","p_fname", "p_lname", "p_mi", "philhealthno","attendant","service_avail"};
        String whereClause = "1=1";
        String[][] records = PG.executeDQL("Patient", columns, whereClause);
        UIC.Table.setModel(tblServiceAvail, records, columns);
    }

    public void resetPersonnelForm() {
        txtUsername.setText("");
        txtFirstname.setText("");
        txtLastname.setText("");
        txtMiddleInitial.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        bg.clearSelection();
        cmbPosition.setSelectedItem("Midwife");
        lblIMAGE.setIcon(icon);
      
    }
    
    public void resetPatientForm(){
        txtPatientFname.setText("");
        txtPatientMI.setText("");
        txtPatientLname.setText("");
        txtPatientAge.setText("");
        txtContact.setText("");
        bg.clearSelection();
        txtPhilHealthNo.setText("");
        txtAddress.setText("");
        txtDiagnosis.setText("");
        dcDOB = null;
    }
    
    private void comboPatientFill(){
        try{
            PG.conn = getConnection(PG.url,PG.username,PG.password);
            PG.stmt = PG.conn.createStatement();
            String query = "SELECT * FROM Personnel";
            ResultSet rs = PG.stmt.executeQuery(query);
            while(rs.next()){
                String name = rs.getString("U_Fname");
                String lname = rs.getString("U_Lname");
                cmbAttendant.addItem(name+ " " + lname);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     private void comboServiceFill(){
        try{
            PG.conn = getConnection(PG.url,PG.username,PG.password);
            PG.stmt = PG.conn.createStatement();
            String query = "SELECT * FROM Service";
            ResultSet rs = PG.stmt.executeQuery(query);
            while(rs.next()){
                String servicename = rs.getString("description");
                cmbServiceAvail.addItem(servicename);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
      public void showTableMedAvail(){
        String[] columns = {"MA_ID","P_NAME","MED_NAME","CON_NAME"};
        String whereClause = "1=1";
        String[][] records = PG.executeDQL("Medicine_avail", columns, whereClause);
        UIC.Table.setModel(tblMedAvail, records, columns);
    } 
    public void showTablePatient(){
        String[] columns = {"p_id","p_fname", "p_lname", "p_mi", "p_gender","p_contact","p_birthdate","p_address","p_occupation","philhealthno","attendant","service_avail","diagnosis"};
        String whereClause = "1=1";
        String[][] records = PG.executeDQL("Patient", columns, whereClause);
        UIC.Table.setModel(tblPatient, records, columns);
    }

    public void showTableAudit() {
        String[] columns = {"audit_id", "dateAudit", "timeAudit", "status", "U_ID"};
        String whereClause = "1=1";
        String[][] records = PG.executeDQL("Audit", columns, whereClause);
        UIC.Table.setModel(tblAudit, records, columns);
    }
    
    public void showTablePersonnel(){
        String[] columns = {"U_ID", "U_Fname", "U_Lname", "U_MI", "Email", "Gender", "Username", "Password", "Position"};
        String whereClause = "1=1";
        String[][] records = PG.executeDQL("Personnel", columns, whereClause);
        UIC.Table.setModel(tblPersonnel, records, columns);
    }

    public void showDate() {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("MMMM dd, yyyy");
        lblDate.setText(s.format(d));

    }

    public void showTime() {
        new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss a");
                lblTime.setText(s.format(d));

            }

        }
        ).start();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pnlButton = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnHelp = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        btnHome = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        pnlHome = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        pnlAddPatient = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPatient = new javax.swing.JTable();
        jLabel55 = new javax.swing.JLabel();
        txtSearchPatient = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        txtPatientFname = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        txtPatientMI = new javax.swing.JTextField();
        txtPatientLname = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        txtPatientAge = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        txtContact = new javax.swing.JTextField();
        dcDOB = new com.toedter.calendar.JDateChooser();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        txtOccupation = new javax.swing.JTextField();
        rdoBtnMALE = new javax.swing.JRadioButton();
        jLabel63 = new javax.swing.JLabel();
        rdoBtnFEMALE = new javax.swing.JRadioButton();
        jLabel65 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDiagnosis = new javax.swing.JTextArea();
        jLabel64 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        cmbServiceAvail = new javax.swing.JComboBox<>();
        jLabel69 = new javax.swing.JLabel();
        cmbAttendant = new javax.swing.JComboBox<>();
        btnDeletePatient = new javax.swing.JButton();
        btnSubmitPatient = new javax.swing.JButton();
        btnResetPatient = new javax.swing.JButton();
        btnUpdatePatient = new javax.swing.JButton();
        jLabel72 = new javax.swing.JLabel();
        txtPhilHealthNo = new javax.swing.JTextField();
        pnlAddUser = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPersonnel = new javax.swing.JTable();
        lblIMAGE = new javax.swing.JLabel();
        btnBrowse = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        txtLastname = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        txtMiddleInitial = new javax.swing.JTextField();
        txtSearchPersonnel = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        rdoBtnMale = new javax.swing.JRadioButton();
        rdoBtnFemale = new javax.swing.JRadioButton();
        jLabel46 = new javax.swing.JLabel();
        txtFirstname = new javax.swing.JTextField();
        cmbPosition = new javax.swing.JComboBox<>();
        jLabel47 = new javax.swing.JLabel();
        btnDelete = new javax.swing.JButton();
        btnSubmit = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        txtPassword = new javax.swing.JTextField();
        pnlActivity = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblAudit = new javax.swing.JTable();
        txtSearchActivity = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        btnDeleteActivity = new javax.swing.JButton();
        pnlPrint = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblMedAvail = new javax.swing.JTable();
        btnPrintSummary = new javax.swing.JButton();
        btnPrintServiceAvail = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblServiceAvail = new javax.swing.JTable();
        pnlHelp = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        pnlButton.setBackground(new java.awt.Color(102, 204, 255));
        pnlButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/rsz_addpatient.png"))); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel2.setText("  Add Patient");

        btnHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/rsz_if_info-sign_173039.png"))); // NOI18N
        btnHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHelpActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel4.setText("       Help");

        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/rsz_if_shutdown_box_red_34246_1.png"))); // NOI18N
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel5.setText("    Log out");

        lblDate.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 48)); // NOI18N
        lblDate.setText("DATE");

        lblTime.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 48)); // NOI18N
        lblTime.setText("TIME");

        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/home.png"))); // NOI18N
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel6.setText("      Home");

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Graph Report-100.png"))); // NOI18N
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Print");

        javax.swing.GroupLayout pnlButtonLayout = new javax.swing.GroupLayout(pnlButton);
        pnlButton.setLayout(pnlButtonLayout);
        pnlButtonLayout.setHorizontalGroup(
            pnlButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(pnlButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnHome)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(pnlButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(pnlButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65)
                .addGroup(pnlButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65)
                .addGroup(pnlButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(588, 588, 588)
                .addGroup(pnlButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlButtonLayout.setVerticalGroup(
            pnlButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonLayout.createSequentialGroup()
                .addGroup(pnlButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlButtonLayout.createSequentialGroup()
                        .addGroup(pnlButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6)))
                    .addGroup(pnlButtonLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlButtonLayout.createSequentialGroup()
                        .addGroup(pnlButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnHelp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)))))
                .addContainerGap())
        );

        pnlContent.setBackground(new java.awt.Color(255, 255, 153));
        pnlContent.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlContent.setLayout(new java.awt.CardLayout());

        pnlHome.setBackground(new java.awt.Color(255, 255, 153));
        pnlHome.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/lwell-family-ogo_resized (1).gif"))); // NOI18N

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 24)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("PhilHealth Accredited Facility");

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));

        jPanel2.setBackground(new java.awt.Color(255, 255, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Services Offered:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Yu Gothic UI Semibold", 1, 32), new java.awt.Color(0, 51, 255))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 20)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("FAMILY PLANNING");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Heart Outline-20.png"))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Pills Supply");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Heart Outline-20.png"))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Condom Supply");

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Heart Outline-20.png"))); // NOI18N

        jLabel15.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Counseling on ");

        jLabel16.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Family Planning");

        jLabel17.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Adolescent");

        jLabel18.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Reproductive Health");

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Sphere Filled-15.png"))); // NOI18N

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Sphere Filled-15.png"))); // NOI18N

        jLabel21.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 20)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("MATERNAL & CHILD HEALTH");

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Heart Outline-20.png"))); // NOI18N

        jLabel23.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Pre-natal Care");

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Heart Outline-20.png"))); // NOI18N

        jLabel25.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Post-natal Care");

        jLabel26.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Pregnancy Test");

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Heart Outline-20.png"))); // NOI18N

        jLabel28.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Safe Delivery");

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Heart Outline-20.png"))); // NOI18N

        jLabel30.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 20)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("OTHERS");

        jLabel31.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Blood Pressure Taking");

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Heart Outline-20.png"))); // NOI18N

        jLabel33.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Weight Taking");

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Heart Outline-20.png"))); // NOI18N

        jLabel35.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Wound Dressing");

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Heart Outline-20.png"))); // NOI18N

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Heart Outline-20.png"))); // NOI18N

        jLabel38.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Ear Piercing");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel18))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel12)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(426, 426, 426)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel22)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel23))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel29)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel28))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel24)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel25))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel27)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel26)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel32)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel31))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel34)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel33))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel36)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel35))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel37)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel38))))
                            .addComponent(jLabel13))
                        .addGap(62, 62, 62))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(338, 338, 338)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 344, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addGap(131, 131, 131))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(104, 104, 104))))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel17)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addComponent(jLabel18))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38))))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlHomeLayout = new javax.swing.GroupLayout(pnlHome);
        pnlHome.setLayout(pnlHomeLayout);
        pnlHomeLayout.setHorizontalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addGroup(pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHomeLayout.createSequentialGroup()
                        .addGap(748, 748, 748)
                        .addComponent(jLabel7))
                    .addGroup(pnlHomeLayout.createSequentialGroup()
                        .addGap(539, 539, 539)
                        .addGroup(pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator1)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 807, Short.MAX_VALUE)
                            .addComponent(jSeparator2)))
                    .addGroup(pnlHomeLayout.createSequentialGroup()
                        .addGap(252, 252, 252)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(2073, Short.MAX_VALUE))
        );
        pnlHomeLayout.setVerticalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(275, Short.MAX_VALUE))
        );

        pnlContent.add(pnlHome, "card6");

        pnlAddPatient.setBackground(new java.awt.Color(255, 255, 153));
        pnlAddPatient.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblPatient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPatientMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPatient);

        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Search-35.png"))); // NOI18N

        txtSearchPatient.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        txtSearchPatient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchPatientKeyReleased(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel56.setText("Given Name:");

        jLabel57.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel57.setText("Middle Initial:");

        txtPatientFname.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N

        jLabel58.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel58.setText("Lastname:");

        txtPatientMI.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N

        txtPatientLname.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N

        jLabel59.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel59.setText("Gender:");

        txtPatientAge.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N

        jLabel60.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel60.setText("Contact#:");

        txtContact.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N

        dcDOB.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N

        jLabel61.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel61.setText("Date of Birth:");

        jLabel62.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel62.setText("Occupation:");

        txtOccupation.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N

        rdoBtnMALE.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        rdoBtnMALE.setText("Male");
        rdoBtnMALE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoBtnMALEActionPerformed(evt);
            }
        });

        jLabel63.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel63.setText("Age:");

        rdoBtnFEMALE.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        rdoBtnFEMALE.setText("Female");

        jLabel65.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel65.setText("Patient's Address: ");

        txtAddress.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N

        jLabel68.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel68.setText("Diagnosis:");

        txtDiagnosis.setColumns(20);
        txtDiagnosis.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        txtDiagnosis.setRows(5);
        jScrollPane3.setViewportView(txtDiagnosis);

        jLabel64.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 36)); // NOI18N
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText("PATIENT FORM:");

        jLabel66.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel66.setText("Service Avail:");

        cmbServiceAvail.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        cmbServiceAvail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbServiceAvailActionPerformed(evt);
            }
        });

        jLabel69.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel69.setText("Attendant:");

        cmbAttendant.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N

        btnDeletePatient.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        btnDeletePatient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Delete-25.png"))); // NOI18N
        btnDeletePatient.setText("Delete");
        btnDeletePatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletePatientActionPerformed(evt);
            }
        });

        btnSubmitPatient.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        btnSubmitPatient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Save-25.png"))); // NOI18N
        btnSubmitPatient.setText("Submit");
        btnSubmitPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitPatientActionPerformed(evt);
            }
        });

        btnResetPatient.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        btnResetPatient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Reset-25 (1).png"))); // NOI18N
        btnResetPatient.setText("Reset");
        btnResetPatient.setToolTipText("");
        btnResetPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetPatientActionPerformed(evt);
            }
        });

        btnUpdatePatient.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        btnUpdatePatient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Reset-25.png"))); // NOI18N
        btnUpdatePatient.setText("Update");
        btnUpdatePatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdatePatientActionPerformed(evt);
            }
        });

        jLabel72.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel72.setText("PhilHealth No:");

        txtPhilHealthNo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N

        javax.swing.GroupLayout pnlAddPatientLayout = new javax.swing.GroupLayout(pnlAddPatient);
        pnlAddPatient.setLayout(pnlAddPatientLayout);
        pnlAddPatientLayout.setHorizontalGroup(
            pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddPatientLayout.createSequentialGroup()
                .addContainerGap(1836, Short.MAX_VALUE)
                .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlAddPatientLayout.createSequentialGroup()
                        .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pnlAddPatientLayout.createSequentialGroup()
                                .addComponent(jLabel68)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(363, 363, 363))
                            .addGroup(pnlAddPatientLayout.createSequentialGroup()
                                .addComponent(jLabel65)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(191, 191, 191)
                        .addComponent(btnUpdatePatient, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(83, 83, 83)
                        .addComponent(btnDeletePatient, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(btnResetPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(btnSubmitPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAddPatientLayout.createSequentialGroup()
                        .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel64)
                            .addComponent(jLabel69))
                        .addGap(1285, 1285, 1285)
                        .addComponent(jLabel55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAddPatientLayout.createSequentialGroup()
                        .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(pnlAddPatientLayout.createSequentialGroup()
                                    .addComponent(jLabel57)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtPatientMI, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(15, 15, 15)
                                    .addComponent(jLabel63)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtPatientAge, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlAddPatientLayout.createSequentialGroup()
                                    .addComponent(jLabel62)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtOccupation))
                                .addGroup(pnlAddPatientLayout.createSequentialGroup()
                                    .addComponent(jLabel56)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtPatientFname))
                                .addGroup(pnlAddPatientLayout.createSequentialGroup()
                                    .addComponent(jLabel58)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtPatientLname))
                                .addGroup(pnlAddPatientLayout.createSequentialGroup()
                                    .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel60)
                                        .addComponent(jLabel61))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(dcDOB, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                        .addComponent(txtContact)))
                                .addGroup(pnlAddPatientLayout.createSequentialGroup()
                                    .addComponent(jLabel59)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(rdoBtnMALE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(rdoBtnFEMALE)))
                            .addGroup(pnlAddPatientLayout.createSequentialGroup()
                                .addComponent(jLabel72)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPhilHealthNo, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(cmbAttendant, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlAddPatientLayout.createSequentialGroup()
                                    .addComponent(jLabel66)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cmbServiceAvail, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1415, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlAddPatientLayout.setVerticalGroup(
            pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddPatientLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlAddPatientLayout.createSequentialGroup()
                        .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlAddPatientLayout.createSequentialGroup()
                                .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPatientFname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4))
                            .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtPatientMI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPatientAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPatientLname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dcDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtOccupation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(rdoBtnMALE)
                            .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoBtnFEMALE))
                        .addGap(10, 10, 10)
                        .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(cmbServiceAvail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbAttendant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPhilHealthNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlAddPatientLayout.createSequentialGroup()
                        .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSearchPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)))
                .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAddPatientLayout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(btnDeletePatient, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSubmitPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnResetPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdatePatient, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlAddPatientLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlAddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(207, Short.MAX_VALUE))
        );

        pnlContent.add(pnlAddPatient, "card2");

        pnlAddUser.setBackground(new java.awt.Color(255, 255, 153));
        pnlAddUser.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblPersonnel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        tblPersonnel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblPersonnel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPersonnelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPersonnel);

        lblIMAGE.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIMAGE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Female User-128.png"))); // NOI18N

        btnBrowse.setText("Browse");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel39.setText("Username:");

        txtEmail.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N

        jLabel40.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel40.setText("Password:");

        jLabel42.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel42.setText("Given Name:");

        txtUsername.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N

        jLabel43.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel43.setText("Last Name:");

        txtLastname.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N

        jLabel44.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel44.setText("Middle Initial:");

        txtMiddleInitial.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N

        txtSearchPersonnel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        txtSearchPersonnel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchPersonnelKeyReleased(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel45.setText("Gender:");

        rdoBtnMale.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        rdoBtnMale.setText("Male");

        rdoBtnFemale.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        rdoBtnFemale.setText("Female");

        jLabel46.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel46.setText("Email:");

        txtFirstname.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N

        cmbPosition.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        cmbPosition.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Midwife", "Staff" }));

        jLabel47.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel47.setText("Position:");

        btnDelete.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Delete-25.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnSubmit.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        btnSubmit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Save-25.png"))); // NOI18N
        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Search-35.png"))); // NOI18N

        btnReset.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Reset-25 (1).png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.setToolTipText("");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Reset-25.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        txtPassword.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N

        javax.swing.GroupLayout pnlAddUserLayout = new javax.swing.GroupLayout(pnlAddUser);
        pnlAddUser.setLayout(pnlAddUserLayout);
        pnlAddUserLayout.setHorizontalGroup(
            pnlAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddUserLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(pnlAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAddUserLayout.createSequentialGroup()
                        .addGroup(pnlAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39)
                            .addComponent(jLabel40))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(85, 85, 85)
                        .addComponent(jLabel47)
                        .addGap(18, 18, 18)
                        .addComponent(cmbPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblIMAGE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBrowse, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGroup(pnlAddUserLayout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMiddleInitial, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAddUserLayout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAddUserLayout.createSequentialGroup()
                        .addGroup(pnlAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43)
                            .addComponent(jLabel45))
                        .addGap(18, 18, 18)
                        .addGroup(pnlAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlAddUserLayout.createSequentialGroup()
                                .addComponent(rdoBtnMale)
                                .addGap(18, 18, 18)
                                .addComponent(rdoBtnFemale))
                            .addComponent(txtLastname, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlAddUserLayout.createSequentialGroup()
                        .addComponent(jLabel46)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1824, Short.MAX_VALUE)
                .addGroup(pnlAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlAddUserLayout.createSequentialGroup()
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAddUserLayout.createSequentialGroup()
                        .addComponent(jLabel48)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchPersonnel, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1211, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );
        pnlAddUserLayout.setVerticalGroup(
            pnlAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddUserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAddUserLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(pnlAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtSearchPersonnel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAddUserLayout.createSequentialGroup()
                        .addComponent(lblIMAGE, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBrowse)
                        .addGap(66, 66, 66)
                        .addGroup(pnlAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(cmbPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlAddUserLayout.createSequentialGroup()
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddUserLayout.createSequentialGroup()
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(pnlAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMiddleInitial, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtLastname, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(rdoBtnFemale)
                            .addComponent(rdoBtnMale)
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(162, Short.MAX_VALUE))
        );

        pnlContent.add(pnlAddUser, "card3");

        pnlActivity.setBackground(new java.awt.Color(255, 255, 153));
        pnlActivity.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblAudit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tblAudit);

        txtSearchActivity.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        txtSearchActivity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchActivityKeyReleased(evt);
            }
        });

        jLabel70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Search-35.png"))); // NOI18N

        btnDeleteActivity.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        btnDeleteActivity.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-Reset-25 (1).png"))); // NOI18N
        btnDeleteActivity.setText("Reset");
        btnDeleteActivity.setToolTipText("");
        btnDeleteActivity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActivityActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlActivityLayout = new javax.swing.GroupLayout(pnlActivity);
        pnlActivity.setLayout(pnlActivityLayout);
        pnlActivityLayout.setHorizontalGroup(
            pnlActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActivityLayout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addGroup(pnlActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDeleteActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlActivityLayout.createSequentialGroup()
                        .addComponent(jLabel70)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1670, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1949, Short.MAX_VALUE))
        );
        pnlActivityLayout.setVerticalGroup(
            pnlActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActivityLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(pnlActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtSearchActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(btnDeleteActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        pnlContent.add(pnlActivity, "card4");

        pnlPrint.setBackground(new java.awt.Color(255, 255, 153));

        tblMedAvail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tblMedAvail);

        btnPrintSummary.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        btnPrintSummary.setText("Print Medicine Avail");
        btnPrintSummary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintSummaryActionPerformed(evt);
            }
        });

        btnPrintServiceAvail.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        btnPrintServiceAvail.setText("Print Service Avail");
        btnPrintServiceAvail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintServiceAvailActionPerformed(evt);
            }
        });

        tblServiceAvail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(tblServiceAvail);

        javax.swing.GroupLayout pnlPrintLayout = new javax.swing.GroupLayout(pnlPrint);
        pnlPrint.setLayout(pnlPrintLayout);
        pnlPrintLayout.setHorizontalGroup(
            pnlPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrintLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(pnlPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 819, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrintSummary, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(pnlPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPrintServiceAvail, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 920, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1898, Short.MAX_VALUE))
        );
        pnlPrintLayout.setVerticalGroup(
            pnlPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrintLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(pnlPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addGroup(pnlPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrintSummary, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrintServiceAvail, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(233, Short.MAX_VALUE))
        );

        pnlContent.add(pnlPrint, "card7");

        pnlHelp.setBackground(new java.awt.Color(255, 255, 153));
        pnlHelp.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel49.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 24)); // NOI18N
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-User Manual-96.png"))); // NOI18N
        jLabel49.setText("USER GUIDE");

        jPanel3.setBackground(new java.awt.Color(255, 255, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Guide", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI Semibold", 1, 36), new java.awt.Color(0, 51, 255))); // NOI18N

        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-1-60.png"))); // NOI18N

        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-2-60.png"))); // NOI18N

        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/nav.png"))); // NOI18N

        jLabel53.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel53.setText("The navigation for the system; Clicking this will let you go to the different functionality of the system.");

        jLabel54.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel54.setText("Clicking the data in the table will automatically load the different information of the entity.");

        jLabel71.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel71.setText("After Clicking the data you can either delete, reset and update the information.");

        jLabel76.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel76.setText("In the Print button, clicking the two different button will generate a report of the patient that avails medicine, contraceptive and services");

        jLabel75.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-4-60.png"))); // NOI18N

        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/profile/img/icons8-3-60.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel53))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel51)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel54))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 967, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel67)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel71))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel75)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel76)))
                .addContainerGap(571, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel75)
                    .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(190, 190, 190))
        );

        javax.swing.GroupLayout pnlHelpLayout = new javax.swing.GroupLayout(pnlHelp);
        pnlHelp.setLayout(pnlHelpLayout);
        pnlHelpLayout.setHorizontalGroup(
            pnlHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHelpLayout.createSequentialGroup()
                .addGroup(pnlHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHelpLayout.createSequentialGroup()
                        .addGap(815, 815, 815)
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlHelpLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(1878, Short.MAX_VALUE))
        );
        pnlHelpLayout.setVerticalGroup(
            pnlHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHelpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        pnlContent.add(pnlHelp, "card5");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        pnlContent.removeAll();
        pnlContent.repaint();
        pnlContent.revalidate();

        pnlContent.add(pnlAddPatient);
        pnlContent.repaint();
        pnlContent.revalidate();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHelpActionPerformed
        pnlContent.removeAll();
        pnlContent.repaint();
        pnlContent.revalidate();

        pnlContent.add(pnlHelp);
        pnlContent.repaint();
        pnlContent.revalidate();
    }//GEN-LAST:event_btnHelpActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        this.dispose();
        FrmLogin flogin = new FrmLogin();
        flogin.setVisible(true);
        Date currentDate = GregorianCalendar.getInstance().getTime();
        DateFormat df = DateFormat.getDateInstance();
        String dateString = df.format(currentDate);

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timeString = sdf.format(d);
        String value0 = timeString;
        String value1 = dateString;
        int value = FrmLogin.pid;
        String status = "Log out";
        Object[] values1 = {value1,value0,status,value};
        String query1 = "INSERT INTO Audit(dateaudit,timeaudit,status,u_id) VALUES (?,?,?,?)";
        PG.executeDML(query1, values1);
        
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        pnlContent.removeAll();
        pnlContent.repaint();
        pnlContent.revalidate();

        pnlContent.add(pnlHome);
        pnlContent.repaint();
        pnlContent.revalidate();
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);

        File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();

        //raw image to bytes
        File fimage = new File(filename);
        try {
            FileInputStream fis = new FileInputStream(fimage);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
            imgdata = bos.toByteArray();

            //update JLabel to load image
            java.awt.Image image = stretchImage(filename, this.lblIMAGE);
            this.lblIMAGE.setIcon(new javax.swing.ImageIcon(image));
        } catch (Exception e) {
            System.out.println("FileInputStream Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        try {
            
            if (rdoBtnMale.isSelected()) {
                gender = "Male";
            } else {
                gender = "Female";
            }
            String firstname = txtFirstname.getText();
            String MI = txtMiddleInitial.getText();
            String lastname = txtLastname.getText();
            String email = txtEmail.getText();
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String position = cmbPosition.getSelectedItem().toString();
            byte[] image = this.imgdata;
            if (txtFirstname.equals("") || txtMiddleInitial.equals("") || txtLastname.equals("") || txtEmail.equals("") || txtUsername.equals("") || txtPassword.equals("") || bg.getSelection() == null) {
                JOptionPane.showMessageDialog(null, "Some fields are empty!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (txtMiddleInitial.getText().length() < 1 || txtMiddleInitial.getText().length() > 1) {
                JOptionPane.showMessageDialog(null, "Middle Initial Only", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int response = JOptionPane.showConfirmDialog(null, "Do you want to register?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (response == 0) {
                Object[] values = {firstname, lastname, MI, email, gender, username, password, position, image};
                String query = "INSERT INTO Personnel (u_fname,u_lname,u_mi,email,gender,username,password,position,image) VALUES (?,?,?,?,?,?,?,?,?)";
                if (PG.executeDML(query, values)) {
                  JOptionPane.showMessageDialog(null, username + " has been created");

                    this.showTablePersonnel();
                    this.showTableAudit();
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "An error occured while creating new task.");
                }
                    Date currentDate = GregorianCalendar.getInstance().getTime();
                    DateFormat df = DateFormat.getDateInstance();
                    String dateString = df.format(currentDate);

                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String timeString = sdf.format(d);
                    String value0 = timeString;
                    String value1 = dateString;
                    int value = FrmLogin.pid;
                   
                    String status = "Inserted the field "+firstname+","+lastname+","+MI+","+email+","+gender+","+username+","+password+","+position+" Into Personnel";
                    Object[] values1 = {value1,value0,status,value};
                    String query1 = "INSERT INTO Audit(dateaudit,timeaudit,status,u_id) VALUES (?,?,?,?)";
                    PG.executeDML(query1, values1); 

            }
        } catch (Exception e) {
            System.out.println("FileInputStream Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void txtSearchPersonnelKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchPersonnelKeyReleased
        String searchPersonnel = txtSearchPersonnel.getText();
        if (txtSearchPersonnel.equals("")) {
            this.showTablePersonnel();
        }else{
            try{
                PG.conn = getConnection(PG.url,PG.username,PG.password);
                PG.stmt = PG.conn.createStatement();
                String whereClause = "u_fname like '%"+searchPersonnel+"%' OR u_lname like '%"+searchPersonnel+"%' OR u_mi like '%"
                        +searchPersonnel+"%' OR email like '%"+searchPersonnel+"%' OR gender like '%"+searchPersonnel+"%' OR username like '%"
                        +searchPersonnel+"%' OR password like '%"+searchPersonnel+"%' OR position like '%"+searchPersonnel+"%'";
                String query = "SELECT U_ID, U_FNAME, U_LNAME, U_MI, EMAIL, GENDER, USERNAME, PASSWORD, POSITION FROM Personnel WHERE "+ whereClause;
                ResultSet rs = PG.stmt.executeQuery(query);
                
                tblPersonnel.setModel(DbUtils.resultSetToTableModel(rs));
                PG.conn.close();
            }catch(Exception e){
                System.out.println("Search Error: "+e);
            }
        }                                     
    }//GEN-LAST:event_txtSearchPersonnelKeyReleased

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        this.resetPersonnelForm();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int id = UIC.Table.getSelectedRow(tblPersonnel);
        Object[] values = {id};
        int reply = javax.swing.JOptionPane.showConfirmDialog(null, "Do you wish to delete this account?", "Delete", JOptionPane.YES_NO_OPTION);
        if(reply == 0){
            String query = "DELETE FROM Personnel WHERE u_id=?";
            if (PG.executeDML(query, values)) {
                javax.swing.JOptionPane.showMessageDialog(null, "Personnel Removed!");

                this.resetPersonnelForm();
                this.showTablePersonnel();
                this.showTableAudit();
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "An error occured while removing Personnel Information");
            }
             Date currentDate = GregorianCalendar.getInstance().getTime();
                    DateFormat df = DateFormat.getDateInstance();
                    String dateString = df.format(currentDate);

                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String timeString = sdf.format(d);
                    String value0 = timeString;
                    String value1 = dateString;
                    int value = FrmLogin.pid;
                    String status = "Deleted the field id "+id+" from Personnel";
                    Object[] values1 = {value1,value0,status,value};
                    String query1 = "INSERT INTO Audit(dateaudit,timeaudit,status,u_id) VALUES (?,?,?,?)";
                    PG.executeDML(query1, values1);
        }

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblPersonnelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPersonnelMouseClicked
        // TODO add your handling code here:
        int lpersonnel = UIC.Table.getSelectedRow(tblPersonnel);
        System.out.println("Clicked: " + lpersonnel);
        String[] columns = {"U_ID", "U_Fname", "U_Lname", "U_MI", "Email", "Gender", "Username", "Password", "Position"};
        String whereClause = "u_id=" + lpersonnel;
        String[][] records = PG.executeDQL("Personnel", columns, whereClause);

        txtFirstname.setText(records[0][1]);
        txtLastname.setText(records[0][2]);
        txtMiddleInitial.setText(records[0][3]);
        txtEmail.setText(records[0][4]);
        String gender = records[0][5];
        if (gender.equals("Male")) {
            rdoBtnMale.setSelected(true);
            rdoBtnFemale.setSelected(false);
        } else {
            rdoBtnMale.setSelected(false);
            rdoBtnFemale.setSelected(true);
        }
        txtUsername.setText(records[0][6]);
        txtPassword.setText(records[0][7]);
        String position = records[0][8];
        if (position.equals("Midwife")) {
            cmbPosition.setSelectedItem("Midwife");
        } else {
            cmbPosition.setSelectedItem("Staff");
        }
        try {
            PG.conn = getConnection(PG.url, PG.username, PG.password);
            PG.stmt = PG.conn.createStatement();
            ResultSet rs = PG.stmt.executeQuery("SELECT image FROM Personnel WHERE u_id=" + lpersonnel);
            if (rs.next()) {
                byte[] in = rs.getBytes("image");
                ImageIcon aykon = new ImageIcon(in);
                //stretching image
                Image image = aykon.getImage();
                Image scaledImage = image.getScaledInstance(lblIMAGE.getWidth(), lblIMAGE.getHeight(), Image.SCALE_DEFAULT);
                lblIMAGE.setIcon(new javax.swing.ImageIcon(scaledImage));
            } else {
                lblIMAGE.setIcon(this.icon);
            }
            //byte[] in = PG.read("profile", "image", id, lblIMAGE);
//            ImageIcon icon =new ImageIcon(in);
//
//            //stretching image
//            Image image = icon.getImage();
//            Image scaledImage = image.getScaledInstance(lblIMAGE.getWidth(),lblIMAGE.getHeight(), Image.SCALE_DEFAULT);                                                                
//            lblIMAGE.setIcon(new javax.swing.ImageIcon(scaledImage));  
        } catch (Exception e) {
            //System.out.println("Image Error: " + e.getMessage());
            System.out.println("no image");
            ImageIcon icons = new ImageIcon("C:\\Users\\Tomambid\\Documents\\NetBeansProjects\\PatientRecordManagementSystem\\src\\com\\profile\\img\\icons8-Female User-128.png");
            Image image = icons.getImage();
            Image scaledImage = image.getScaledInstance(lblIMAGE.getWidth(), lblIMAGE.getHeight(), Image.SCALE_DEFAULT);
            lblIMAGE.setIcon(new javax.swing.ImageIcon(scaledImage));
        }
    }//GEN-LAST:event_tblPersonnelMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
         int id = UIC.Table.getSelectedRow(tblPersonnel);
        
        if (rdoBtnMale.isSelected()) {
                gender = "Male";
            } else {
                gender = "Female";
            }
            String firstname = txtFirstname.getText();
            String MI = txtMiddleInitial.getText();
            String lastname = txtLastname.getText();
            String email = txtEmail.getText();
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String position = cmbPosition.getSelectedItem().toString();
            byte[] image = this.imgdata;
            int reply = javax.swing.JOptionPane.showConfirmDialog(null, "Do you wish to update this account?", "UPDATE", JOptionPane.YES_NO_OPTION);
            if(reply == 0){
                Object[] values = {firstname,lastname, MI, email, gender, username, password, position, image, id};
                String query = "UPDATE Personnel SET U_fname=?, U_lname=?, U_MI=?, email=?, gender=?, username=?, password=?, position=?, image=? WHERE u_id=?";
                if(PG.executeDML(query, values)){
                    JOptionPane.showMessageDialog(null, "Personnel Information Successfully Updated!");
                    this.showTablePersonnel();
                }else{
                    JOptionPane.showMessageDialog(null, "Personnel Update Error!");
                }
                 Date currentDate = GregorianCalendar.getInstance().getTime();
                    DateFormat df = DateFormat.getDateInstance();
                    String dateString = df.format(currentDate);

                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String timeString = sdf.format(d);
                    String value0 = timeString;
                    String value1 = dateString;
                    int value = FrmLogin.pid;
                    String status = "Updated the field "+firstname+","+lastname+","+MI+","+email+","+gender+","+username+","+password+","+position+","+image+" Into Personnel";
                    Object[] values1 = {value1,value0,status,value};
                    String query1 = "INSERT INTO Audit(dateaudit,timeaudit,status,u_id) VALUES (?,?,?,?)";
                    PG.executeDML(query1, values1);
            }else{
                JOptionPane.showMessageDialog(null, "Update cancelled!");
                this.showTablePersonnel();
                this.showTableAudit();
            }
            
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void rdoBtnMALEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoBtnMALEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoBtnMALEActionPerformed

    private void txtSearchActivityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchActivityKeyReleased
        String searchActivity = txtSearchActivity.getText();
        if(txtSearchActivity.equals("")){
            this.showTableAudit();
        }else{
            try{
                PG.conn = getConnection(PG.url,PG.username,PG.password);
                PG.stmt = PG.conn.createStatement();
                String whereClause = "dateAudit LIKE '%"+searchActivity+"%' OR "
                        + "timeAudit LIKE '%"+searchActivity+"%' OR status LIKE '%"+searchActivity+"%'";
                String query = "SELECT AUDIT_ID, DATEAUDIT, TIMEAUDIT, STATUS, U_ID FROM Audit WHERE "+whereClause;
                ResultSet rs = PG.stmt.executeQuery(query);
                
                tblAudit.setModel(DbUtils.resultSetToTableModel(rs));
                PG.conn.close();
            }catch(Exception e){
                System.out.println("Search Error:"+e.getMessage());
            }
        }
    }//GEN-LAST:event_txtSearchActivityKeyReleased

    private void btnDeletePatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletePatientActionPerformed
        int id = UIC.Table.getSelectedRow(tblPatient);
        Object[] values = {id};
        int reply = javax.swing.JOptionPane.showConfirmDialog(null, "Do you wish to delete this Patient Record?", "Delete", JOptionPane.YES_NO_OPTION);
        if(reply == 0){
            String query = "DELETE FROM Patient WHERE p_id=?";
            if (PG.executeDML(query, values)) {
                javax.swing.JOptionPane.showMessageDialog(null, "Patient Record Removed!");

                this.resetPatientForm();
                this.showTablePatient();
                this.showTableAudit();
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "An error occured while removing Patient Record");
            }
             Date currentDate = GregorianCalendar.getInstance().getTime();
                    DateFormat df = DateFormat.getDateInstance();
                    String dateString = df.format(currentDate);

                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String timeString = sdf.format(d);
                    String value0 = timeString;
                    String value1 = dateString;
                    int value = FrmLogin.pid;
                    String status = "Deleted the field id "+id+" from Patient";
                    Object[] values1 = {value1,value0,status,value};
                    String query1 = "INSERT INTO Audit(dateaudit,timeaudit,status,u_id) VALUES (?,?,?,?)";
                    PG.executeDML(query1, values1);
        }
    }//GEN-LAST:event_btnDeletePatientActionPerformed

    private void btnSubmitPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitPatientActionPerformed
       try {

            if (rdoBtnMale.isSelected()) {
                gender = "Male";
            } else {
                gender = "Female";
            }
            String firstname = txtPatientFname.getText();
            String MI = txtPatientMI.getText();
            String lastname = txtPatientLname.getText();
            String contact = txtContact.getText();
            String occupation = txtOccupation.getText();
            String diagnosis = txtDiagnosis.getText();
            String paddress = txtAddress.getText();
            String bdate = UIC.DateChooser.getDate(dcDOB);
            String philno = txtPhilHealthNo.getText();
            String age = txtPatientAge.getText();
            String service_avail = cmbServiceAvail.getSelectedItem().toString();
            String attendant = cmbAttendant.getSelectedItem().toString();
            if (bdate.equals("") ||txtPatientFname.equals("") || txtPatientMI.equals("") || txtPatientLname.equals("") || txtContact.equals("") || txtOccupation.equals("") || txtAddress.equals("") || bg.getSelection() == null) {
                JOptionPane.showMessageDialog(null, "Some fields are empty!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if (txtPatientMI.getText().length() < 1 || txtPatientMI.getText().length() > 1) {
                JOptionPane.showMessageDialog(null, "Middle Initial Only", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if(txtContact.getText().length() < 11 || txtContact.getText().length() > 11){
                JOptionPane.showMessageDialog(null,"11 digits Only","ERROR",JOptionPane.ERROR_MESSAGE);
                return;
            }else if(txtPhilHealthNo.equals("") || txtDiagnosis.equals("")){
                philno = null;
                diagnosis = null;
            } 
            int response = JOptionPane.showConfirmDialog(null, "Do you want to Add Patient", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (response == 0) {
              if((txtPhilHealthNo.getText().length() < 12 && txtPhilHealthNo.getText().length() > 1) || txtPhilHealthNo.getText().length() > 12){
                JOptionPane.showMessageDialog(null, "12 digits only","ERROR",JOptionPane.ERROR_MESSAGE);
                return;
                } 
                Object[] values = {firstname, lastname, MI, age, gender, contact, bdate, paddress, occupation, philno, attendant, service_avail,diagnosis};
                String query = "INSERT INTO Patient (p_fname, p_lname, p_mi, p_age, p_gender, p_contact, p_birthdate, p_address, p_occupation ,philhealthno, attendant, service_avail,diagnosis) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                if (PG.executeDML(query, values)) {
                  JOptionPane.showMessageDialog(null, "Patient successfully added");
                    this.showTablePatient();
                    this.showTableAudit();
                    FrmMedicine fmed = new FrmMedicine();
                    fmed.setVisible(true);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "An error occured while creating new task.");
                }
                    Date currentDate = GregorianCalendar.getInstance().getTime();
                    DateFormat df = DateFormat.getDateInstance();
                    String dateString = df.format(currentDate);

                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String timeString = sdf.format(d);
                    String value0 = timeString;
                    String value1 = dateString;
                    int value = FrmLogin.pid;
                   
                    String status = "Inserted the field "+firstname+","+lastname+","+MI+","+age+","+contact+","+bdate+","+paddress+","+occupation+","+philno+" Into Personnel";
                    Object[] values1 = {value1,value0,status,value};
                    String query1 = "INSERT INTO Audit(dateaudit,timeaudit,status,u_id) VALUES (?,?,?,?)";
                    PG.executeDML(query1, values1); 

            }
        } catch (Exception e) {
            System.out.println("FileInputStream Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnSubmitPatientActionPerformed

    private void btnResetPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetPatientActionPerformed
       this.resetPatientForm();
    }//GEN-LAST:event_btnResetPatientActionPerformed

    private void btnUpdatePatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdatePatientActionPerformed
        int id = UIC.Table.getSelectedRow(tblPatient);
        
        if (rdoBtnMale.isSelected()) {
                gender = "Male";
            } else {
                gender = "Female";
            }
         
            String firstname = txtPatientFname.getText();
            String MI = txtPatientMI.getText();
            String lastname = txtPatientLname.getText();
            String age = txtPatientAge.getText();
            String contact = txtContact.getText();
            String philno = txtPhilHealthNo.getText();
            String bdate = UIC.DateChooser.getDate(dcDOB);
            String occupation = txtOccupation.getText();
            String address = txtAddress.getText();
            String diagnosis = txtDiagnosis.getText();
            String service_avail = cmbServiceAvail.getSelectedItem().toString();
            String attendant = cmbAttendant.getSelectedItem().toString();
            int reply = javax.swing.JOptionPane.showConfirmDialog(null, "Do you wish to update this account?", "UPDATE", JOptionPane.YES_NO_OPTION);
            if(reply == 0){
                Object[] values = {firstname,lastname, MI, age, gender, contact,bdate,address,occupation,philno,attendant,service_avail,diagnosis,id};
                String query = "UPDATE Personnel SET p_fname=?, p_lname=?, p_MI=?, p_age=?, p_gender=?, p_contact=?, p_birthdate=?, p_address=?, p_occupation=?, philhealthno=?, attendant=?, service_avail=?, diagnosis=? WHERE p_id=?";
                if(PG.executeDML(query, values)){
                    JOptionPane.showMessageDialog(null, "Patient Recods Successfully Updated!");
                    this.showTablePersonnel();
                }else{
                    JOptionPane.showMessageDialog(null, "Patient Update Error!");
                }
                 Date currentDate = GregorianCalendar.getInstance().getTime();
                    DateFormat df = DateFormat.getDateInstance();
                    String dateString = df.format(currentDate);

                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String timeString = sdf.format(d);
                    String value0 = timeString;
                    String value1 = dateString;
                    int value = FrmLogin.pid;
                    String status = "Updated the field "+firstname+","+lastname+","+MI+","+age+","+gender+","+contact+","+bdate+","+address+","+occupation+","+philno+","+attendant+","+service_avail+","+diagnosis+" Into Patient";
                    Object[] values1 = {value1,value0,status,value};
                    String query1 = "INSERT INTO Audit(dateaudit,timeaudit,status,u_id) VALUES (?,?,?,?)";
                    PG.executeDML(query1, values1);
            }else{
                JOptionPane.showMessageDialog(null, "Update cancelled!");
                this.showTablePatient();
                this.showTableAudit();
            }
    }//GEN-LAST:event_btnUpdatePatientActionPerformed

    private void btnDeleteActivityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActivityActionPerformed
      int reply = javax.swing.JOptionPane.showConfirmDialog(null, "Do you wish to delete this data?", "WARNING", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
      if(reply == 0){
        try{
            JOptionPane.showMessageDialog(null, "Successfully Deleted");
            PG.conn = getConnection(PG.url,PG.username,PG.password);
            PG.stmt = PG.conn.createStatement();
            String query = "TRUNCATE TABLE AUDIT; ALTER SEQUENCE audit_audit_id_seq RESTART WITH 1";
            ResultSet rs = PG.stmt.executeQuery(query);
        }catch(Exception e){
            System.out.println("Search Error:"+e.getMessage());
            }
      }else{
          JOptionPane.showMessageDialog(null, "Reset Cancelled");
      }
       this.showTableAudit();
    }//GEN-LAST:event_btnDeleteActivityActionPerformed

    private void tblPatientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPatientMouseClicked
        // TODO add your handling code here:
        int lpatient = UIC.Table.getSelectedRow(tblPatient);
        System.out.println("Clicked: " + lpatient);
        String[] columns = {"p_id","p_fname", "p_lname", "p_mi","p_age","p_gender","p_contact","p_birthdate","p_address","p_occupation","philhealthno","attendant","service_avail","diagnosis"};
        String whereClause = "p_id=" + lpatient;
        String[][] records = PG.executeDQL("Patient", columns, whereClause);

        txtPatientFname.setText(records[0][1]);
        txtPatientLname.setText(records[0][2]);
        txtPatientMI.setText(records[0][3]);
        txtPatientAge.setText(records[0][4]);
        String gender = records[0][5];
        if (gender.equals("Male")) {
            rdoBtnMale.setSelected(true);
            rdoBtnFemale.setSelected(false);
        } else {
            rdoBtnMale.setSelected(false);
            rdoBtnFemale.setSelected(true);
        }
        txtContact.setText(records[0][6]);
        txtAddress.setText(records[0][8]);
        txtOccupation.setText(records[0][9]);
        txtPhilHealthNo.setText(records[0][10]);
        txtDiagnosis.setText(records[0][13]);
        String attendant = records[0][11];
        if (attendant.equals(records[0][11])) {
            cmbPosition.setSelectedItem(records[0][11]);
        } else {
            cmbPosition.setSelectedItem(records[0][11]);
        }
        String myDate = records[0][7];
         try{
            Date dt = new SimpleDateFormat("MMM dd, YYYY").parse(myDate);
            dcDOB.setDate(dt);
        }catch(Exception e){
            System.out.println("Date Error: "+e);
        }
        String service = records[0][12];
        if(service.equals(records[0][12])){
            cmbServiceAvail.setSelectedItem(records[0][12]);
        }else{
            cmbServiceAvail.setSelectedItem(records[0][12]);
        }
        
       
    }//GEN-LAST:event_tblPatientMouseClicked

    private void cmbServiceAvailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbServiceAvailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbServiceAvailActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        pnlContent.removeAll();
        pnlContent.repaint();
        pnlContent.revalidate();

        pnlContent.add(pnlPrint);
        pnlContent.repaint();
        pnlContent.revalidate();
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnPrintSummaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintSummaryActionPerformed
       try{
           PG.conn = getConnection(PG.url,PG.username,PG.password);
           String reportName = "src\\com\\profile\\app\\MedicineAvailReport.jrxml";
           UIC.Report.viewReport(reportName);
           PG.conn.close();
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
       }
    }//GEN-LAST:event_btnPrintSummaryActionPerformed

    private void btnPrintServiceAvailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintServiceAvailActionPerformed
       try{
           PG.conn = getConnection(PG.url,PG.username,PG.password);
           String reportName = "src\\com\\profile\\app\\ServiceAvail.jrxml";
           UIC.Report.viewReport(reportName);
           PG.conn.close();
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
       }
    }//GEN-LAST:event_btnPrintServiceAvailActionPerformed

    private void txtSearchPatientKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchPatientKeyReleased
         String searchPatient = txtSearchPatient.getText();
        if(txtSearchPatient.equals("")){
            this.showTablePatient();
        }else{
            try{
                PG.conn = getConnection(PG.url,PG.username,PG.password);
                PG.stmt = PG.conn.createStatement();
                String whereClause = "p_fname LIKE '%"+searchPatient+"%' OR "
                        + "p_lname LIKE '%"+searchPatient+"%' OR p_mi LIKE '%"+searchPatient+"%' OR "
                        + "p_age LIKE '%"+searchPatient+"%' OR p_gender LIKE '%"+searchPatient+"%' OR "
                        + "p_contact LIKE '%"+searchPatient+"%' OR p_birthdate LIKE '%"+searchPatient+"%' OR p_address LIKE '%"+searchPatient+"%' OR "
                        + "p_occupation LIKE '%"+searchPatient+"%' OR philhealthno LIKE '%"+searchPatient+"%' OR attendant LIKE '%"+searchPatient+"%' OR "
                        + "service_avail LIKE '%"+searchPatient+"%' OR diagnosis LIKE '%"+searchPatient+"%'";
                String query = "SELECT P_ID, P_FNAME, P_LNAME, P_MI, P_AGE, P_GENDER, P_CONTACT, P_BIRTHDATE, P_ADDRESS, P_OCCUPATION, PHILHEALTHNO, ATTENDANT, SERVICE_AVAIL, DIAGNOSIS FROM Patient WHERE "+whereClause;
                ResultSet rs = PG.stmt.executeQuery(query);
                
                tblPatient.setModel(DbUtils.resultSetToTableModel(rs));
                PG.conn.close();
            }catch(Exception e){
                System.out.println("Search Error:"+e.getMessage());
            }

        }
    }//GEN-LAST:event_txtSearchPatientKeyReleased

    public Image stretchImage(String path, JLabel label) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(path);
        Image scaledImage = image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT);
        return scaledImage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmMainStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMainStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMainStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMainStaff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMainStaff().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeleteActivity;
    private javax.swing.JButton btnDeletePatient;
    private javax.swing.JButton btnHelp;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnPrintServiceAvail;
    private javax.swing.JButton btnPrintSummary;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnResetPatient;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnSubmitPatient;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUpdatePatient;
    private javax.swing.JComboBox<String> cmbAttendant;
    private javax.swing.JComboBox<String> cmbPosition;
    private javax.swing.JComboBox<String> cmbServiceAvail;
    private com.toedter.calendar.JDateChooser dcDOB;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblIMAGE;
    private javax.swing.JLabel lblTime;
    private javax.swing.JPanel pnlActivity;
    private javax.swing.JPanel pnlAddPatient;
    private javax.swing.JPanel pnlAddUser;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlHelp;
    private javax.swing.JPanel pnlHome;
    private javax.swing.JPanel pnlPrint;
    private javax.swing.JRadioButton rdoBtnFEMALE;
    private javax.swing.JRadioButton rdoBtnFemale;
    private javax.swing.JRadioButton rdoBtnMALE;
    private javax.swing.JRadioButton rdoBtnMale;
    private javax.swing.JTable tblAudit;
    private javax.swing.JTable tblMedAvail;
    private javax.swing.JTable tblPatient;
    private javax.swing.JTable tblPersonnel;
    private javax.swing.JTable tblServiceAvail;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtContact;
    private javax.swing.JTextArea txtDiagnosis;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstname;
    private javax.swing.JTextField txtLastname;
    private javax.swing.JTextField txtMiddleInitial;
    private javax.swing.JTextField txtOccupation;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtPatientAge;
    private javax.swing.JTextField txtPatientFname;
    private javax.swing.JTextField txtPatientLname;
    private javax.swing.JTextField txtPatientMI;
    private javax.swing.JTextField txtPhilHealthNo;
    private javax.swing.JTextField txtSearchActivity;
    private javax.swing.JTextField txtSearchPatient;
    private javax.swing.JTextField txtSearchPersonnel;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
