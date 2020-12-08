package com.frgp.remember.Base.Data;

public class DatosBD {

    //Información de la BD
    public static String host = "freedb.tech";
    public static String port = "3306";
    public static String nameBD = "freedbtech_remember_me";
        public static String user = "freedbtech_pa2_2020";
    public static String pass = "pa2_2020";


    //Información para la conexion
    public static String urlMySQL = "jdbc:mysql://" + host + ":" + port + "/" + nameBD;
    public static String driver = "com.mysql.jdbc.Driver";


}
