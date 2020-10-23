/*
 * REST SERVICE
 */
package py.una.pol.personas.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import py.una.pol.personas.model.Location;
import py.una.pol.personas.model.LocationQuery;
import py.una.pol.personas.model.Persona;
import py.una.pol.personas.model.Transport;

import py.una.pol.personas.service.LTService;

/**
 * JAX-RS Example
 * <p/>
 * Esta clase produce un servicio RESTful para leer y escribir contenido de personas
 */
@Path("/LT")
@RequestScoped
public class LTRESTService {

    @Inject
    private Logger log;

    @Inject
    LTService ltService;

    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public List<Transport> listar() { return ltService.listTransport(); }

    /**
     * Creates a new member from the values provided. Performs validation, and will return a JAX-RS response with either 200 ok,
     * or with a map of fields, and related errors.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/record")
    public Response recordTransport( Transport t ) {
        Response.ResponseBuilder builder = null;

        try {
            ltService.recordTransport( t );
            // Create an "ok" response
            //builder = Response.ok();
            builder = Response.status(201).entity("Movil registrado exitosamente");

        } catch (SQLException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("bd-error", e.getLocalizedMessage());
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/recordLocation")
    public Response recordLocation( Location l ) {

        Response.ResponseBuilder builder = null;

        try {
            ltService.specifyLocation( l );
            // Create an "ok" response
            //builder = Response.ok();
            builder = Response.status(201).entity("Movil en Ubicacion registrado exitosamente");

        } catch (SQLException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("bd-error", e.getLocalizedMessage());
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    @POST
    @Path("/near")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Location> nearLocation( LocationQuery query) {
        List<Location> lista = new ArrayList<>();
        try {
           lista = ltService.nearLocation( query );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (lista  == null) {
            log.info("No se encontro moviles cercanos ");
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        log.info("Moviles Encontrados");
        return lista;
    }

}
