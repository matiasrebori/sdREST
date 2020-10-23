package py.una.pol.personas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import py.una.pol.personas.model.Persona;

@Stateless
public class PersonaDAO {
 
	
    @Inject
    private Logger log;
    
	/**
	 * 
	 * @param condiciones 
	 * @return
	 */
	public List<Persona> seleccionar() {
		String query = "SELECT cedula, nombre, apellido FROM persona ORDER BY nombre";
		
		List<Persona> lista = new ArrayList<Persona>();
		
		Connection conn = null; 
        try 
        {
        	conn = Bd.connect();
        	ResultSet rs = conn.createStatement().executeQuery(query);

        	while(rs.next()) {
        		Persona p = new Persona();
        		p.setCedula(rs.getLong(1));
        		p.setNombre(rs.getString(2));
        		p.setApellido(rs.getString(3));
        		
        		lista.add(p);
        	}
        	
        } catch (SQLException ex) {
            log.severe("Error en la seleccion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
		return lista;

	}
	
	public Persona seleccionarPorCedula(long cedula) {
		String SQL = "SELECT cedula, nombre, apellido FROM persona WHERE cedula = ? ";
		
		Persona p = null;
		
		Connection conn = null; 
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
        	pstmt.setLong(1, cedula);
        	
        	ResultSet rs = pstmt.executeQuery();

        	while(rs.next()) {
        		p = new Persona();
        		p.setCedula(rs.getLong(1));
        		p.setNombre(rs.getString(2));
        		p.setApellido(rs.getString(3));
        	}
        	
        } catch (SQLException ex) {
        	log.severe("Error en la seleccion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
		return p;

	}
	
	
    public long insertar(Persona p) throws SQLException {

        String SQL = "INSERT INTO persona(cedula, nombre,apellido) "
                + "VALUES(?,?,?)";
 
        long id = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, p.getCedula());
            pstmt.setString(2, p.getNombre());
            pstmt.setString(3, p.getApellido());
 
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                	throw ex;
                }
            }
        } catch (SQLException ex) {
        	throw ex;
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
        	
        return id;
    	
    	
    }
	

    public long actualizar(Persona p) throws SQLException {

        String SQL = "UPDATE persona SET nombre = ? , apellido = ? WHERE cedula = ? ";
 
        long id = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, p.getNombre());
            pstmt.setString(2, p.getApellido());
            pstmt.setLong(3, p.getCedula());
 
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
        	log.severe("Error en la actualizacion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
        return id;
    }
    
    public long borrar(long cedula) throws SQLException {

        String SQL = "DELETE FROM persona WHERE cedula = ? ";
 
        long id = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, cedula);
 
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                	log.severe("Error en la eliminación: " + ex.getMessage());
                	throw ex;
                }
            }
        } catch (SQLException ex) {
        	log.severe("Error en la eliminación: " + ex.getMessage());
        	throw ex;
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        		throw ef;
        	}
        }
        return id;
    }
    

}
