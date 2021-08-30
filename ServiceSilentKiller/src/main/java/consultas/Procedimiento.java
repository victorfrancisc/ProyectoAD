/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultas;

import java.sql.ResultSet;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author franc
 */
public class Procedimiento {

    ResultSet rs = null;
    Statement st = null;
    String opcion = "opcion";
    JSONArray array = new JSONArray();
    JSONObject inarray = new JSONObject();
    JSONObject alldata = new JSONObject();

    public void borr() {


        if (alldata.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                array.remove(i);
            }

            alldata.remove("datos");

        }

    }

    public String any_que(String consulta, String bu) {
        borr();
        Conectar c = new Conectar();
        String tomardato = "";
        int tama=0;
        try {
            st = c.getConnection().createStatement();
            rs = st.executeQuery(consulta);
            int con = 1;
            while (rs.next()) {

                tomardato = rs.getString(1) + "," + tomardato;

            }
            String str;
            str = tomardato.replaceAll("[( )]*", "");
            String partes[] = str.split(",");
         
            for (int j = 0; j < partes.length; j++) {
                  
                if(j%2==0){inarray = new JSONObject(); array.put(inarray);}
                inarray.put(opcion + con, partes[j]);              
                        
                if (con == Integer.valueOf(bu)) {
                    con = 0;
                }
                con++;
                 
            }

            alldata.put("datos", array);
            return alldata.toString();
        } catch (Exception e) {
         //   System.out.println(e.getMessage());
        } finally {
            try {
                if (st.getConnection() != null) {

                    c.closeconnection();
                }
            } catch (Exception e) {
               // System.out.println(e.getMessage());
            }
        }
        return "";
    }

    public static void main(String[] args) {

       // Procedimiento po = new Procedimiento();
    //   String consulta = "SELECT public.consultactividadporfecha ('ABC001','2021-08-25')";
        // String consulta =  "select public.sensores ('" + "ABC001" + "')";
        //   String consulta = "select public.consultactividadmovimiento('ABC001')";
     // System.out.println(po.any_que(consulta,"2"));
    }

}
