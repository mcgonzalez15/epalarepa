package dao;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	public final static String USUARIO = "ISIS2304A551810";


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


	public ArrayList<Reserva> getReservas( DAOOferta daoOferta, DAOCliente daoCliente) throws SQLException, Exception {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();

		String sql = String.format("SELECT * FROM %1$s.RESERVAS", USUARIO);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		int contador = 0;
		while (rs.next() &&contador<59) {
			contador++;
			reservas.add(convertResultSetToReserva(rs,daoOferta,daoCliente));
			System.out.println(contador+"");
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
	
	public void addReserva (Reserva Reserva) throws SQLException, Exception {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fecha1 = "'"+dateFormat.format(Reserva.getFechaInicio())+"'";
		String fecha2 = "'"+dateFormat.format(Reserva.getFechaFin())+"'";
		String fecha4 = "'"+dateFormat.format(Reserva.getCancelacionOportuna())+"'";
		String cancelada = "F";
		if(Reserva.isCancelada())
			cancelada = "T";
		String terminada = "F";
		if(Reserva.isTermino())
			terminada = "T";
		
		String colectiva = "F";
		if(Reserva.isColectiva())
			//colectiva true si la reserva es colectiva
			colectiva = "T";
		// hago una variable idColectiva
		String idColectiva = "F";
		if(Reserva.isColectiva())
			colectiva = "T";
		String sql = null;
		if(Reserva.isColectiva())
		{
			sql = "INSERT INTO "+USUARIO+".RESERVAS (ID, FECHA_INICIO, FECHA_FIN, NUM_PERSONAS,CANCELADA, FECHA_CANCELACION, CANCELACION_OPORTUNA,PRECIO_TOTAL, TERMINADA, ID_OFERTA, ID_CLIENTE, COLECTIVA, ID_COLECTIVA) VALUES ("+ 
				Reserva.getId()+" , "+
				fecha1+" , "+
				fecha2+" , "+
				Reserva.getNumPersonas()+" , '"+
				cancelada+ "' , "+
				null+" , "+
				Reserva.getPrecioTotal()+" ,"+
				fecha4+" , '"+
				terminada+ "' , "+
				Reserva.getOferta().getId()+" , '"+
				colectiva +"' , "+
				Reserva.getIdColectiva()+" , "+
				Reserva.getCliente().getId()+")";
		System.out.println(sql);
		}
		else
		{
			sql = "INSERT INTO "+USUARIO+".RESERVAS (ID, FECHA_INICIO, FECHA_FIN, NUM_PERSONAS,CANCELADA, FECHA_CANCELACION, CANCELACION_OPORTUNA,PRECIO_TOTAL, TERMINADA, ID_OFERTA, ID_CLIENTE, COLECTIVA, ID_COLECTIVA) VALUES ("+ 
					Reserva.getId()+" , "+
					fecha1+" , "+
					fecha2+" , "+
					Reserva.getNumPersonas()+" , '"+
					cancelada+ "' , "+
					null+" , "+
					Reserva.getPrecioTotal()+" ,"+
					fecha4+" , '"+
					terminada+ "' , "+
					Reserva.getOferta().getId()+" , "+
					Reserva.getCliente().getId()+")";
			System.out.println(sql);
		}
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.executeQuery();
		prepStmt.close();


	}

	
	/**
	 * Metodo que intentara agregar la informacion de una reserva colectiva en la Base de Datos a partir de los parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Reserva Reserva que desea agregar a la Base de Datos
	 * @param Cantidad cantidad de reservas a agregar-
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si no se puede generar la reserva colectiva.
	 */
	public Documentacion addReservaColectiva (ReservaColectiva reservaColectiva, DAOOferta daoAlojamiento) throws SQLException, Exception {
		
		ArrayList<String> informe = new ArrayList<String>();
		// tipo de reserva.
		String tipo = reservaColectiva.getReserva().getOferta().getTipo().name();
		Date fechaInicio = reservaColectiva.getReserva().getFechaInicio();
		Date fechaFin = reservaColectiva.getReserva().getFechaFin();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fecha1 = "'"+dateFormat.format(fechaInicio)+"'";
		String fecha2 = "'"+dateFormat.format(fechaFin)+"'";
		String sql = "SELECT * FROM "+USUARIO+".OFERTAS ofe WHERE ofe.ID NOT IN ( SELECT res.ID_OFERTA FROM  "+USUARIO+".RESERVAS res WHERE( res.FECHA_INICIO  BETWEEN "+fecha1+" AND "+fecha2+") OR  ( res.FECHA_FIN  BETWEEN "+fecha1+" AND "+fecha2+") OR (  res.FECHA_INICIO <"+fecha1+"  AND   res.FECHA_FIN>  "+fecha2+") ) AND ofe.ID IN ( SELECT ofer.ID FROM  "+USUARIO+".OFERTAS ofer WHERE ofer.TIPO='"+tipo+"')";
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		System.out.println("die");
		String sql3 = "SELECT COUNT (*) AS NUM_OFERTAS FROM "+USUARIO+".OFERTAS ofe WHERE ofe.ID NOT IN ( SELECT res.ID_OFERTA FROM  "+USUARIO+".RESERVAS res WHERE( res.FECHA_INICIO  BETWEEN "+fecha1+" AND "+fecha2+") OR  ( res.FECHA_FIN  BETWEEN "+fecha1+" AND "+fecha2+") OR (  res.FECHA_INICIO <"+fecha1+"  AND   res.FECHA_FIN>  "+fecha2+") ) AND ofe.ID IN ( SELECT ofer.ID FROM  "+USUARIO+".OFERTAS ofer WHERE ofer.TIPO='"+tipo+"')";
		System.out.println(sql3);
		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		ResultSet rs3 = prepStmt3.executeQuery();
		rs3.next();
		int rowcount = Integer.parseInt(rs3.getString("NUM_OFERTAS"));
		if(rowcount >= reservaColectiva.getCantidad())
		{
			String sql2 = "SELECT MAX(ID) as MAXIMO FROM "+USUARIO+".RESERVAS";
			System.out.println(sql2);
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			rs2.next();
			Long idReserva = Long.parseLong(rs2.getString("MAXIMO"));
			int contador = 0;
			while (rs.next() && contador<reservaColectiva.getCantidad()) {
				contador++;
				Oferta actual = daoAlojamiento.convertResultSetToOferta(rs);
				Long idReservaActual = idReserva+1+contador;
				Reserva ope = new Reserva(idReservaActual, fechaInicio, fechaFin, reservaColectiva.getReserva().isCancelada() , reservaColectiva.getReserva().getNumPersonas(), null, actual.getPrecio(), reservaColectiva.getReserva().isTermino(), reservaColectiva.getReserva().getCancelacionOportuna(), actual,true , reservaColectiva.getIdReservaColectiva(), reservaColectiva.getReserva().getCliente(),  reservaColectiva.getReserva().getServiciosAdicionales());
				addReserva(ope);
				String x = "La reserva No. "+contador+" ha sido asignada al alojamiento con id "+actual.getId()+" y con ubicacion "+actual.getUbicacion()+" con id de reserva individual "+idReservaActual;
				informe.add(x);
			}
		}
		else
		{
			String y ="La reserva colectiva no pudo realizarse debido a que no se contaban con los alojamientos requeridos.";
			informe.add(y);
		}
		Documentacion inf = new Documentacion(informe);
		return inf;
	}

	/**
	 * Metodo que actualiza la informacion del Reserva en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Reserva Reserva que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void cancelarReserva(Reserva Reserva) throws SQLException, Exception {

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = new Date();
		String x = dateFormat.format(date1);
		String fecha = x;
		double precio = Reserva.getPrecioTotal();
		if(Reserva.getCancelacionOportuna().compareTo(date1)>0)
		{
			precio = precio *0.1;
		}
		else if (Reserva.getCancelacionOportuna().compareTo(date1)<0 && Reserva.getFechaInicio().compareTo(date1)>0)
		{
			precio = precio *0.3;
		}
		else
			precio = precio *0.5;
		String sql = "UPDATE "+ USUARIO +".RESERVAS SET "+"CANCELADA = 'T' , FECHA_CANCELACION = '"+fecha+"' , COSTO_DEFINITIVO = "+precio+" , TERMINADA = 'F' WHERE ID ="+Reserva.getId();
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	/**
	 * Metodo que cancela la reserva colectiva dada por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Reserva Reserva colectiva que desea cancelar
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Documentacion cancelarReservaColectiva(ReservaColectiva reservaColectiva, DAOOferta daoAlojamiento, DAOCliente daoCliente) throws SQLException, Exception {
		ArrayList<String> informes = new ArrayList<String>();
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		double precioTotal = 0;
		String sql ="SELECT * FROM "+ USUARIO +".RESERVAS WHERE ID_COLECTIVA = "+reservaColectiva.getIdReservaColectiva();
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		ResultSet rs =prepStmt.executeQuery();
		
		while (rs.next()) {
			reservas.add(convertResultSetToReserva(rs,daoAlojamiento,daoCliente));
		}
		
		for (int i = 0; i < reservas.size(); i++) {
			
			Reserva actual = reservas.get(i);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = new Date();
			String x = dateFormat.format(date1);
			String fecha = x;
			double costoDef = actual.getPrecioTotal();
			if(actual.getCancelacionOportuna().compareTo(date1)>0)
			{
				costoDef = costoDef *0.1;
			}
			else if (actual.getCancelacionOportuna().compareTo(date1)<0 && actual.getFechaInicio().compareTo(date1)>0)
			{
				costoDef = costoDef *0.3;
			}
			else
				costoDef = costoDef *0.5;
			precioTotal += costoDef;
			String sql2 = "UPDATE "+ USUARIO +".RESERVAS SET "+"CANCELADA = 'T' , FECHA_CANCELACION = '"+fecha+"' , COSTO_DEFINITIVO = "+costoDef+" , TERMINADA = 'F' WHERE ID ="+actual.getId();
			System.out.println(sql2);
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2.toString());
			recursos.add(prepStmt2);
			prepStmt2.executeQuery();
			prepStmt2.close();
			String stringActual = "La reserva con id: " + actual.getId() + " ha sido cancelada con una valor de: "+costoDef;
			informes.add(stringActual);
		}
		String z = "La reserva colectiva con id de reserva colectiva: "+ reservaColectiva.getReserva().getIdColectiva()+ " ha sido cancelada con un costo total de "+ precioTotal;
		informes.add(z);
		Documentacion f = new Documentacion(informes);
		return f;
		
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
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla reservaS y RELACIONES) en una instancia de la clase Reserva.
	 * @param resultSet ResultSet con la informacion de un Reserva que se obtuvo de la base de datos.
	 * @return Reserva cuyos atributos corresponden a los valores asociados a un registro particular de la tabla reservaS y RELACIONES.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public Reserva convertResultSetToReserva(ResultSet resultSet, DAOOferta daoAlojamiento, DAOCliente daoCliente) throws SQLException, Exception {
		long id = Long.parseLong(resultSet.getString("ID"));
		
		long idColectiva =-1;
		if(resultSet.getString("ID_COLECTIVA")!= null)
		{
			idColectiva =Long.parseLong(resultSet.getString("ID_COLECTIVA"));
		}
		String fechaInicio = resultSet.getString("FECHA_INICIO");
		String fif = fechaInicio.substring(2, 10);
		String [] array = fif.split("-");
		int anho = Integer.parseInt(array[0])+100;
		int mes = Integer.parseInt(array[1])-1;
		int dia = Integer.parseInt(array[2]);
		Date date1 = new Date(anho, mes, dia);
		String fechaFin = resultSet.getString("FECHA_FIN");
		String fff = fechaFin.substring(2, 10);
		String [] array2 = fff.split("-");
		int anho2 = Integer.parseInt(array2[0])+100;
		int mes2 = Integer.parseInt(array2[1])-1;
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
			int anho3 = Integer.parseInt(array3[0])+100;
			int mes3 = Integer.parseInt(array3[1])-1;
			int dia3 = Integer.parseInt(array3[2]);
			date3 = new Date(anho3, mes3, dia3);
		}
		double costoFinal = Double.parseDouble(resultSet.getString("COSTO_DEFINITIVO"));
		String tiempoOportuno = resultSet.getString("TIEMPO_OPORTUNO");
		String ftf = tiempoOportuno.substring(2, 10);
		String [] array4 = ftf.split("-");
		int anho4 = Integer.parseInt(array4[0])+100;
		int mes4 = Integer.parseInt(array4[1])-1;
		int dia4 = Integer.parseInt(array4[2]);
		Date date4 = new Date(anho4, mes4, dia4);
		boolean terminada = false;
		if(resultSet.getString("TERMINADA").equals("T"))
			terminada = true;
		boolean colectiva = false;
		if(resultSet.getString("COLECTIVA").equals("T"))
			colectiva = true;
		long idAlojamiento = Long.parseLong(resultSet.getString("ID_OFERTA"));
		long idCliente = Long.parseLong(resultSet.getString("ID_CLIENTE"));
		Oferta alojamiento = daoAlojamiento.findOfertaById(idAlojamiento);
		Cliente cliente = daoCliente.findClienteById(idCliente);
		ArrayList <Servicio>servicios = new ArrayList<Servicio>();
		Reserva ope = new Reserva(id, date1, date2, cancelada, numPersonas, date3, costoFinal, terminada, date4, alojamiento,colectiva , idColectiva, cliente, servicios);

		return ope;
	}
}
