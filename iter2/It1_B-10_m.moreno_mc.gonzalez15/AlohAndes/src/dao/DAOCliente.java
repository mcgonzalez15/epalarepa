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

	}

	
	/**
	 * Metodo que obtiene la informacion de todos los Clientes fieles para un alojamiento dado <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @param id del alojamiento para el cual se hace el analisis
	 * @return	lista con la informacion de todos los Clientes que han reservado mas de 15 noches o en 3 ocasiones un alojamiento
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Documentacion getClientesFieles(Long  id)    throws SQLException,Exception
	{
		ArrayList<String> clientes = new ArrayList<String>();
		

	String sql=String.format("SELECT res.ID_OFERTA,res.ID_CLIENTE,cli.NOMBRE,cli.CORREO,COUNT(*) AS NUMERO_OCASIONES,SUM(TRUNC(res.FECHA_FIN)-TRUNC(res.FECHA_INICIO)) AS NUM_NOCHES");
	String sql2=String.format(	" FROM RESERVAS res INNER JOIN CLIENTES cli ON cli.ID=res.ID_CLIENTE WHERE res.ID_OFERTA = %1$d AND res.CANCELADA='F' ",id);
	String sql3=String.format(" GROUP BY res.ID_CLIENTE, res.ID_OFERTA,cli.NOMBRE,cli.CORREO HAVING COUNT(*)>=3 OR SUM(TRUNC(res.FECHA_FIN)-TRUNC(res.FECHA_INICIO)) >=15");
	
		System.out.println(sql+sql2+sql3);
		PreparedStatement prepStmt = conn.prepareStatement(sql+sql2+sql3);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		String z = "No existen cliente fieles";
		boolean existen = false;
		while (rs.next()) {
			existen = true;
			String idAlojamiento = rs.getString("ID_OFERTA");
			String idCliente = rs.getString("ID_CLIENTE");
			String nombre = rs.getString("NOMBRE");
			String contacto = rs.getString("CORREO");
			String numOcasiones = rs.getString("NUMERO_OCASIONES");
			String numNoches = rs.getString("NUM_NOCHES");
			String y = "El Alojamiento con id: "+ idAlojamiento+" tiene un cliente fiel, identificado con id: "+ idCliente+", con nombre: " + nombre+" y correo: "+contacto+". Ha visitado el alojamiento en: "+numOcasiones+" ocasiones y se ha hospedado un total de: "+numNoches+" noches";
			clientes.add(y);
		}
		if(!existen)
		{
			clientes.add(z);
		}
		Documentacion x = new Documentacion(clientes);
		return x;
		
		
	}
	
	/////// CUIDADO WARNING WARNING/////////////
	
	/**
	 * Metodo que obtiene la informacion de todo el uso de alohandes en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los Clientes que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<UtilizacionAloh> getUsoAlohAndes() throws SQLException, Exception {
		ArrayList<UtilizacionAloh> clientes = new ArrayList<UtilizacionAloh>();
		String sql = "SELECT cli.TIPO, ofe.TIPO AS TIPO_ALOJAMIENTO,SUM(res.PRECIO_TOTAL) AS PRECIO_TOTAL,SUM(res.NUM_DIAS) AS NUM_DIAS FROM "+USUARIO+".CLIENTES cli INNER JOIN "+USUARIO+".RESERVAS res ON res.ID_CLIENTE=cli.ID INNER JOIN "+USUARIO+".OFERTAS ofe ON ofe.ID=res.ID_OFERTA GROUP BY cli.TIPO,ofe.TIPO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String tipoUsuario = rs.getString("tipo");
			String tipoAlojamiento = rs.getString("TIPO_ALOJAMIENTO");
			String dineroPagado = rs.getString("PRECIO_TOTAL");
			String dias = rs.getString("NUM_DIAS");
			UtilizacionAloh actual = new UtilizacionAloh(tipoUsuario, tipoAlojamiento, dineroPagado, dias);
			clientes.add(actual);
		}
		return clientes;
	}
	
	/**
	 * Metodo que obtiene las estadisticas del cliente enviado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
	 * @param id el identificador del Cliente
	 * @return la informacion del Cliente que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el bebedor conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public EstadisticasCliente getEstadisticasCliente(Cliente cliente) throws SQLException, Exception 
	{
		EstadisticasCliente eCliente = null;

		String sql = "SELECT cli.ID,  cli.NOMBRE,ofe.TIPO AS TIPO_ALOJAMIENTO,SUM (res.PRECIO_TOTAL) AS PRECIO_TOTAL,SUM(res.NUM_DIAS) AS NUM_DIAS FROM" +USUARIO+".CLIENTES cli INNER JOIN" + USUARIO + ".RESERVAS res ON res.ID_CLIENTE=cli.ID INNER JOIN" +USUARIO+".OFERTAS ofe ON ofe.ID=res.ID_OFERTA WHERE cli.NOMBRE='"+cliente.getNombre()+"' GROUP BY cli.NOMBRE,cli.ID,ofe.TIPO"; 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String id = rs.getString("ID");
			String nombre = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO_ALOJAMIENTO");
			String dinero = rs.getString("PRECIO_TOTAL");
			String dias = rs.getString("NUM_DIAS");
			eCliente = new EstadisticasCliente(id,nombre,tipo,dinero,dias);
		}

		return eCliente;
	}
	
	
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
