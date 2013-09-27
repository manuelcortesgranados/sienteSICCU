/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cobra;

import cobra.Supervisor.FacesUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author carlosloaiza
 */
public class Geocodeimplementacion {

    /*Geocode request URL. Here see we are passing "json" it means we will get the output in JSON format.
     * You can also pass "xml" instead of "json" for XML output.
     * For XML output URL will be "http://maps.googleapis.com/maps/api/geocode/xml";
     */
    private static final String URL = "http://maps.googleapis.com/maps/api/geocode/json";

    /*
     * Here the fullAddress String is in format like "address,city,state,zipcode". Here address means "street number + route" .
     *
     */
    public String getJSONByGoogle(String fullAddress) throws MalformedURLException, UnsupportedEncodingException, IOException {

        /*
         * Create an java.net.URL object by passing the request URL in constructor.
         * Here you can see I am converting the fullAddress String in UTF-8 format.
         * You will get Exception if you don't convert your address in UTF-8 format. Perhaps google loves UTF-8 format. :)
         * In parameter we also need to pass "sensor" parameter.
         * sensor (required parameter) — Indicates whether or not the geocoding request comes from a device with a location sensor. This value must be either true or false.
         */
        URL url = new URL(URL + "?address=" + URLEncoder.encode(fullAddress, "UTF-8") + "&sensor=false");

// Open the Connection
        URLConnection conn = url.openConnection();

//This is Simple a byte array output stream that we will use to keep the output data from google.
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);

// copying the output data from Google which will be either in JSON or XML depending on your request URL that in which format you have requested.
        IOUtils.copy(conn.getInputStream(), output);

//close the byte array output stream now.
        output.close();

        return output.toString(); // This returned String is JSON string from which you can retrieve all key value pair and can save it in POJO.
    }

    public String obtenerDireccionxLatyLong(String latitud, String longitud) {
        String direccion = "";
        try {
            String dirgoogle = "";

            URL url = new URL(URL + "?latlng=" + latitud + "," + longitud + "&sensor=false");
           
            URLConnection conn = url.openConnection();

            //This is Simple a byte array output stream that we will use to keep the output data from google.
            ByteArrayOutputStream output = new ByteArrayOutputStream(1024);

            // copying the output data from Google which will be either in JSON or XML depending on your request URL that in which format you have requested.
            try {
                IOUtils.copy(conn.getInputStream(), output);
                dirgoogle = output.toString("UTF-8");
                output.close();

                JSONObject json = (JSONObject) JSONSerializer.toJSON(dirgoogle);

                JSONArray res = json.getJSONArray("results");

                JSONObject son = res.getJSONObject(0);

                direccion = son.getString("formatted_address");
            } catch (IOException ex) {
                output.close();
                direccion = "Faltante";
            }

            //close the byte array output stream now.
            return direccion;
        } catch (MalformedURLException ex) {
            Logger.getLogger(Geocodeimplementacion.class.getName()).log(Level.SEVERE, null, ex);
            return direccion;
        } catch (IOException ex) {
            Logger.getLogger(Geocodeimplementacion.class.getName()).log(Level.SEVERE, null, ex);
            return direccion;
        }

    }
    
    
    public Marcador obtenerMarcadorporDireccion(String fullAddress)  {
        Marcador marca= new Marcador();        
        try {
            /*
             * Create an java.net.URL object by passing the request URL in constructor.
             * Here you can see I am converting the fullAddress String in UTF-8 format.
             * You will get Exception if you don't convert your address in UTF-8 format. Perhaps google loves UTF-8 format. :)
             * In parameter we also need to pass "sensor" parameter.
             * sensor (required parameter) — Indicates whether or not the geocoding request comes from a device with a location sensor. This value must be either true or false.
             */
            URL url = new URL(URL + "?address=" + URLEncoder.encode(fullAddress, "UTF-8") + "&sensor=false");

    // Open the Connection
            URLConnection conn = url.openConnection();

    //This is Simple a byte array output stream that we will use to keep the output data from google.
            ByteArrayOutputStream output = new ByteArrayOutputStream(1024);

    // copying the output data from Google which will be either in JSON or XML depending on your request URL that in which format you have requested.
            IOUtils.copy(conn.getInputStream(), output);

    //close the byte array output stream now.
            String dirgoogle = "";
            dirgoogle = output.toString("UTF-8");          
            
            output.close();
            JSONObject json = (JSONObject) JSONSerializer.toJSON(dirgoogle);

            JSONArray res = json.getJSONArray("results");
            if (res.size() !=0){
            JSONObject son = res.getJSONObject(0);
    //le pone la direccion
            marca.setAddress(son.getString("formatted_address"));
            
            
            JSONObject songeometry=  son.getJSONObject("geometry").getJSONObject("location");
            
            
            marca.setLatitude(songeometry.getString("lat"));
            marca.setLongitude(songeometry.getString("lng"));            
            }else{
                FacesUtils.addErrorMessage("La Direccion ingresada es erronea");
                marca = null;
            }

             // This returned String is JSON string from which you can retrieve all key value pair and can save it in POJO.
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Geocodeimplementacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Geocodeimplementacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Geocodeimplementacion.class.getName()).log(Level.SEVERE, null, ex);
        }
       
            return marca;
        
        
        
    }

}
