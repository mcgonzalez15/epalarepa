package dao;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

import vos.*;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicacion
 */
public class DAOCliente {

	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTANTES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constante para indicar un usuario Oracle
	 */
	public final static String USUARIO = "ISIS2304A431810";


	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase DAOOperador <br/>
	 */
	public DAOCliente() {
		recursos = new ArrayList<Object>();
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE COMUNICACION CON LA BASE DE DATOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que obtiene la informacion de todos los Clientes en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los Clientes que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Cliente> getClientes() throws SQLException, Exception {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();

		String sql = String.format("SELECT * FROM %1$s.CLIENTES", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			clientes.add(convertResultSetToCliente(rs));
		}
		return clientes;
	}



	/**
	 * Metodo que obtiene la informacion del Cliente en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
	 * @param id el identificador del Cliente
	 * @return la informacion del Cliente que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el bebedor conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Cliente findClienteById(Long id) throws SQLException, Exception 
	{
		Cliente Cliente = null;

		String sql = String.format("SELECT * FROM %1$s.CLIENTES WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Cliente = convertResultSetToCliente(rs);
		}

		return Cliente;
	}

	/**
	 * Metodo que agregar la informacion de un nuevo Cliente en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Cliente Cliente que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addCliente (Cliente Cliente) throws SQLException, Exception {


		String sql = String.format("INSERT INTO %1$s.CLIENTES (ID, NOMBRE, CORREO, ID_VINCULO) VALUES (%2$d, '%3$s', '%4$s', '%5$s', %6$d)", 
				USUARIO, 
				Cliente.getId(), 
				Cliente.getNombre(),
				Cliente.getCorreo(), 
				Cliente.getVinculoUniandes().toString());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		

	}

	/**
	 * Metodo que actualiza la informacion del Cliente en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Cliente Cliente que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void updateCliente(Cliente Cliente) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.CLIENTES SET ", USUARIO));
		sql.append(String.format("NOMBRE = '%1$s' , CORREO = '%2$s'", Cliente.getNombre(), Cliente.getCorreo()));
		sql.append(String.format(" WHERE ID = %d ", Cliente.getId()));
		System.out.println(sql);
		// Falta Actualizar los ofertas del Cliente
		//Falta Agregar todos los contratos
		//Falta agregar todos los servicios
		//Falta agregar todas las reservas
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que actualiza la informacion del Cliente en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Cliente Cliente que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteCliente(Cliente Cliente) throws SQLException, Exception {

		String sql2 = String.format("DELETE FROM %1$s.CLIENTES WHERE ID = %2$d", USUARIO, Cliente.getId());

		System.out.println(sql2);

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();


		//Revisar Cascade de los ofertas
		//Falta revisar cascade todos los contratos
		//Falta revisar cascade todos los servicios
		//Falta revisar cascade todas las reservas
	}

	
	
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS AUXILIARES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo encargado de inicializar la conexion del DAO a la Base de Datos a partir del parametro <br/>
	 * <b>Postcondicion: </b> el atributo conn es inicializado <br/>
	 * @param connection la conexion generada en el TransactionManager para la comunicacion con la Base de Datos
	 */
	public void setConn(Connection connection){
		this.conn = connection;
	}

	/**
	 * Metodo que cierra todos los recursos que se encuentran en el arreglo de recursos<br/>
	 * <b>Postcondicion: </b> Todos los recurso del arreglo de recursos han sido cerrados.
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla CLIENTES y VINCULOS) en una instancia de la clase Cliente.
	 * @param resultSet ResultSet con la informacion de un Cliente que se obtuvo de la base de datos.
	 * @return Cliente cuyos atributos corresponden a los valores asociados a un registro particular de la tabla ClienteS y VINCULOS.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public Cliente convertResultSetToCliente(ResultSet resultSet) throws SQLException, Exception {

		long id = Long.parseLong(resultSet.getString("ID"));
		String nombre = resultSet.getString("NOMBRE");
		String correo = resultSet.getString("CORREO");
		String vinculo = resultSet.getString("ID_VINCULO");
		VinculoUniandes relacion;
			if(vinculo.equals(VinculoUniandes.ESTUDIANTE.toString()))
			{
				relacion = VinculoUniandes.ESTUDIANTE;
			} else if(vinculo.equals(VinculoUniandes.PROFESOR.toString()))
			{
				relacion = VinculoUniandes.PROFESOR;
			}else if(vinculo.equals(VinculoUniandes.HOTEL.toString()))
			{
				relacion = VinculoUniandes.HOTEL;
			}
			else if(vinculo.equals(VinculoUniandes.HOSTAL.toString()))
			{
				relacion = VinculoUniandes.HOSTAL;
			}else if(vinculo.equals(VinculoUniandes.EMPLEADO.toString()))
			{
				relacion = VinculoUniandes.EMPLEADO;
			}else if(vinculo.equals(VinculoUniandes.VIVIENDA_UNIVERSITARIA.toString()))
			{
				relacion = VinculoUniandes.VIVIENDA_UNIVERSITARIA;
			}else if(vinculo.equals(VinculoUniandes.FENICIA.toString()))
			{
				relacion = VinculoUniandes.FENICIA;
			} else if(vinculo.equals(VinculoUniandes.PROFESOR_INVITADO.toString()))
			{
				relacion = VinculoUniandes.PROFESOR_INVITADO;
			} else if(vinculo.equals(VinculoUniandes.REGISTRADO.toString()))
			{
				relacion = VinculoUniandes.REGISTRADO;
			} else if(vinculo.equals(VinculoUniandes.EGRESADO.toString()))
			{
				relacion = VinculoUniandes.EGRESADO;
			} else relacion = VinculoUniandes.PADRE_ESTUDIANTE;
	
		ArrayList <Oferta> ofertas = new ArrayList <Oferta>();
		ArrayList <Contrato> contratos = new ArrayList <Contrato>();
		ArrayList <Reserva> reservas = new ArrayList <Reserva>();
		ArrayList <Servicio> servicios = new ArrayList <Servicio>();

		Cliente convertido = new Cliente(id, nombre, correo, relacion, contratos, reservas, servicios, ofertas);

		return convertido;
	}
}
