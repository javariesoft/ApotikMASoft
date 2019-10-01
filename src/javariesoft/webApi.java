/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javariesoft;

/**
 *
 * @author erwadi
 */
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintException;
import javax.print.PrintService;
import jzebra.PrintRaw;
import jzebra.PrintServiceMatcher;

public class webApi {

    private final String USER_AGENT = "Mozilla/5.0";

    public List getData(String turl) {
        List data = new ArrayList();
        try {
            //"http://localhost/trunk/index.php/webapi/list?model=kirim"
            URL url = new URL(turl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                //System.out.println(output);                
//                JsonObject jsonObject = JsonObject.readFrom( output );
                if (output.equals("kosong")) {
                    data=null;
                } else {
                    JsonArray jsonArray = JsonArray.readFrom(output);
                    int i = 0;
                    for (JsonValue value : jsonArray) {
                        //System.out.println(value);
                        data.add(i, value);
                        i++;
                    }
                }
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }
        return data;
    }

    public String getString(String data) {
        String hasil = "";
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) != '"') {
                hasil += data.charAt(i);
            }

        }
        return hasil;
    }

    public String cetak(String data, int panjang, int align) {
        String hasil = data;
        if (align == 0) {
            for (int i = data.length(); i < panjang; i++) {
                hasil += " ";
            }
        } else if (align == 1) {
            String a = "";
            for (int i = 0; i < (10 - data.length()); i++) {
                a += " ";
            }
            hasil = a + hasil;
        }
        return hasil;
    }

    public String sendData(String url, String urlParameters) throws Exception {
        String hasil = "";
        //String url = "http://localhost/trunk/index.php/webapi/coba";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        //String urlParameters = "{\"qty\":100,\"name\":\"iPad 4\"}";
        //String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        hasil = response.toString();
        return hasil;
    }

    public static void main(String[] args) {
        webApi n = new webApi();
        Iterator iter = n.getData("http://localhost/trunk/webapi.php?model=kirim&idw=1").iterator();
        //String printer = "zebra";  // This should match your printer name from Step 2
        PrintService ps = PrintServiceMatcher.findPrinter("zebra");
        PrintRaw p = new PrintRaw();
        try {
            p.append("No    Kode Barang    Nama Barang \r\n");
            p.append("-------------------------------- \r\n");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(webApi.class.getName()).log(Level.SEVERE, null, ex);
        }
        int no = 0;
        while (iter.hasNext()) {
            no++;
            JsonObject dt = (JsonObject) iter.next();
            String cd_product = n.getString(dt.asObject().get("cd_product").asString());
            String nm_product = n.getString(dt.asObject().get("nm_product").asString());
            //System.out.println(no + " " + cd_product + " " + nm_product + "\r\n");

            try {
                p.append(n.cetak("" + no, 4, 0) + "" + n.cetak(cd_product, 15, 0) + "" + n.cetak(nm_product, 30, 0) + "\r\n");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(webApi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        p.setOutputPath("c:\\temp\\coba.txt");
        try {
            //if(ps!=null){
            //    p.print();
            //}
            p.printToFile();
        } catch (Exception ex) {
            Logger.getLogger(webApi.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

}
