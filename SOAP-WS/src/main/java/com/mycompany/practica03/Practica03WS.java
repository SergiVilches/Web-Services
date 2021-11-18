/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica03;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author alumne
 */
@WebService(serviceName = "Practica03WS")
public class Practica03WS {

    /**
     * Web service operation
     * @param image
     * @return int
     */
    @WebMethod(operationName = "RegisterImage")
    public int RegisterImage(@WebParam(name = "image") Image image) {
        try {
            return Connexio.RegistrarImatge(image);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }

    /**
     * Web service operation
     * @param image
     * @return int
     */
    @WebMethod(operationName = "ModifyImage")
    public int ModifyImage(@WebParam(name = "image") Image image) {
        try {
            return Connexio.ModificarImatge(image);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }
    
    /**
     * Web service operation
     * @param id
     * @return int
     */
    @WebMethod(operationName = "DeleteImage")
    public int DeleteImage(@WebParam(name = "id") int id) {
        try {
            return Connexio.EliminarImatge(id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }
    
        
    /**
     * Web service operation
     * @return int
     */
    @WebMethod(operationName = "ListImage")
    public List<Image> ListImage() {
        try {
            return Connexio.LlistarImatges();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Web service operation
     * @param id
     * @return int
     */
    @WebMethod(operationName = "SearchbyId")
    public Image SearchbyId(@WebParam(name = "id") int id) {
        try {
            return Connexio.BuscarPerId(id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Web service operation
     * @param title
     * @return 
     */
    @WebMethod(operationName = "SearchbyTitle")
    public List<Image> SearchbyTitle(@WebParam(name = "title") String title) {
        try {
            return Connexio.BuscarPerTitol(title);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Web service operation
     * @param creaDate
     * @return 
     */
    @WebMethod(operationName = "SearchbyCreaDate")
    public List SearchbyCreaDate(@WebParam(name = "creaDate") String creaDate) {
        try {
            return Connexio.BuscarPerDataCreacio(creaDate);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * Web service operation
     * @param keywords
     * @return 
     */
    @WebMethod(operationName = "SearchbyKeywords")
    public List SearchbyKeywords(@WebParam(name = "keywords") String keywords) {
        try {
            return Connexio.BuscarPerParaulaClau(keywords);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * Web service operation
     * @param author
     * @return 
     */
    @WebMethod(operationName = "SearchbyAuthor")
    public List SearchbyAuthor(@WebParam(name = "author") String author) {
        try {
            return Connexio.BuscarPerAutor(author);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Web service operation
     * @param username
     * @param password
     * @return 
     */
    @WebMethod(operationName = "CheckUser")
    public int CheckUser(@WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        try {
            return Connexio.MiraUsuari(username, password);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }

}

