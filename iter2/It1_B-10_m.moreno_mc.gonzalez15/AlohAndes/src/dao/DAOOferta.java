package dao;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import vos.*;
import vos.Oferta.tipoAlojamiento;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicacion
 */
public class DAOOferta {

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
	public DAOOferta() {
		recursos = new ArrayList<Object>();
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE COMUNICACION CON LA BASE DE DATOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que obtiene la informacion de todos los Ofertas en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los Ofertas que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Oferta> getOfertas() throws SQLException, Exception {
		ArrayList<Oferta> Ofertas = new ArrayList<Oferta>();

		String sql = String.format("SELECT * FROM %1$s.OFERTAS", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();


		while (rs.next()) {
			Ofertas.add(convertResultSetToOferta(rs));
		}
		return Ofertas;
	}



	/**
	 * Metodo que obtiene la informacion del Oferta en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
	 * @param id el identificador del Oferta
	 * @return la informacion del Oferta que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el bebedor conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Oferta findOfertaById(Long id) throws SQLException, Exception 
	{
		Oferta Oferta = null;

		String sql = String.format("SELECT * FROM %1$s.OFERTAS WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Oferta = convertResultSetToOferta(rs);
		}
		rs.close();
		return Oferta;
	}

	/**
	 * Metodo que agregar la informacion de un nuevo Oferta en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Oferta Oferta que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addOferta (Oferta Oferta) throws SQLException, Exception {
// Los alojamientos empiezan sin reservas y el operador existe
		String var = "F";
		if(Oferta.isDisponible())
			var = "T";
		String sql2 = "INSERT INTO "+ USUARIO+".OFERTAS (ID, UBICACION, PRECIO, CAPACIDAD, DISPONIBLE, FECHA_RETIRO, TIPO , ID_OPERADOR) VALUES ("+ 

				Oferta.getId()+", '"+
				Oferta.getUbicacion()+"' ,"+
				Oferta.getPrecio()+","+
				Oferta.getCapacidad()+", '"+
				var+"' , "+
				null+" , '"+
				Oferta.getTipo()+"' ,"+
				Oferta.getOperador().getId()+")";
		System.out.println(sql2);
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();


		List<Servicio> lista = Oferta.getServicios();
		for (int i = 0; i < lista.size(); i++) {
			Servicio actual = lista.get(i);
			String sql3 = "INSERT INTO "+USUARIO+".SERVICIOS (ID, NOMBRE, DESCRIPCION, COSTO) VALUES ("+
					actual.getId()+", '"+
					actual.getNombre()+"' , '"+
					actual.getDescripcion()+"', "+
					actual.getCosto() +" )";
			System.out.println(sql3);
			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			prepStmt3.executeQuery();

			String sql4 = "INSERT INTO " +USUARIO +".OFRECEN (ID_OFERTA , ID_SERVICIO) VALUES ("+
					Oferta.getId()+" , "+
					actual.getId()+")";
			System.out.println(sql4);
			PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
			recursos.add(prepStmt4);
			prepStmt4.executeQuery();
		}
	}

	/**
	 * Metodo que agregar la informacion de un nuevo Apartamento en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Apartamento Apartamento que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addApartamento (Apartamento Apartamento) throws SQLException, Exception {

		String var = "F";
		if(Apartamento.isDisponible())
			var = "T";
		String fecha = Apartamento.getFechaDeRetiro().getDate() +"/" +Apartamento.getFechaDeRetiro().getMonth()+"/" +Apartamento.getFechaDeRetiro().getYear();
		String sql2 = "INSERT INTO "+ USUARIO+".OFERTAS (ID, UBICACION, PRECIO, CAPACIDAD, DISPONIBLE, FECHA_RETIRO, TIPO , ID_OPERADOR) VALUES ("+ 

				Apartamento.getId()+", '"+
				Apartamento.getUbicacion()+"' ,"+
				Apartamento.getPrecio()+","+
				Apartamento.getCapacidad()+", '"+
				var+"' , '"+
				fecha+"' , '"+
				Apartamento.getTipo()+"' ,"+
				Apartamento.getOperador().getId()+")";
		System.out.println(sql2);
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();

		String var2 = "F";
		String var3 = "F";
		String var4 =  "F";
		if(Apartamento.isAmoblado())
			var2 = "T";
		String sql3 = "INSERT INTO "+ USUARIO+".APARTAMENTO (ID, AMOBLADO,NUM_HABITACIONES, INCLUYE_ADMI,  SERVICIOS_PUBLICOS, TIPO_ALQUILER ) VALUES ("+ 

				Apartamento.getId()+", '"+
				var2+ "' ,"+
				Apartamento.getNumHabitaciones()+ "' ,"+
				var3 + "' ,"+
				var4  + "' ,"+
				Apartamento.getTipoAlquiler() +
				" )";
		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		prepStmt3.executeQuery();
		
		List<Servicio> lista = Apartamento.getServicios();
		for (int i = 0; i < lista.size(); i++) {
			Servicio actual = lista.get(i);
			String sql5 = "INSERT INTO "+USUARIO+".SERVICIOS (ID, NOMBRE, DESCRIPCION, COSTO) VALUES ("+
					actual.getId()+", '"+
					actual.getNombre()+"' , '"+
					actual.getDescripcion()+"', "+
					actual.getCosto() +" )";
			System.out.println(sql5);
			PreparedStatement prepStmt5 = conn.prepareStatement(sql5);
			recursos.add(prepStmt5);
			prepStmt5.executeQuery();

			String sql4 = "INSERT INTO " +USUARIO +".OFRENCEN (ID_OFERTA , ID_SERVICIO) VALUES ("+
					Apartamento.getId()+" , "+
					actual.getId()+")";
			System.out.println(sql4);
			PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
			recursos.add(prepStmt4);
			prepStmt4.executeQuery();
		}

	}
	/**
	 * Metodo que agregar la informacion de un nuevo Hostal en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Hostal Hostal que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addHostal (Hostal Hostal) throws SQLException, Exception {
		// Se asume que el operador ya existe.
		// El Hostal empieza sin reservas.
		String var = "F";
		if(Hostal.isDisponible())
			var = "T";
		
		String sql2 = "INSERT INTO "+ USUARIO+".OFERTAS (ID, UBICACION, PRECIO, CAPACIDAD, DISPONIBLE, FECHA_RETIRO, TIPO , ID_OPERADOR) VALUES ("+ 

				Hostal.getId()+", '"+
				Hostal.getUbicacion()+"' ,"+
				Hostal.getPrecio()+","+
				Hostal.getCapacidad()+", '"+
				var+"' , '"+
				null+"' , '"+
				Hostal.getTipo()+"' ,"+
				Hostal.getOperador().getId()+")";
		System.out.println(sql2);
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();

		String var2 = "F";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String fecha2 = dateFormat.format(Hostal.getHorarioApertura())+":"+Hostal.getHorarioApertura().getHours()+":"+Hostal.getHorarioApertura().getMinutes()+":"+Hostal.getHorarioApertura().getSeconds();
		String fecha3 = dateFormat.format(Hostal.getHorarioCierre())+":"+Hostal.getHorarioCierre().getHours()+":"+Hostal.getHorarioCierre().getMinutes()+":"+Hostal.getHorarioCierre().getSeconds();
		String fecha2final = "to_date('"+fecha2+"', 'yyyy/mm/dd:hh:mi:ss')";
		String fecha3final = "to_date('"+fecha3+"', 'yyyy/mm/dd:hh:mi:ss')";
		if(Hostal.isIndividual())
			var2 = "T";
		String sql3 = "INSERT INTO "+ USUARIO+".HOSTAL (ID, HORARIO_APERTURA, HORARIO_CIERRE, INDIVIDUAL ) VALUES ("+ 

				Hostal.getId() +" , "+
				fecha2final+" , "+
				fecha3final+" , '"+
				var2+"' )";
		System.out.println(sql3);
		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		prepStmt3.executeQuery();


		List<Servicio> lista = Hostal.getServicios();
		for (int i = 0; i < lista.size(); i++) {
			Servicio actual = lista.get(i);
			String sql5 = "INSERT INTO "+USUARIO+".SERVICIOS (ID, NOMBRE, DESCRIPCION, COSTO) VALUES ("+
					actual.getId()+", '"+
					actual.getNombre()+"' , '"+
					actual.getDescripcion()+"', "+
					actual.getCosto() +" )";
			System.out.println(sql5);
			PreparedStatement prepStmt5 = conn.prepareStatement(sql5);
			recursos.add(prepStmt5);
			prepStmt5.executeQuery();

			String sql4 = "INSERT INTO " +USUARIO +".OFRENCEN (ID_OFERTA , ID_SERVICIO) VALUES ("+
					Hostal.getId()+" , "+
					actual.getId()+")";
			System.out.println(sql4);
			PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
			recursos.add(prepStmt4);
			prepStmt4.executeQuery();
		}
	}
	/**
	 * Metodo que agregar la informacion de un nuevo Hotel en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Hotel Hotel que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addHotel (Hotel Hotel) throws SQLException, Exception {
		// Se asume que el operador ya existe.
		// El Hotel empieza sin reservas.
		String var = "F";
		if(Hotel.isDisponible())
			var = "T";
		
		String sql2 = "INSERT INTO "+ USUARIO+".OFERTAS (ID, UBICACION, PRECIO, CAPACIDAD, DISPONIBLE, FECHA_RETIRO, TIPO , ID_OPERADOR) VALUES ("+ 

				Hotel.getId()+", '"+
				Hotel.getUbicacion()+"' ,"+
				Hotel.getPrecio()+","+
				Hotel.getCapacidad()+", '"+
				var+"' , '"+
				null+"' , '"+
				Hotel.getTipo()+"' ,"+
				Hotel.getOperador().getId()+")";
		System.out.println(sql2);
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();


		String sql3 = "INSERT INTO "+ USUARIO+".HOTEL (ID, TIPO_HABITACION ) VALUES ("+ 

				Hotel.getId()+", '"+
				Hotel.getTipoHabitacion()+"' )";
		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		prepStmt3.executeQuery();

		List<Servicio> lista = Hotel.getServicios();
		for (int i = 0; i < lista.size(); i++) {
			Servicio actual = lista.get(i);
			String sql5 = "INSERT INTO "+USUARIO+".SERVICIOS (ID, NOMBRE, DESCRIPCION, COSTO) VALUES ("+
					actual.getId()+", '"+
					actual.getNombre()+"' , '"+
					actual.getDescripcion()+"', "+
					actual.getCosto() +" )";
			System.out.println(sql5);
			PreparedStatement prepStmt5 = conn.prepareStatement(sql5);
			recursos.add(prepStmt5);
			prepStmt5.executeQuery();

			String sql4 = "INSERT INTO " +USUARIO +".OFRECEN (ID_OFERTA , ID_SERVICIO) VALUES ("+
					Hotel.getId()+" , "+
					actual.getId()+")";
			System.out.println(sql4);
			PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
			recursos.add(prepStmt4);
			prepStmt4.executeQuery();
		}
		
	}
	/**
	 * Metodo que agregar la informacion de un nuevo habUniversitaria en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param habUniversitaria habUniversitaria que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addViviendaUniversitaria (ViviendaUniversitaria habUniversitaria) throws SQLException, Exception {
		String var = "F";
		if(habUniversitaria.isDisponible())
			var = "T";

		String sql2 = "INSERT INTO "+ USUARIO+".OFERTAS (ID, UBICACION, PRECIO, CAPACIDAD, DISPONIBLE, FECHA_RETIRO, TIPO , ID_OPERADOR) VALUES ("+ 

				habUniversitaria.getId()+", '"+
				habUniversitaria.getUbicacion()+"' ,"+
				habUniversitaria.getPrecio()+","+
				habUniversitaria.getCapacidad()+", '"+
				var+"' , '"+
				null+"' , '"+
				habUniversitaria.getTipo()+"' ,"+
				habUniversitaria.getOperador().getId()+")";
		System.out.println(sql2);
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();

		String var2 = "T";
		String sql3 = "INSERT INTO "+ USUARIO+".VIVIENDA_UNIVERSITARIA (ID, NUM_NOCHES, INDIVIDUAL ) VALUES ("+ 

				habUniversitaria.getId()+", "+
				habUniversitaria.getNumNoches()+" , '"+
				var2+"' )";
		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		prepStmt3.executeQuery();

		//Falta agregar todos los servicios.
		//Falta agregar todos los contratos.
	}
	/**
	 * Metodo que agregar la informacion de un nuevo Habitacion en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Habitacion Habitacion que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addHabitacion (Habitacion Habitacion) throws SQLException, Exception {
		// Se asume que el operador ya existe.
		// El Habitacion empieza sin reservas.
		String var = "F";
		if(Habitacion.isDisponible())
			var = "T";
	
		String sql2 = "INSERT INTO "+ USUARIO+".OFERTAS (ID, UBICACION, PRECIO, CAPACIDAD, DISPONIBLE, FECHA_RETIRO, TIPO , ID_OPERADOR) VALUES ("+ 

				Habitacion.getId()+", '"+
				Habitacion.getUbicacion()+"' ,"+
				Habitacion.getPrecio()+","+
				Habitacion.getCapacidad()+", '"+
				var+"' , '"+
				null+"' , '"+
				Habitacion.getTipo()+"' ,"+
				Habitacion.getOperador().getId()+")";
		System.out.println(sql2);
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();

		String var2 = "F";
		if(Habitacion.isIndividual())
			var2= "T";
		String sql3 = "INSERT INTO "+ USUARIO+".HABITACION (ID, COSTO_SERVICIOS,INDIVIDUAL) VALUES ("+ 

				Habitacion.getId()+", "+
				Habitacion.getCosto()+ ", '"
				+var2+
				"' )";
		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		prepStmt3.executeQuery();

		
		List<Servicio> lista = Habitacion.getServicios();
		for (int i = 0; i < lista.size(); i++) {
			Servicio actual = lista.get(i);
			String sql5 = "INSERT INTO "+USUARIO+".SERVICIOS (ID, NOMBRE, DESCRIPCION, COSTO) VALUES ("+
					actual.getId()+", '"+
					actual.getNombre()+"' , '"+
					actual.getDescripcion()+"', "+
					actual.getCosto() +" )";
			System.out.println(sql5);
			PreparedStatement prepStmt5 = conn.prepareStatement(sql5);
			recursos.add(prepStmt5);
			prepStmt5.executeQuery();

			String sql4 = "INSERT INTO " +USUARIO +".OFRENCEN (ID_OFERTA , ID_SERVICIO) VALUES ("+
					Habitacion.getId()+" , "+
					actual.getId()+")";
			System.out.println(sql4);
			PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
			recursos.add(prepStmt4);
			prepStmt4.executeQuery();
		}
	
	}
	/**
	 * Metodo que retira la oferta de oferta en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Oferta oferta que se desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void retirarOferta(Oferta oferta) throws SQLException, Exception {


		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = new Date();
		String x = dateFormat.format(date1);

		String fecha = x;

		String sentencia ="SELECT MAX (RE.FECHA_FIN) AS MAX_DATE FROM RESERVAS RE WHERE RE.ID_OFERTA="+oferta.getId();
		PreparedStatement prepStmt2 = conn.prepareStatement(sentencia);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();
		ResultSet rs = prepStmt2.executeQuery();
		if(rs.next()) {
			String fechaUltimaReserva = rs.getString("MAX_DATE");
			if(fechaUltimaReserva!= null)
			{
				String prueba = fechaUltimaReserva.substring(2, 10);
				String [] array = prueba.split("-");
				int anho = Integer.parseInt(array[0])+100;
				int mes = Integer.parseInt(array[1])-1;
				int dia = Integer.parseInt(array[2]);
				Date date = new Date(anho, mes, dia);
				if(date.compareTo(date1)>0)
				{
					fecha = dia+"/"+(mes+1)+"/"+(anho+1900);
				}
			}
		}

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.OFERTAS SET ", USUARIO));
		sql.append(String.format("DISPONIBLE = 'F' , FECHA_RETIRO = '%1$s'",  fecha));
		sql.append(String.format(" WHERE ID = %d ", oferta.getId()));
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	/**
	 * Metodo que deshabilita la oferta de alojamiento en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Alojamiento alojamiento que se desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Documentacion deshabilitarOferta(Oferta alojamiento, DAOReserva daoReserva, DAOCliente daoCliente) throws SQLException, Exception {

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date datex = new Date();
		String xm = dateFormat.format(datex);
		
		ArrayList<String> informe = new ArrayList<>();
		// Selecciona todo de RESERVAS ordenado por fecha de inicio donde el id es de la oferta dada y esta vigente
		String sentencia ="SELECT * FROM  " +USUARIO +".RESERVAS RE WHERE RE.ID_OFERTA = "+alojamiento.getId()+" AND CANCELADA = 'F' AND TERMINADA = 'F' ORDER BY RE.FECHA_INICIO";
		System.out.println(sentencia);
		PreparedStatement prepStmt2 = conn.prepareStatement(sentencia);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();
		ResultSet rs = prepStmt2.executeQuery();
		while(rs.next()) {
			String idReservaActual = rs.getString("ID");
			String fechaInicio = "'"+ rs.getString("FECHA_INICIO")+"'";
			String fechaFin = "'"+rs.getString("FECHA_FIN")+"'";
			String fI = fechaInicio.substring(3, 11);
			System.out.println(fI);
			String [] array = fI.split("-");
			int anho = Integer.parseInt(array[0])+100;
			int mes = Integer.parseInt(array[1])-1;
			int dia = Integer.parseInt(array[2]);
			Date date1 = new Date(anho, mes, dia);
			System.out.println( "El dia es: "+ dia +"El mes es: "+ mes +"El anho es: "+anho);
			String date11= "'"+dateFormat.format(date1)+"'";

			String fF = fechaFin.substring(3, 11);
			System.out.println(fF);
			String [] array2 = fF.split("-");
			int anho2 = Integer.parseInt(array2[0])+100;
			int mes2 = Integer.parseInt(array2[1])-1;
			int dia2 = Integer.parseInt(array2[2]);
			Date date2 = new Date(anho2, mes2, dia2);
			System.out.println( "El dia es: "+ dia2 +"El mes es: "+ mes2 +"El anho es: "+anho2);
			String date22= "'"+dateFormat.format(date2)+"'";
			
			// seleccionar todo de ofertas donde el id de ofertas no este en ( id oferta de reserva  donde la fecha de inicio de la reserca este entre las dadas o que la fecga fin entre las dadas o que la fecha de inicio sea mejor a la dada y a fecha fin mayor a la 2)
			String sql3 = "SELECT * FROM "+USUARIO+".OFERTAS ALO WHERE ROWNUM < 2 AND ALO.ID NOT IN ( SELECT RE.ID_OFERTA FROM  "+USUARIO+".RESERVAS RE WHERE( RE.FECHA_INICIO  BETWEEN "+date11+" AND "+date22+") OR  ( RE.FECHA_FIN  BETWEEN "+date11+" AND "+date22+") OR (  RE.FECHA_INICIO < "+date11+"  AND   RE.FECHA_FIN >  "+date22+") )";
			System.out.println(sql3);
			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			ResultSet rs3 = prepStmt3.executeQuery();
			if(rs3.next())
			{// Selecciono el ID MAS DE RESERVAS)
				String sql4 = "SELECT MAX (ID) AS NUMACTUAL FROM "+USUARIO+".RESERVAS";
				System.out.println(sql4);
				PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
				recursos.add(prepStmt4);
				ResultSet rs4 = prepStmt4.executeQuery();
				rs4.next();
				Long idReserva = Long.parseLong(rs4.getString("NUMACTUAL"));
				Oferta actual = convertResultSetToOferta(rs3);
				int numPer = Integer.parseInt(rs.getString("NUM_PERSONAS"));
				String fechaCancelacion = rs.getString("TIEMPO_OPORTUNO");
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


				long idCliente = Long.parseLong(rs.getString("ID_CLIENTE"));
				Cliente cliente = daoCliente.findClienteById(idCliente);
				ArrayList<Servicio> servicios = new ArrayList<>();
				Reserva ope = new Reserva(idReserva+2, date1, date2, false , numPer , null, actual.getPrecio(), false, date3, actual,false , Long.parseLong("0"),  cliente, servicios);
				daoReserva.addReserva(ope);
				String x = "La reserva No."+idReservaActual+" ha sido asignada al alojamiento con id "+actual.getId()+" con ubicacion "+actual.getUbicacion()+" con un nuevo id "+idReserva+1 +" debido a inconvenientes con el alojamiento " +alojamiento.getId()+ " quien se encuentra deshabilitado temporalmente";
				informe.add(x);
				prepStmt4.close();
				
			}
			else
			{
				String zx = "La reserva con id: "+ idReservaActual +" fue cancelada debido a inconvenitentes con el alojamiento y no se encontro una oferta disponible en las fechas";
				informe.add(zx);
			}
			prepStmt3.close();

		}

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.OFERTAS SET ", USUARIO));
		sql.append("VIGENTE = 'F' ");
		sql.append(String.format(" WHERE ID = %d ", alojamiento.getId()));
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		String y = "Se ha deshabilitado el alojamiento: " +alojamiento.getId();
		informe.add(y);
		
		String sql6 = "UPDATE " +USUARIO +".RESERVAS SET CANCELADA = 'T' , FECHA_CANCELACION = '"+xm+"' WHERE ID_OFERTA = "+alojamiento.getId();
		System.out.println(sql6);
		PreparedStatement prepStmt6 = conn.prepareStatement(sql6);
		recursos.add(prepStmt6);
		prepStmt6.executeQuery();
		
		Documentacion inf = new Documentacion(informe);
		return inf;
	}

	/**
	 * Metodo que habilita la oferta de alojamiento en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Alojamiento alojamiento que se desea habilitar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Documentacion habilitarOferta(Oferta alojamiento) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.ALOJAMIENTOS SET ", USUARIO));
		sql.append("VIGENTE = 'T' ");
		sql.append(String.format(" WHERE ID = %d ", alojamiento.getId()));
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		ArrayList<String> noMasSistrans = new ArrayList<>();
		String i = "Se ha habilitado la oferta de alojamiento identificada con id: "+alojamiento.getId();
		noMasSistrans.add(i);
		Documentacion d = new Documentacion(noMasSistrans);
		return d;
	}
	
	
	
	
	/**
	 * Metodo que obtiene la informacion de todos los Alojamientos mas populares en la base de datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los Alojamientos que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<MejoresOfertas> getAlojamientosMasPopulares() throws SQLException, Exception {
		ArrayList<MejoresOfertas> Alojamientos = new ArrayList<MejoresOfertas>();

		String sql = "SELECT A.* FROM (SELECT ALO.ID as id_alojamiento,OPE.NOMBRE AS OPERADOR,ALO.UBICACION AS UBICACION ,COUNT(RE.ID) AS mas_reservado FROM (ISIS2304A431810.RESERVAS RE INNER JOIN ISIS2304A431810.OFERTAS ALO ON RE.ID_OFERTA=ALO.ID INNER JOIN ISIS2304A431810.OPERADORES OPE ON ALO.ID_OPERADOR=OPE.ID) GROUP BY ALO.ID,ALO.UBICACION, OPE.NOMBRE ORDER BY COUNT(RE.ID) DESC) A WHERE ROWNUM<20";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();


		while (rs.next()) {
			String id = rs.getString("ID_ALOJAMIENTO");
			String operador = rs.getString("OPERADOR");
			String ubicacion = rs.getString("UBICACION");
			String numReserva = rs.getString("MAS_RESERVADO");
			MejoresOfertas actual = new MejoresOfertas(id, operador, ubicacion, numReserva);
			Alojamientos.add(actual);		}
		return Alojamientos;
	}
	/**
	 * Metodo que obtiene la informacion de todos los Alojamientos con restriccion de fecha y servicios <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los Alojamientos que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Oferta> getAlojamientosConRestriccion(Condicion pCondiciones) throws SQLException, Exception {
		ArrayList<Oferta> Alojamientos = new ArrayList<Oferta>();
		if(pCondiciones.getFechaFin()!= null && pCondiciones!=null && pCondiciones.getServicios()!= null&& !pCondiciones.getServicios().isEmpty())
		{
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaInicio = pCondiciones.getFechaInicio();
			Date fechaFin = pCondiciones.getFechaFin();
			String x1 = dateFormat.format(fechaInicio);
			String x2 = dateFormat.format(fechaFin);
		   String sql = "SELECT * FROM ISIS2304A431810.OFERTAS ALO WHERE ALO.ID NOT IN ( SELECT RE.ID_OFERTA FROM  ISIS2304A431810.RESERVAS RE WHERE( RE.FECHA_INICIO  BETWEEN '"+x1+"' AND '"+x2+"')";
			String sql2	=	" ) AND ALO.ID IN ( SELECT SEO.ID_OFERTA FROM  ISIS2304A431810.OFRECEN SEO INNER JOIN  ISIS2304A431810.SERVICIOS SE ON SE.ID=SEO.ID_SERVICIO WHERE ";
			String sql3 = "";
			for (int i = 1; i < pCondiciones.getServicios().size(); i++) {
				String actual = pCondiciones.getServicios().get(i).getNombre();
				sql3+=" SE.NOMBRE='"+actual+"'OR ";
			}
			String sql4 =  "SE.NOMBRE='"+pCondiciones.getServicios().get(0).getNombre()+"' )";
			System.out.println(sql+sql2+sql3+sql4);
			PreparedStatement prepStmt = conn.prepareStatement(sql+sql2+sql3+sql4);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();


			while (rs.next()) {
				Alojamientos.add(convertResultSetToOferta(rs));
			}
		}
		else
			throw new Exception("Condiciones de busqueda invalidas");
		return Alojamientos;
	}
	
	
	
	/**
	 * Metodo que dado un tipo de alojamiento y un rango de fechas semana o mes dice cuales fueron las fechas de mayor y menor numero de reservas y la de mayor recaudacion
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion del parametro analizado y la fecha en la cual se produce ademas de su valor.
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Documentacion getOperacionAlohAndes(Condicionn cond) throws SQLException,Exception
	{
		
		ArrayList<String> pReporte = new ArrayList<>();
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaInicio = cond.getFechaInicio();
		Date fechaFin = cond.getFechaFin();
		String x1 = dateFormat.format(fechaInicio);
		String x2 = dateFormat.format(fechaFin);

		long diff = fechaFin.getTime() - fechaInicio.getTime();
		int n=(int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		System.out.println ("Days de : " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
		String tipo=cond.getTipo().getTipo().name();
		if(cond!=null && cond.getFechaInicio()!= null &&cond.getFechaFin()!= null && cond.getTipo()!= null)
		{
			String diamaxOcu=null;
			String diaminOcu=null;
			String diamaxRec=null;

			double maxOcupacion= -1;
			double minOcuapcion= Double.POSITIVE_INFINITY;
			double maxRecaudacion= -1;
			Date d1 = fechaInicio;

			for(int i=0;i<n;i++)//puede cambairse este while
			{

				String xi=dateFormat.format(d1);
				String sql=String.format("SELECT COUNT(*) AS OCUPACION  FROM RESERVAS RE INNER JOIN OFERTAS ALO ON ALO.ID=RE.ID_OFERTA WHERE "+xi+" BETWEEN RE.FECHA_INICIO AND RE.FECHA_FIN  AND RE.CANCELADA='F' AND  ALO.TIPO = %1$s",tipo);
				System.out.println(sql);
				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);
				ResultSet rs = prepStmt.executeQuery();
				double ocupaciondiaactual=Integer.parseInt( rs.getString("OCUPACION"));
				rs.close();
				if(ocupaciondiaactual>maxOcupacion  )
				{
					maxOcupacion=ocupaciondiaactual;
					diamaxRec=xi;
				}
				if(ocupaciondiaactual<minOcuapcion  )
				{
					minOcuapcion=ocupaciondiaactual;
					diaminOcu=xi;
				}

				String sql2=String.format("SELECT SUM(COSTO_DEFINITIVO) AS TOTAL_COBRADO_POR_DIA FROM RESERVAS RE INNER JOIN OFERTAS ALO ON RE.ID_OFERTA=ALO.ID WHERE (RE.TERMINADA='T' AND RE.FECHA_FIN=" +xi+ " )OR (RE.CANCELADA='T' AND RE.FECHA_CANCELACION=" +xi +") AND  ALO.TIPO = $1$S",tipo);
			    System.out.println(sql2);
				PreparedStatement prepStmt2 = conn.prepareStatement(sql);
				recursos.add(prepStmt2);
				ResultSet rs2 = prepStmt.executeQuery();
				double reacudodiaactual=Integer.parseInt( rs2.getString("TOTAL_COBRADO_POR_DIA"));
				rs2.close();

				if(reacudodiaactual>maxRecaudacion  )
				{
					maxRecaudacion=reacudodiaactual;
					diamaxRec=xi;
				}


				Date d2 = new Date();
				d2.setTime(d1.getTime() + 1 * 24 * 60 * 60 * 1000);	
				d1=d2;
			}
			pReporte.add("la ocupacion maxima se da en "+diamaxOcu+"cuando hay ocupados "+maxOcupacion+" alojamientos");
			pReporte.add("la ocupacion maxima se da en "+diaminOcu+"cuando hay ocupados "+minOcuapcion+" alojamientos");
			pReporte.add("la ocupacion maxima se da en "+diamaxRec+"cuando hay cancelados $"+maxRecaudacion);
		}
	
		Documentacion result=new Documentacion(pReporte);
		return result;

	}
	
	
	/**
	 * Metodo que obtiene la informacion de todos los Alojamientos que no tienen reservas durante 1 mes completo <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los Alojamientos que se no fueron usados durante 1 mes o mas
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Oferta> getAlojamientosDesiertos(Condicion pCondiciones) throws SQLException, Exception {
		ArrayList<Oferta> Alojamientos = new ArrayList<Oferta>();
		if(pCondiciones.getFechaFin()!= null && pCondiciones!=null && pCondiciones.getServicios()!= null&& !pCondiciones.getServicios().isEmpty())
		{
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaInicio = pCondiciones.getFechaInicio();
			Date fechaFin = pCondiciones.getFechaFin();
			String x1 = dateFormat.format(fechaInicio);
			String x2 = dateFormat.format(fechaFin);
		   String sql = "SELECT * FROM ISIS2304A431810.OFERTAS ALO WHERE ALO.ID NOT IN ( SELECT RE.ID_OFERTA FROM  ISIS2304A431810.RESERVAS RE WHERE( RE.FECHA_INICIO  BETWEEN '"+x1+"' AND '"+x2+"')";
			String sql2	=	" ) AND ALO.ID IN ( SELECT SEO.ID_OFERTA FROM  ISIS2304A431810.OFRECEN SEO INNER JOIN  ISIS2304A431810.SERVICIOS SE ON SE.ID=SEO.ID_SERVICIO WHERE ";
			String sql3 = "";
			for (int i = 1; i < pCondiciones.getServicios().size(); i++) {
				String actual = pCondiciones.getServicios().get(i).getNombre();
				sql3+=" SE.NOMBRE='"+actual+"'OR ";
			}
			String sql4 =  "SE.NOMBRE='"+pCondiciones.getServicios().get(0).getNombre()+"' )";
			System.out.println(sql+sql2+sql3+sql4);
			PreparedStatement prepStmt = conn.prepareStatement(sql+sql2+sql3+sql4);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();


			while (rs.next()) {
				Alojamientos.add(convertResultSetToOferta(rs));
			}
		}
		else
			throw new Exception("Condiciones de busqueda invalidas");
		return Alojamientos;
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
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla OfertaS y VINCULOS) en una instancia de la clase Oferta.
	 * @param resultSet ResultSet con la informacion de un Oferta que se obtuvo de la base de datos.
	 * @return Oferta cuyos atributos corresponden a los valores asociados a un registro particular de la tabla OfertaS y VINCULOS.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public Oferta convertResultSetToOferta(ResultSet resultSet) throws SQLException, Exception {

		long id = Long.parseLong(resultSet.getString("ID"));
		String ubicacion = resultSet.getString("UBICACION");
		int precio = Integer.parseInt(resultSet.getString("PRECIO"));
		int capacidad = Integer.parseInt(resultSet.getString("CAPACIDAD"));
		boolean disponible = resultSet.getString("DISPONIBLE").equals("T");

		String fech = resultSet.getString("FECHA_RETIRO");
		Date date = null;
		if(fech!=null)
		{
			String prueba = fech.substring(2, 10);
			System.out.println(fech);
			System.out.println(prueba);
			String [] array = prueba.split("-");
			int anho = Integer.parseInt(array[0])+2000;
			int mes = Integer.parseInt(array[1]);
			int dia = Integer.parseInt(array[2]);
			System.out.println(dia);
			System.out.println(mes);
			System.out.println(anho);
			date = new Date(anho, mes, dia);
		}
		String tipis = resultSet.getString("TIPO");
		
		tipoAlojamiento tipo;
		
		if(tipis == tipoAlojamiento.HOSTAL.toString())
		{
			tipo = tipoAlojamiento.HOSTAL; 
		} else if(tipis ==  tipoAlojamiento.HOTEL.toString())
		{
			tipo = tipoAlojamiento.HOTEL;
		} else if(tipis ==  tipoAlojamiento.APARTAMENTO.toString())
		{
			tipo = tipoAlojamiento.APARTAMENTO;
		}else if(tipis ==  tipoAlojamiento.HABITACION.toString())
		{
			tipo = tipoAlojamiento.HABITACION;
		} else tipo = tipoAlojamiento.VIVIENDA_UNIVERSITARIA;
		
		Long idOp = Long.parseLong(resultSet.getString("ID_OPERADOR"));

		String sql = String.format("SELECT * FROM %1$s.OPERADORES WHERE ID = %2$d", USUARIO, idOp); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		long idOpera = Long.parseLong(rs.getString("ID"));
		String nombre = rs.getString("NOMBRE");
		String correo = rs.getString("CORREO");
		String vinculo = rs.getString("ID_VINCULO");


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
		Operador ope = new Operador(idOpera, nombre, correo, ofertas, relacion);
		
		ArrayList <Servicio> servicios = new ArrayList <Servicio>();

		ArrayList <Reserva> reservas = new ArrayList <Reserva>();

		Oferta oferta = new Oferta(id, ubicacion, precio, capacidad,  disponible, date, ope, servicios, reservas, tipo);


		return oferta;
	}
}