package dao;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.*;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicacion
 */
public class DAOReserva {

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
	 * Metodo constructor de la clase DAOReserva <br/>
	 */
	public DAOReserva() {
		recursos = new ArrayList<Object>();
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE COMUNICACION CON LA BASE DE DATOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que obtiene la informacion de todos los reservas en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los reservas que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Reserva> getReservas( DAOOferta daoOferta, DAOCliente daoCliente) throws SQLException, Exception {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();

		String sql = String.format("SELECT * FROM %1$s.RESERVAS", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			reservas.add(convertResultSetToReserva(rs,daoOferta,daoCliente));
		}
		return reservas;
	}



	/**
	 * Metodo que obtiene la informacion del Reserva en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
	 * @param id el identificador del Reserva
	 * @return la informacion del Reserva que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el bebedor conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Reserva findReservaById(Long id, DAOOferta daoOferta, DAOCliente daoCliente) throws SQLException, Exception 
	{
		Reserva Reserva = null;

		String sql = String.format("SELECT * FROM %1$s.RESERVAS WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Reserva = convertResultSetToReserva(rs,daoOferta,daoCliente);
		}

		return Reserva;
	}

	/**
	 * Metodo que agregar la informacion de un nuevo Reserva en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Reserva Reserva que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addReserva (Reserva reserva) throws SQLException, Exception {
		String fecha1 = "'"+reserva.getFechaInicio().getDate() +"/" +reserva.getFechaInicio().getMonth()+"/" +reserva.getFechaInicio().getYear()+"'";
		String fecha2 = "'"+reserva.getFechaFin().getDate() +"/" +reserva.getFechaFin().getMonth()+"/" +reserva.getFechaFin().getYear()+"'";

		String fecha4 = "'"+reserva.getCancelacionOportuna().getDate() +"/" +reserva.getCancelacionOportuna().getMonth()+"/" +reserva.getCancelacionOportuna().getYear()+"'";
		String cancelada = "F";
		if(reserva.isCancelada())
			cancelada = "T";
		String terminada = "F";
		if(reserva.isTermino())
			terminada = "T";
		String sql = "INSERT INTO "+USUARIO+".RESERVAS (ID, NUM_DIAS, FECHA_INICIO, FECHA_FIN, NUM_PERSONAS,CANCELADA, FECHA_CANCELACION, PRECIO_TOTAL, CANCELACION_OPORTUNA, TERMINADA, ID_OFERTA, ID_CLIENTE) VALUES ("+ 
				reserva.getId()+" , "+
				reserva.getNumDias()+" , "+
				fecha1+" , "+
				fecha2+" , "+
				reserva.getNumPersonas()+" , '"+
				cancelada+ "' , "+
				null+" , "+
				reserva.getPrecioTotal()+" ,"+
				fecha4+" , '"+
				terminada+ "' , "+
				reserva.getOferta().getId()+" , "+
				reserva.getCliente().getId()+")";
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();


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
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla reservaS y RELACIONES) en una instancia de la clase Reserva.
	 * @param resultSet ResultSet con la informacion de un Reserva que se obtuvo de la base de datos.
	 * @return Reserva cuyos atributos corresponden a los valores asociados a un registro particular de la tabla reservaS y RELACIONES.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public Reserva convertResultSetToReserva(ResultSet resultSet, DAOOferta daoOferta, DAOCliente daoCliente) throws SQLException, Exception {

		long id = Long.parseLong(resultSet.getString("ID"));
		int numDias = Integer.parseInt(resultSet.getString("NUM_DIAS"));
		String fechaInicio = resultSet.getString("FECHA_INICIO");

		String fif = fechaInicio.substring(2, 10);
		String [] array = fif.split("-");
		int anho = Integer.parseInt(array[0])+2000;
		int mes = Integer.parseInt(array[1]);
		int dia = Integer.parseInt(array[2]);
		Date date1 = new Date(anho, mes, dia);

		String fechaFin = resultSet.getString("FECHA_FIN");
		String fff = fechaFin.substring(2, 10);
		String [] array2 = fff.split("-");
		int anho2 = Integer.parseInt(array2[0])+2000;
		int mes2 = Integer.parseInt(array2[1]);
		int dia2 = Integer.parseInt(array2[2]);
		Date date2 = new Date(anho2, mes2, dia2);

		int numPersonas = Integer.parseInt(resultSet.getString("NUM_PERSONAS"));
		boolean cancelada = false;
		if(resultSet.getString("CANCELADA").equals("T"))
			cancelada = true;
		String fechaCancelacion = resultSet.getString("FECHA_CANCELACION");
		Date date3 = null;
		if(fechaCancelacion != null)
		{
			String fcf = fechaCancelacion.substring(2, 10);
			String [] array3 = fcf.split("-");
			int anho3 = Integer.parseInt(array3[0])+2000;
			int mes3 = Integer.parseInt(array3[1]);
			int dia3 = Integer.parseInt(array3[2]);
			date3 = new Date(anho3, mes3, dia3);
		}

		double precioTotal = Double.parseDouble(resultSet.getString("PRECIO_TOTAL"));
		String cancelacionOportuna = resultSet.getString("CANCELACION_OPORTUNA");
		String ftf = cancelacionOportuna.substring(2, 10);
		String [] array4 = ftf.split("-");
		int anho4 = Integer.parseInt(array4[0])+2000;
		int mes4 = Integer.parseInt(array4[1]);
		int dia4 = Integer.parseInt(array4[2]);
		Date date4 = new Date(anho4, mes4, dia4);

		boolean terminada = false;
		if(resultSet.getString("TERMINADA").equals("T"))
			terminada = true;
		long idOferta = Long.parseLong(resultSet.getString("ID_OFERTA"));
		long idCliente = Long.parseLong(resultSet.getString("ID_CLIENTE"));

		Oferta oferta = daoOferta.findOfertaById(idOferta);
		Cliente cliente = daoCliente.findClienteById(idCliente);
		ArrayList <Servicio>servicios = new ArrayList<Servicio>();
		Reserva ope = new Reserva(id, numDias, date1, date2, cancelada, numPersonas, date3, precioTotal, terminada, date4, oferta, cliente, servicios);

		return ope;
	}
}
