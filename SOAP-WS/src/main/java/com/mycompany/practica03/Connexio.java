/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alumne
 */
public class Connexio {
    
        public static Connection getConexio() {
        Connection conex = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            try {
                conex = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2"); 
            } catch (SQLException es){
                System.out.println("Failed Connection");
            }
        } catch (ClassNotFoundException ex){
            System.out.println("Driver error");
        }
        return conex;
    } 
    
    
    public static void closeconnection(Connection conex) {
        try {
            if (conex != null) {
                conex.close();
            } 
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    
    public static int MiraUsuari(String user, String password) {
        Connection con = Connexio.getConexio();
        try {
            if (con != null) {
                String query = "SELECT * FROM usuarios WHERE id_usuario = ? and password = ?";
                PreparedStatement statement = con.prepareStatement(query);
                statement.setString(1, user);
                statement.setString(2, password);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    if (rs.getString(1).equals(user) && rs.getString(2).equals(password)){ 
                        closeconnection(con);
                        return 1; 
                    }
                }
            } 
        } catch (Exception e) {
                System.err.println(e.getMessage());
        } 
        closeconnection(con);
        return 0;
    }
    
    
    public static int ModificarImatge(Image image) {
        Connection con = Connexio.getConexio();
        try {
            String query = "UPDATE IMAGE SET TITLE = ?, DESCRIPTION = ?, KEYWORDS = ?, AUTHOR = ?, CAPTURE_DATE = ?, FILENAME = ? WHERE ID = ?";
            
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, image.getTitle());
            statement.setString(2, image.getDescription());
            statement.setString(3, image.getKeywords());
            statement.setString(4, image.getAuthor());
            statement.setString(5, image.getCapture_date());
            statement.setString(6, image.getFilename());
            statement.setString(7, image.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return 0;
        }
        Connexio.closeconnection(con);
        return 1;
    }
    
    public static int EliminarImatge(int id){
        Connection con = Connexio.getConexio();
        int imatge_borrada = 0;
        try {
            Statement new_statement = con.createStatement();
            String query = "delete from image where id=" + id;
            imatge_borrada = new_statement.executeUpdate(query);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return imatge_borrada;
        }
        Connexio.closeconnection(con);
        return imatge_borrada;
    }    
    
    public static Image BuscarPerId(int id) {
        Connection con = Connexio.getConexio();
        Image image = new Image();
        try{
            Statement new_statement = con.createStatement();
            String query = "select * from image where id=" + id;
            ResultSet rs = new_statement.executeQuery(query);
            while(rs.next()) {
                image.setId(rs.getString("id"));
                image.setTitle(rs.getString("title"));
                image.setDescription(rs.getString("description"));
                image.setKeywords(rs.getString("keywords"));
                image.setAuthor(rs.getString("author"));
                image.setCreator(rs.getString("creator"));
                image.setStorage_date(rs.getString("storage_date"));
                image.setCapture_date(rs.getString("capture_date"));
                image.setFilename(rs.getString("filename"));
            }  
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        Connexio.closeconnection(con);
        return image;
    }
    
    
    public static int RegistrarImatge(Image image) {
        Connection con = Connexio.getConexio();
        try {
            String query = "INSERT INTO IMAGE (TITLE, DESCRIPTION, KEYWORDS, AUTHOR, CREATOR, CAPTURE_DATE, STORAGE_DATE, FILENAME) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
            LocalDate localDate = LocalDate.now();
            
            PreparedStatement statement; statement = con.prepareStatement(query);           
            statement.setString(1, image.getTitle());
            statement.setString(2, image.getDescription());
            statement.setString(3, image.getKeywords());
            statement.setString(4, image.getAuthor());
            statement.setString(5, image.getCreator());
            statement.setString(6, image.getCapture_date());
            statement.setString(7, dtf.format(localDate));
            statement.setString(8, image.getFilename());            
            statement.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return 0;
        }
        Connexio.closeconnection(con);
        return 1;
    }
    
    
    public static List<Image> BuscarPerTitol(String title) {
        Connection con = Connexio.getConexio();
        ArrayList<Image> list = new ArrayList<>(); 
        try{
            PreparedStatement new_statement = con.prepareStatement("select * from image where title like ?");
            String cerca = '%' + title + '%';
            new_statement.setString(1, cerca);
            ResultSet rs = new_statement.executeQuery();
            while(rs.next()) {
                Image image = new Image();
                image.setId(rs.getString("id"));
                image.setTitle(rs.getString("title"));
                image.setDescription(rs.getString("description"));
                image.setKeywords(rs.getString("keywords"));
                image.setAuthor(rs.getString("author"));
                image.setCreator(rs.getString("creator"));
                image.setStorage_date(rs.getString("storage_date"));
                image.setCapture_date(rs.getString("capture_date"));
                image.setFilename(rs.getString("filename"));
                list.add(image);
            }  
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        Connexio.closeconnection(con);
        return list;
    }
    
    
    public static List<Image> BuscarPerDataCreacio(String creaDate) {
        Connection con = Connexio.getConexio();
        ArrayList<Image> list = new ArrayList<>(); 
        try{
            PreparedStatement new_statement = con.prepareStatement("select * from image where storage_date like ?");
            String cerca = '%' + creaDate + '%';
            new_statement.setString(1, cerca);
            ResultSet rs = new_statement.executeQuery();
            while(rs.next()) {
                Image image = new Image();
                image.setId(rs.getString("id"));
                image.setTitle(rs.getString("title"));
                image.setDescription(rs.getString("description"));
                image.setKeywords(rs.getString("keywords"));
                image.setAuthor(rs.getString("author"));
                image.setCreator(rs.getString("creator"));
                image.setStorage_date(rs.getString("storage_date"));
                image.setCapture_date(rs.getString("capture_date"));
                image.setFilename(rs.getString("filename"));
                list.add(image);
            }  
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return list;
        }
        Connexio.closeconnection(con);
        return list;
    }
    
    public static List<Image> BuscarPerParaulaClau(String keywords) {
        Connection con = Connexio.getConexio();
        ArrayList<Image> list = new ArrayList<>(); 
        try{
            Statement new_statement = con.createStatement();
            String query = "select * from image where 1=1 AND keywords LIKE '%"+keywords+"%'";
            ResultSet resultado_busqueda = new_statement.executeQuery(query);
            while(resultado_busqueda.next()) {
                Image image = new Image();
                image.setId(resultado_busqueda.getString("id"));
                image.setTitle(resultado_busqueda.getString("title"));
                image.setDescription(resultado_busqueda.getString("description"));
                image.setKeywords(resultado_busqueda.getString("keywords"));
                image.setAuthor(resultado_busqueda.getString("author"));
                image.setCreator(resultado_busqueda.getString("creator"));
                image.setStorage_date(resultado_busqueda.getString("storage_date"));
                image.setCapture_date(resultado_busqueda.getString("capture_date"));
                image.setFilename(resultado_busqueda.getString("filename"));
                list.add(image);
            }  
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return list;
        }
        Connexio.closeconnection(con);
        return list;
    }
    
    public static List<Image> BuscarPerAutor(String author) {
        Connection con = Connexio.getConexio();
        ArrayList<Image> list = new ArrayList<>(); 
        try{
            PreparedStatement new_statement = con.prepareStatement("select * from image where author like ?");
            String cerca = '%' + author + '%';
            new_statement.setString(1, cerca);
            ResultSet resultado_busqueda = new_statement.executeQuery();
            while(resultado_busqueda.next()) {
                Image image = new Image();
                image.setId(resultado_busqueda.getString("id"));
                image.setTitle(resultado_busqueda.getString("title"));
                image.setDescription(resultado_busqueda.getString("description"));
                image.setKeywords(resultado_busqueda.getString("keywords"));
                image.setAuthor(resultado_busqueda.getString("author"));
                image.setCreator(resultado_busqueda.getString("creator"));
                image.setStorage_date(resultado_busqueda.getString("storage_date"));
                image.setCapture_date(resultado_busqueda.getString("capture_date"));
                image.setFilename(resultado_busqueda.getString("filename"));
                list.add(image);
            }  
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return list;
        }
        Connexio.closeconnection(con);
        return list;
    }
    
    public static List<Image> LlistarImatges() {
        Connection con = Connexio.getConexio();
        ArrayList<Image> list = new ArrayList<>(); 
        try{
            Statement new_statement = con.createStatement();
            String query = "select * from image where 1=1";
            ResultSet resultado_busqueda = new_statement.executeQuery(query);
            while(resultado_busqueda.next()) {
                Image image = new Image();
                image.setId(resultado_busqueda.getString("id"));
                image.setTitle(resultado_busqueda.getString("title"));
                image.setDescription(resultado_busqueda.getString("description"));
                image.setKeywords(resultado_busqueda.getString("keywords"));
                image.setAuthor(resultado_busqueda.getString("author"));
                image.setCreator(resultado_busqueda.getString("creator"));
                image.setStorage_date(resultado_busqueda.getString("storage_date"));
                image.setCapture_date(resultado_busqueda.getString("capture_date"));
                image.setFilename(resultado_busqueda.getString("filename"));
                list.add(image);
            }  
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return list;
        }
        Connexio.closeconnection(con);
        return list;
    }
}
