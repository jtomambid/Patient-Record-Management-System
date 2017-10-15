/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profile.app;

/***
 * Class Name: UIC
 * Class Description: Yet Another Java Helper Class for Desktop Dev't
 * Requirements:
 * (a) Java Development Kit 7 (suggested version jdk 1.8)
 * (b) Netbeans IDE 8 and below (suggested version 8.2)
 * (c) Project > Libraries > Add JAR/Folder > jcalendar.jar
 */

public class UIC {
    
    public static class Frame{
        public static void setDefault(javax.swing.JFrame frame){
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
        }
    }
    
    public static class Table{
        public static void setModel(javax.swing.JTable table, String[][] records, String[] columns){
            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(records, columns){
                @Override
                public boolean isCellEditable(int row, int column) {return false;}
            };
            table.setModel(model);
        }
        public static int getSelectedRow(javax.swing.JTable table){
            try{
                return java.lang.Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
            }
            catch(Exception e){
                return 0;
            }
        }
    }
    
    public static class DateChooser{
        //https://stackoverflow.com/questions/30235183/how-do-we-disable-editing-options-on-jdatechooser
        public static void setReadOnly(com.toedter.calendar.JDateChooser chooser){
            com.toedter.calendar.JTextFieldDateEditor editor = (com.toedter.calendar.JTextFieldDateEditor) chooser.getDateEditor();
            editor.setEditable(false);
        }

        //https://stackoverflow.com/questions/21012751/get-jdatechooser-date-to-jlabel
        public static String getDate(com.toedter.calendar.JDateChooser chooser){
            java.util.Date date = chooser.getDate();
            return java.text.DateFormat.getDateInstance().format(date);
        }    
    }
     public static class Report{
        public static void viewReport(String reportName){
            try{
                net.sf.jasperreports.engine.design.JasperDesign jasperDesign = net.sf.jasperreports.engine.xml.JRXmlLoader.load(reportName);
                net.sf.jasperreports.engine.JasperReport jasperReport = net.sf.jasperreports.engine.JasperCompileManager.compileReport(jasperDesign);
                net.sf.jasperreports.engine.JasperPrint jasperPrint = net.sf.jasperreports.engine.JasperFillManager.fillReport(jasperReport, null, PG.conn);
                net.sf.jasperreports.view.JasperViewer.viewReport(jasperPrint, false);             
            }catch(Exception e){
                System.out.println("iReport Error: " + e.getMessage());
            }    
        }
        public static void viewReport(javax.swing.JScrollPane jspReport, String reportName){            
            try{
                java.util.HashMap parameter = new java.util.HashMap();
                java.io.File reportFile = new java.io.File(reportName);          

                net.sf.jasperreports.engine.design.JasperDesign jasperDesign = net.sf.jasperreports.engine.xml.JRXmlLoader.load(reportFile);
                net.sf.jasperreports.engine.JasperReport jReport = net.sf.jasperreports.engine.JasperCompileManager.compileReport(jasperDesign);
                net.sf.jasperreports.engine.JasperPrint jPrint = net.sf.jasperreports.engine.JasperFillManager.fillReport(jReport, parameter, PG.conn);
                net.sf.jasperreports.view.JRViewer viewer = new net.sf.jasperreports.view.JRViewer(jPrint);
                viewer.setOpaque(true);
                viewer.setVisible(true);
                jspReport.add(viewer);
                jspReport.setViewportView(viewer);            
            }catch(Exception e){
                System.out.println("iReport Error: " + e.getMessage());
            }
        }
    }
}
