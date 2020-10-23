package py.una.pol.personas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import py.una.pol.personas.model.Persona;
import py.una.pol.personas.model.Transport;
@Stateless
public class TransportDAO {


    @Inject
    private Logger log;


    public void insert(Transport t) throws SQLException {

        String SQL;
        SQL = "INSERT INTO movil (tipo) VALUES(?)";
        Connection conn = null;

        try
        {
            //conectar a la db
            conn = Bd.connect();
            //log.info("Conexion a la db exitosa");
            //preparar datos a insertar
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, t.getType() );
            //ejecutar query
            int affectedRows = pstmt.executeUpdate();
            //rows affected
            //System.out.println( affectedRows );
            System.out.println("fila insertada en tabla movil->" + t.getType() );

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
    }

    public List<Transport> listTransport() {
        String query = "SELECT id_movil, tipo FROM movil ORDER BY id_movil";

        List<Transport> lista = new ArrayList<Transport>();

        Connection conn = null;
        try
        {
            conn = Bd.connect();
            ResultSet rs = conn.createStatement().executeQuery(query);

            while(rs.next()) {
                Transport t = new Transport(1);
                t.setId( rs.getInt(1) );
                t.setType( rs.getString(2) );

                lista.add(t);
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

    public static void main(String[] args) throws SQLException {
        Transport te = new Transport("maseratti");
        TransportDAO d = new TransportDAO();
        d.insert(te);
        List<Transport> lista = d.listTransport();
        for (Transport t: lista)
        {
            System.out.println( t.getId() +":"+ t.getType() );
        }


    }

}
