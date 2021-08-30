/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webServices;

import consultas.Procedimiento;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import java.net.URI;

/**
 * REST Web Service
 *
 * @author franc
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;
    Procedimiento po = new Procedimiento();

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of webServices.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    @Path("monoxido")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public Response getXml(String data, @Context UriInfo uriInfo) {
        URI uri = uriInfo.getAbsolutePath();
        JSONObject nuevo = new JSONObject(data);
        String consulta = "select public.ingresoActivida ('" + nuevo.getString("identificador") + "','" + nuevo.getString("movimiento") + "','" + nuevo.getInt("flama") + "','" + nuevo.getInt("Monoxido") + "')";
        // System.out.println(po.any_que(consulta, nuevo.getString("bucle")));
        return Response.created(uri).entity(po.any_que(consulta, nuevo.getString("bucle"))).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers").build();

    }

    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verificarUser(String data, @Context UriInfo uriInfo) {
        URI uri = uriInfo.getAbsolutePath();

        JSONObject nuevo = new JSONObject(data);
        String consulta = "select public.consultaUsuario('" + nuevo.getString("usuario") + "','" + nuevo.getString("clave") + "')";
        // System.out.println(po.any_que(consulta, nuevo.getString("bucle")));
        return Response.created(uri).entity(po.any_que(consulta, nuevo.getString("bucle"))).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers").build();

    }

    @Path("ingresar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getXmlIngresar(String data, @Context UriInfo uriInfo) {
        URI uri = uriInfo.getAbsolutePath();
        JSONObject nuevo = new JSONObject(data);
        String consulta = "select public.ingresoUsuario ('" + nuevo.getString("usuario") + "','" + nuevo.getString("clave") + "','" + nuevo.getString("idArduino") + "')";
        //   System.out.println(po.any_que(consulta, nuevo.getString("bucle")));
        return Response.created(uri).entity(po.any_que(consulta, nuevo.getString("bucle"))).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers").build();

    }

    @Path("sensores")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getXmlsensores(String data, @Context UriInfo uriInfo) {

        URI uri = uriInfo.getAbsolutePath();
        JSONObject nuevo = new JSONObject(data);
        String consulta = "select public.sensores('" + nuevo.getString("idArduino") + "')";
        // System.out.println(po.any_que(consulta, nuevo.getString("bucle")));
        return Response.created(uri).entity(po.any_que(consulta, nuevo.getString("bucle"))).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers").build();

    }

    @Path("notificacion")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getXmlNotificacion(String data, @Context UriInfo uriInfo) {

        URI uri = uriInfo.getAbsolutePath();
        JSONObject nuevo = new JSONObject(data);
        String consulta = "select public.consultActividad ('" + nuevo.getString("idArduino") + "')";
        //   System.out.println(po.any_que(consulta, nuevo.getString("bucle")));
        return Response.created(uri).entity(po.any_que(consulta, nuevo.getString("bucle"))).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers").build();

    }

    @Path("movimiento")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getXmlMovimiento(String data, @Context UriInfo uriInfo) {
        URI uri = uriInfo.getAbsolutePath();
        JSONObject nuevo = new JSONObject(data);
        String consultas = "select public.consultActividadMovimiento ('" + nuevo.get("idArduino").toString() + "')";
        //    System.out.println(po.any_que(consultas, nuevo.getString("bucle")));
        return Response.created(uri).entity(po.any_que(consultas, nuevo.getString("bucle"))).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers").build();

    }

    @Path("fechas")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getXmlFecha(String data, @Context UriInfo uriInfo) {

        URI uri = uriInfo.getAbsolutePath();
        JSONObject nuevo = new JSONObject(data);
        String consultas = "select public.consultActividadporFecha ('" + nuevo.getString("idArduino") + "','" + nuevo.getString("fecha") + "')";
        //    System.out.println(po.any_que(consultas, nuevo.getString("bucle")));
        return Response.created(uri).entity(po.any_que(consultas, nuevo.getString("bucle"))).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers").build();

    }

    @Path("FechaMonox")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getXmlMoNOXI(String data, @Context UriInfo uriInfo) {

        URI uri = uriInfo.getAbsolutePath();
        JSONObject nuevo = new JSONObject(data);
        String consultas = "select public.consultActividadMonoxporFecha ('" + nuevo.getString("idArduino") + "','" + nuevo.getString("fecha") + "')";
        //  System.out.println(po.any_que(consultas, nuevo.getString("bucle")));
        return Response.created(uri).entity(po.any_que(consultas, nuevo.getString("bucle"))).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers").build();

    }

}
