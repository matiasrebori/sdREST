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

import py.una.pol.personas.model.Location;
import py.una.pol.personas.model.Transport;
@Stateless
public class LocationDAO {


    @Inject
    private Logger log;


    public void insert(Location l) throws SQLException {

        String SQL;
        SQL = "INSERT INTO ubicacion (posx,posy,id_movil) VALUES(?,?,?)";
        Connection conn = null;

        try
        {
            //conectar a la db
            conn = Bd.connect();
            //log.info("Conexion a la db exitosa");
            //preparar datos a insertar
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, l.getPosX() );
            pstmt.setInt(2, l.getPosY() );
            pstmt.setInt(3, l.getTransporte().getId() );
            //ejecutar query
            int affectedRows = pstmt.executeUpdate();
            //rows affected
            //System.out.println( affectedRows );
            System.out.println("fila insertada en tabla ubicacion->" + l.getPosX() + ":"
                    + l.getPosY() + ":" + l.getTransporte().getId() );


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

    public List<Location> listLocation() {
        String query = "SELECT * FROM ubicacion";
        Integer id_movil=0;
        String query2 = "SELECT tipo FROM movil WHERE id_movil=";
        String query3="";
        List<Location> lista = new ArrayList<Location>();

        Connection conn = null;
        try
        {
            conn = Bd.connect();
            ResultSet rs = conn.createStatement().executeQuery(query);
            ResultSet rs_movil;
            while(rs.next()) {
                Location l = new Location();
                Transport t = new Transport();
                //l.setIdLocation( rs.getInt(1) );
                l.setPosX( rs.getInt(1) );
                l.setPosY( rs.getInt(2) );
                //
                t.setId( rs.getInt(3) );
                id_movil = t.getId();
                //traemos el tipo con otro query
                query3=query2+id_movil;
                rs_movil = conn.createStatement().executeQuery(query3);
                rs_movil.next();
                t.setType( rs_movil.getString(1) );
                //agregamos el objeto transporte
                l.setTransporte( t );
                lista.add( l );
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
        //Location lo = new Location(1,1,10,10);
        LocationDAO d = new LocationDAO();
        Transport t = new Transport(1,"auto");
        Location lo = new Location( t , 15, 15);
        //d.insert(lo);
        List<Location> lista = d.listLocation();
        for (Location l: lista) {
            System.out.println( l.getPosX()+":"+l.getPosY()+":"+l.getTransporte().getId()+":"+l.getTransporte().getType() );
        }
    }

}
