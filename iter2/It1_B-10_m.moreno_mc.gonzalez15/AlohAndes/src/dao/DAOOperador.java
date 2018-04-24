package dao;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.*;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicacion
 */
public class DAOOperador {

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
	public DAOOperador() {
		recursos = new ArrayList<Object>();
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE COMUNICACION CON LA BASE DE DATOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que obtiene la informacion de todos los operadores en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los Operadores que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Operador> getOperadores( DAOOferta daoOferta) throws SQLException, Exception {
		ArrayList<Operador> operadores = new ArrayList<Operador>();

		String sql = String.format("SELECT * FROM %1$s.OPERADORES", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
		operadores.add(convertResultSetToOperador(rs,daoOferta));
		}
		return operadores;
	}

	

	/**
	 * Metodo que obtiene la informacion del operador en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
	 * @param id el identificador del operador
	 * @return la informacion del operador que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el bebedor conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Operador findOperadorById(Long id, DAOOferta daoOferta) throws SQLException, Exception 
	{
		Operador operador = null;

		String sql = String.format("SELECT * FROM %1$s.OPERADORES WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			operador = convertResultSetToOperador(rs,daoOferta);
		}

		return operador;
	}

	/**
	 * Metodo que agregar la informacion de un nuevo operador en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param operador operador que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addOperador (Operador operador, DAOOferta daoOferta) throws SQLException, Exception {


		
		String sql = String.format("INSERT INTO %1$s.OPERADORES (ID,  NOMBRE, CORREO, ID_VINCULO) VALUES (%2$d, '%3$s', '%4$s', '%5$s', %6$d)", 
				USUARIO, 
				operador.getId(), 
				operador.getNombre(),
				operador.getCorreo(), 
				operador.getVinculoUniandes().toString());
		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		for (int i = 0; i < operador.getOfertas().size(); i++) {
			Oferta actual = operador.getOfertas().get(i);
			daoOferta.addOferta(actual);
		}
		
	}

	/**
	 * Metodo que actualiza la informacion del operador en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param operador operador que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void updateOperador(Operador operador) throws SQLException, Exception {
		//No actualiza los alojamientos.
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.OPERADORES SET ", USUARIO));
		sql.append(String.format("NOMBRE = '%1$s' , CONTACTO = '%2$s'", operador.getNombre(), operador.getCorreo()));
		sql.append(String.format(" WHERE ID = %d ", operador.getId()));
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que actualiza la informacion del operador en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param operador operador que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteOperador(Operador operador,DAOOferta daoOferta) throws SQLException, Exception {

		for (int i = 0; i < operador.getOfertas().size(); i++) {
			Oferta actual = operador.getOfertas().get(i);
			daoOferta.retirarOferta(actual);		}
		
		String sql2 = String.format("DELETE FROM %1$s.OPERADORES WHERE ID = %2$d", USUARIO, operador.getId());
		
		System.out.println(sql2);

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();
		

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
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla OPERADORES) en una instancia de la clase Operador.
	 * @param resultSet ResultSet con la informacion de un operador que se obtuvo de la base de datos.
	 * @return operador cuyos atributos corresponden a los valores asociados a un registro particular de la tabla OPERADORES
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public Operador convertResultSetToOperador(ResultSet resultSet, DAOOferta daoOferta) throws SQLException, Exception {

		long id = Long.parseLong(resultSet.getString("ID"));
		String nombre = resultSet.getString("NOMBRE");
		String contacto = resultSet.getString("CONTACTO");
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
		
		ArrayList<Oferta> ofertas = new ArrayList<Oferta>();

		String sql4 = String.format("SELECT * FROM %1$s.OFERTAS WHERE ID_OPERADOR = %2$d", USUARIO,id);

		PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
		recursos.add(prepStmt4);
		ResultSet rs4 = prepStmt4.executeQuery();

		while (rs4.next()) {
			ofertas.add(daoOferta.convertResultSetToOferta(rs4));
		}
		
		Operador ope = new Operador(id, nombre, contacto, ofertas, relacion);

		return ope;
	}
}
