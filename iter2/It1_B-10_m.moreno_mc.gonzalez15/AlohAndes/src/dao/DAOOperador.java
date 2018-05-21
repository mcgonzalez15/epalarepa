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
	
	
	/**
	 * Metodo por el que se obtienen las ganancias de un operador en el año actual<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public List<OperadorGanancia> getGananciasActualesOperadores() throws SQLException, Exception {

		Date date1 = new Date();
		int anhoActual = date1.getYear()+1900;
		String sql2 = "SELECT ope.ID,ope.NOMBRE, SUM(res.PRECIO_TOTAL) AS GANANCIA FROM "+USUARIO +".RESERVAS res INNER JOIN "+USUARIO +".OFERTAS ofe ON ofe.ID=res.ID_OFERTA INNER JOIN " +USUARIO+ ".OPERADORES ope ON  ofe.ID_OPERADOR=ope.ID WHERE res.FECHA_FIN BETWEEN '01/01/"+anhoActual+"' AND '31/12/"+anhoActual+"' AND  res.TERMINADA='T' GROUP BY ope.ID, ope.NOMBRE";
		
		System.out.println(sql2);
		
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs = prepStmt2.executeQuery();
		
		ArrayList<OperadorGanancia> lista = new ArrayList<>();
		
		while(rs.next()) {
			String id = rs.getString("ID");
			String nombre = rs.getString("NOMBRE");
			String ganancia = rs.getString("GANANCIA");
			OperadorGanancia actual = new OperadorGanancia(id, nombre, ganancia);
			lista.add(actual);
		}
		return lista;
	}
	
	/**
	 * Metodo por el que se obtienen las ganancias de un operador en el año actual<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public List<OperadorGanancia> getGananciasPasadasOperadores() throws SQLException, Exception {

		Date date1 = new Date();
		int anhoActual = date1.getYear()+1900;
		String sql2 = "SELECT ope.ID,ope.NOMBRE, SUM(res.PRECIO_TOTAL) AS GANANCIA FROM "+USUARIO +".RESERVAS res INNER JOIN "+USUARIO +".OFERTAS ofe ON ofe.ID=res.ID_OFERTA INNER JOIN " +USUARIO+ ".OPERADORES ope ON  ofe.ID_OPERADOR=ope.ID WHERE res.FECHA_FIN BETWEEN '01/01/"+(anhoActual-1)+"' AND '31/12/"+(anhoActual-1)+"' AND  res.TERMINADA='T' GROUP BY ope.ID, ope.NOMBRE";
		System.out.println(sql2);
		
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs = prepStmt2.executeQuery();
		
		ArrayList<OperadorGanancia> lista = new ArrayList<>();
		
		while(rs.next()) {
			String id = rs.getString("ID");
			String nombre = rs.getString("NOMBRE");
			String ganancia = rs.getString("GANANCIA");
			OperadorGanancia actual = new OperadorGanancia(id, nombre, ganancia);
			lista.add(actual);
		}
		return lista;
	}
	/**
	 * Metodo por el que se obtienen los indices de ocupacion de varios OFERTAS<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public List<IndiceOcupacion> getIndicesOcupacion() throws SQLException, Exception {

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = new Date();
		String x = dateFormat.format(date1);
		String sql2 = "SELECT ope.ID, ope.NOMBRE, SUM(res.NUM_PERSONAS)/SUM(ofe.CAPACIDAD) AS PORCENTAJE_OCUPACION FROM "+ USUARIO + ".OPERADORES ope INNER JOIN "+ USUARIO + ".OFERTAS ofe ON ope.ID=ofe.ID_OPERADOR INNER JOIN "+ USUARIO + ".RESERVAS res ON res.ID_OFERTA= ofe.ID WHERE res.CANCELADA='F' AND '"+x+"' BETWEEN res.FECHA_INICIO AND res.FECHA_FIN GROUP BY ope.ID,ope.NOMBRE";
		System.out.println(sql2);
		
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs = prepStmt2.executeQuery();
		
		ArrayList<IndiceOcupacion> lista = new ArrayList<IndiceOcupacion>();
		
		while(rs.next()) {
			String id = rs.getString("ID");
			String nombre = rs.getString("NOMBRE");
			String ganancia = rs.getString("PORCENTAJE_OCUPACION");
			IndiceOcupacion actual = new IndiceOcupacion(id, nombre, ganancia);
			lista.add(actual);
		}
		return lista;
	}
	
	
	/**
	 * Metodo por el que se obtienen los indices de ocupacion de varios OFERTAS<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public List<Funcionamiento> getFuncionamientoAlohandes() throws SQLException, Exception {

		ArrayList<Funcionamiento> lista = new ArrayList<Funcionamiento>();
		for (int i = 0; i <= 52; i++) {
			ArrayList <Integer> idAlojMin = new ArrayList<>(); 
			ArrayList <Integer> idAlojMax = new ArrayList<>(); 
			ArrayList <Integer> idOperMin = new ArrayList<>(); 
			ArrayList <Integer> idOperMax = new ArrayList<>(); 
			Funcionamiento func = new Funcionamiento(i+1, idAlojMin, 0.0,idAlojMax, 0.0, idOperMin, 0, idOperMax, 0);
			lista.add(func);
		}
		
		String sql2 = "WITH R1 AS (SELECT SEM, MAX(SOLICI) AS MAXIM, MIN (SOLICI)AS MINIM FROM (SELECT tabla1.SEMANA AS SEM, OFERTAS.ID_OPERADOR, SUM(tabla1.CUENTA) AS SOLICI FROM(SELECT TO_CHAR(FECHA_INICIO,'IW')AS SEMANA,ID_ALOJAMIENTO AS ALOJ,  COUNT (ID) AS CUENTA FROM RESERVAS GROUP BY EXTRACT( YEAR FROM FECHA_INICIO), TO_CHAR(FECHA_INICIO,'IW'), ID_ALOJAMIENTO HAVING EXTRACT( YEAR FROM FECHA_INICIO) = 2018 )tabla1 inner join OFERTAS ON tabla1.ALOJ = OFERTAS.ID GROUP BY tabla1.SEMANA,OFERTAS.ID_OPERADOR) GROUP BY SEM )," + 
				"R2 AS (SELECT tabla1.SEMANA2 AS SEM2, OFERTAS.ID_OPERADOR AS OPER, SUM(tabla1.CUENTA2) AS SOLICI2 FROM(SELECT TO_CHAR(FECHA_INICIO,'IW')AS SEMANA2,ID_ALOJAMIENTO AS ALOJ2,  COUNT (ID) AS CUENTA2 FROM RESERVAS GROUP BY EXTRACT( YEAR FROM FECHA_INICIO), TO_CHAR(FECHA_INICIO,'IW'), ID_ALOJAMIENTO HAVING EXTRACT( YEAR FROM FECHA_INICIO) = 2018 )tabla1 inner join OFERTAS ON tabla1.ALOJ2 = OFERTAS.ID GROUP BY tabla1.SEMANA2,OFERTAS.ID_OPERADOR)," + 
				"R3 AS ( SELECT R1.SEM , R1.MAXIM, R1.MINIM, R2.OPER FROM R1 INNER JOIN R2 ON R1.SEM = R2.SEM2 AND  R1.MAXIM = R2.SOLICI2)" + 
				"SELECT  R3.SEM AS SEMANA , R3.OPER AS OPERADORMAX, R3.MAXIM, R2.OPER AS OPERADORMIN, R3.MINIM FROM R3 INNER JOIN R2 ON R3.SEM = R2.SEM2 AND  R3.MINIM = R2.SOLICI2";
		System.out.println(sql2);
		
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs = prepStmt2.executeQuery();
		
		while(rs.next()) {
			String semana = rs.getString("SEMANA");
			int semana1 = Integer.parseInt(semana);
			
			String operadorMax = rs.getString("OPERADORMAX");
			int operadorMax1 = Integer.parseInt(operadorMax);
			String solicitudesMax = rs.getString("MAXIM");
			int solicitudesMax1 = Integer.parseInt(solicitudesMax);
			String operadorMin = rs.getString("OPERADORMIN");
			int operadorMin1 = Integer.parseInt(operadorMin);
			String solicitudesMin = rs.getString("MINIM");
			int solicitudesMin1 = Integer.parseInt(solicitudesMin);
			
			lista.get(semana1-1).agregarOperadorMax(operadorMax1);
			lista.get(semana1-1).agregarOperadorMin(operadorMin1);
			lista.get(semana1-1).setNumSolicitudesMax(solicitudesMax1);
			lista.get(semana1-1).setNumSolicitudesMin(solicitudesMin1);
			
		}
		rs.close();
		System.out.println("termino");
		
		String sql3 = "WITH Q1 AS (SELECT ALO.ID AS ALOID ,RES.NUM_PERSONAS/ALO.CAPACIDAD AS CAPACI,TO_CHAR(RES.FECHA_INICIO,'IW')AS SEMA FROM OFERTAS ALO INNER JOIN RESERVAS RES ON ALO.ID=RES.ID_ALOJAMIENTO WHERE EXTRACT( YEAR FROM FECHA_INICIO) = 2018), " + 
				"Q2 AS (SELECT MAX(RESE.NUM_PERSONAS/ALOJ.CAPACIDAD) AS CAPACIMAX, TO_CHAR(RESE.FECHA_INICIO,'IW') AS SEMA, MIN(RESE.NUM_PERSONAS/ALOJ.CAPACIDAD) AS CAPACIMIN FROM OFERTAS ALOJ INNER JOIN RESERVAS RESE ON ALOJ.ID=RESE.ID_ALOJAMIENTO GROUP BY EXTRACT( YEAR FROM FECHA_INICIO), TO_CHAR(RESE.FECHA_INICIO,'IW') HAVING EXTRACT( YEAR FROM FECHA_INICIO) = 2018)," + 
				"Q3 AS (SELECT Q1.SEMA AS SEMANA, Q1.ALOID AS ALOJAMIENTOMAX, Q2.CAPACIMAX AS OCUPACIONMAX, Q2.CAPACIMIN AS OCUPACIONMIN FROM Q1  INNER JOIN Q2 ON  Q1.SEMA=Q2.SEMA AND Q1.CAPACI=Q2.CAPACIMAX)" + 
				"SELECT Q3.SEMANA, Q3.ALOJAMIENTOMAX, Q3.OCUPACIONMAX, Q1.ALOID AS ALOJAMIENTOMIN, Q3.OCUPACIONMIN FROM Q1 INNER JOIN Q3 ON  Q1.SEMA=Q3.SEMANA AND Q1.CAPACI=Q3.OCUPACIONMIN";
		System.out.println(sql3);
		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		ResultSet rs3 = prepStmt3.executeQuery();
		while(rs3.next())
		{
			String semana = rs3.getString("SEMANA");
			int semana1 = Integer.parseInt(semana);
			
			String alojamientoMax = rs3.getString("ALOJAMIENTOMAX");
			int alojamientoMax1 = Integer.parseInt(alojamientoMax);
			String ocupacionMax = rs3.getString("OCUPACIONMAX");
			double ocupacionMax1 = Double.parseDouble(ocupacionMax);
			String alojamientoMin = rs3.getString("ALOJAMIENTOMIN");
			int alojamientoMin1 = Integer.parseInt(alojamientoMin);
			String ocupacionMin = rs3.getString("OCUPACIONMIN");
			double ocupacionMin1 = Double.parseDouble(ocupacionMin);
			
			lista.get(semana1-1).agregarAlojamientoMax(alojamientoMax1);
			lista.get(semana1-1).agregarAlojamientoMin(alojamientoMin1);
			lista.get(semana1-1).setOcupacionMax(ocupacionMax1);
			lista.get(semana1-1).setOcupacionMin(ocupacionMin1);
		}
		return lista;
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
