/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javariesoft;

/**
 *
 * @author Ervan Asri
 */
public class global {
    public static String IPADDRESS="";
    public static String URL="http://localhost:8080/DeanWeb";
    public static String REPORT="http://localhost:8080/DeanWeb/report";

    public global(String ipaddress,String url,String report) {
            IPADDRESS = ipaddress;
            REPORT = report;
            URL = url; 
    }
}
