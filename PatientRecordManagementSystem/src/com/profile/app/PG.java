/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profile.app;

import java.sql.ResultSet;
import javax.swing.JLabel;


public class PG {
    //Static Variables
    static java.sql.Connection conn  = null;
    static java.sql.Statement stmt = null;
    static java.sql.ResultSet rs = null;
    static java.sql.PreparedStatement pstmt = null;
    static String url = "jdbc:postgresql://localhost:5432/PatientRecordManagement";
    static String username = "postgres";
    static String password = "admin";
    static String className = "org.postgresql.Driver";
    static String error = "";
    
    //Open DB Session Method
    public static boolean openDB(){
        boolean result = false;
        try{
            Class.forName(className);
            conn = java.sql.DriverManager.getConnection(url,username,password);
            
            //System.out.println("Open DB Success: DB session connected.");
            result = true;
        }
        catch(Exception e){
            error = e.getMessage();
            System.out.println("Open DB Failed: " + e.getMessage());
        } 
        return result;
    }

    //Close DB Session Method
    public static boolean closeDB(){
        boolean result = false;
        try{
            conn.close();

            System.out.println("Session has been executed successfully!");
            result = true;
        }
        catch(Exception e){
            error = e.getMessage();
            System.out.println("Close DB Failed: " + e.getMessage());
        }
        return result;
    }
    
    //Method for INSERT, UPDATE and DELETE statements
    public static boolean executeDML(String query){
        boolean result = false;
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
            result = true;
        }
        catch(Exception e){
            error = e.getMessage();
            System.out.println("Execute DML Error: " + e.getMessage());
            System.out.println("Query: " + query);
        }
        return result;
    }
    
    //Overloaded Method for INSERT, UPDATE and DELETE statments
    public static boolean executeDML(String query, Object[] values){
        boolean result = false;
        if(openDB()){
            try{
                pstmt = null;
                pstmt = conn.prepareStatement(query);
                for(int i=1,j=0;i<=values.length;i++,j++){
                    pstmt.setObject(i, values[j]);
                }
                pstmt.executeUpdate();
                result = true;
            }
            catch(Exception e){
                error = e.getMessage();
                System.out.println("Execute DML Error: " + e.getMessage());
                System.out.println("Query: " + query);
            }
            closeDB();
        }
        return result;
    }

    //Read Image Method
    public static byte[] read(String table, String column, int id, JLabel label){
        byte[] buffer = null;
        if(openDB()){
            try{
                String query = "SELECT " + column +" FROM " + table + " WHERE id=?"; 
                ResultSet rs = null;
                java.sql.PreparedStatement pstmt = null;            

                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, id);

                rs = pstmt.executeQuery();

                if(rs.next()){
                    buffer = rs.getBytes("image");
                }else{
                    label.setIcon(null);
                }
                    
                
            }
            catch(Exception e){
                System.out.println("Read Error: " + e.getMessage());
            }
            
            closeDB();
        }
        
        return buffer;
        
        
    }

    // Method for SELECT statements
    public static String[][] executeDQL(String table, String[] columns, String where){
        String[][] records = null;
        if(openDB()){
            try{
                String[] w = where.split(";");
                
                stmt = conn.createStatement();
                
                //Count total rows
                rs = stmt.executeQuery("SELECT count(*) FROM " + table + " WHERE " + w[0]);
                int totalRows = 0;
                if(rs.next())totalRows = rs.getInt(1);
                
                //Format columns
                String cols = "";
                for (int i=0;i<columns.length;i++) {
                    cols += columns[i];
                    if((i+1)!=columns.length)cols+=", ";
                }
                
                //Execute DML Query
                rs = stmt.executeQuery("SELECT "+ cols +" FROM " + table + " WHERE " + w[0]);
                java.sql.ResultSetMetaData rsmd = rs.getMetaData();
                
                //Count total columns
                int totalColumns = rsmd.getColumnCount();
                
                //Initialize 2D Array "records" with totalRows by totalColumns
                records = new String[totalRows][totalColumns];
                
                //Retrieve the record and store it to 2D Array "records"
                int row=0;
                while(rs.next()){                
                    for(int col=0,index=1;col<totalColumns;col++,index++){
                        records[row][col] = rs.getObject(index).toString();
                    }
                    row++;
                }
            }
            catch(Exception e){
                error = e.getMessage();
                System.out.println("Execute DQL Error: " + e.getMessage());
            }
            closeDB();
        }
        return records;
    } 
    
    public static String[][] showTablePatient(String table, String[] columns, String where){
        String[][] records = null;
        if(openDB()){
            try{
                String[] w = where.split(";");
                
                stmt = conn.createStatement();
                
                //Count total rows
                rs = stmt.executeQuery("SELECT count(*) FROM " + table + " WHERE " + w[0]);
                int totalRows = 0;
                if(rs.next())totalRows = rs.getInt(1);
                
                //Format columns
                String cols = "";
                for (int i=0;i<columns.length;i++) {
                    cols += columns[i];
                    if((i+1)!=columns.length)cols+=", ";
                }
                
                //Execute DML Query
                rs = stmt.executeQuery("SELECT "+ cols +" FROM " + table + "INNER JOIN  WHERE " + w[0]);
                java.sql.ResultSetMetaData rsmd = rs.getMetaData();
                
                //Count total columns
                int totalColumns = rsmd.getColumnCount();
                
                //Initialize 2D Array "records" with totalRows by totalColumns
                records = new String[totalRows][totalColumns];
                
                //Retrieve the record and store it to 2D Array "records"
                int row=0;
                while(rs.next()){                
                    for(int col=0,index=1;col<totalColumns;col++,index++){
                        records[row][col] = rs.getObject(index).toString();
                    }
                    row++;
                }
            }
            catch(Exception e){
                error = e.getMessage();
                System.out.println("Execute DQL Error: " + e.getMessage());
            }
            closeDB();
        }
        return records;
    } 
    
    //Demo Method
    public static void main(String [] args){
        
        //Sample code snippet for INSERT DML Statement
        byte[] image = null;
        Object[] tasks = {"James", "Tomambid","P","shenz@gmail.com","Male","admin","admin123","Midwife",image};
        String query = "INSERT INTO Personnel(u_fname,u_lname,u_mi,email,gender,username,password,position,image) VALUES (?,?,?,?,?,?,?,?,?)";
        if(PG.executeDML(query, tasks)) System.out.println("Query Ok!");
        else System.out.println("Query Not Ok!");
       
        
        /*
        //Sample code snippet for UPDATE DML Statement
        Object[] values = {"Task B", "YES", 13};
        String query = "UPDATE task SET name=? ,isdone=? WHERE id=?";
        if(PG.executeDML(query, values)) System.out.println("Query Ok!");
        else System.out.println("Query Not Ok!");        
        */
        
        /*
        //Sample code snippet for DELETE DML Statement
        Object[] values = {13};
        String query = "DELETE FROM task WHERE id=?";
        if(PG.executeDML(query, values)) System.out.println("Query Ok!");
        else System.out.println("Query Not Ok!");        
        */
        
        /*
        //Sample code snippet for SELECT DQL Statement
        String[] columns = {"name","isdone"};
        String whereClause = "1=1";
        String[][] records = PG.executeDQL("task", columns, whereClause);
        if(records==null || records.length==0)System.out.println("No record found!");
        else System.out.println(records.length + " record(s) found!");
        */
    }
}

