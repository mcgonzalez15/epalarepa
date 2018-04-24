package tm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import dao.*;
import vos.*;

/**
 * 
 * Clase que representa al Manejador de Transacciones de la Aplicacion (Fachada en patron singleton de la aplicacion)
 * Responsabilidades de la clase: 
 * 		Intermediario entre los servicios REST de la aplicacion y la comunicacion con la Base de Datos
 * 		Modelar y manejar autonomamente las transacciones y las reglas de negocio.
 */
public class TransactionManager{

	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTANTES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constante que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private static String CONNECTION_DATA_PATH;
	

	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;
	
	/**
	 * Atributo que representa la conexion a la base de datos
	 */
	private Connection conn;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE CONEXION E INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * <b>Metodo Contructor de la Clase ParranderosTransactionManager</b> <br/>
	 * <b>Postcondicion: </b>	Se crea un objeto  ParranderosTransactionManager,
	 * 						 	Se inicializa el path absoluto del archivo de conexion,
	 * 							Se inicializna los atributos para la conexion con la Base de Datos
	 * @param contextPathP Path absoluto que se encuentra en el servidor del contexto del deploy actual
	 * @throws IOException Se genera una excepcion al tener dificultades con la inicializacion de la conexion<br/>
	 * @throws ClassNotFoundException 
	 */
	public TransactionManager(String contextPathP) {
		
		try {
			CONNECTION_DATA_PATH = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
			initializeConnectionData();
		} 
		catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo encargado de inicializar los atributos utilizados para la conexion con la Base de Datos.<br/>
	 * <b>post: </b> Se inicializan los atributos para la conexion<br/>
	 * @throws IOException Se genera una excepcion al no encontrar el archivo o al tener dificultades durante su lectura<br/>
	 * @throws ClassNotFoundException 
	 */
	private void initializeConnectionData() throws IOException, ClassNotFoundException {

		FileInputStream fileInputStream = new FileInputStream(new File(TransactionManager.CONNECTION_DATA_PATH));
		Properties properties = new Properties();
		
		properties.load(fileInputStream);
		fileInputStream.close();
		
		this.url = properties.getProperty("url");
		this.user = properties.getProperty("usuario");
		this.password = properties.getProperty("clave");
		this.driver = properties.getProperty("driver");
		
		Class.forName(driver);
	}

	/**
	 * Metodo encargado de generar una conexion con la Base de Datos.<br/>
	 * <b>Precondicion: </b>Los atributos para la conexion con la Base de Datos han sido inicializados<br/>
	 * @return Objeto Connection, el cual hace referencia a la conexion a la base de datos
	 * @throws SQLException Cualquier error que se pueda llegar a generar durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("[ALOHANDES APP] Attempting Connection to: " + url + " - By User: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS TRANSACCIONALES
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Metodo que modela la transaccion que retorna todos los operadores de la base de datos. <br/>
	 * @return List<operador> - Lista de operadores que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Operador> getAllOperadores( ) throws Exception {
		DAOOferta daoOferta = new DAOOferta();
		DAOOperador daooperador = new DAOOperador();
		List<Operador> operadores;
		try 
		{
			this.conn = darConexion();
			daooperador.setConn(conn);
			daoOferta.setConn(conn);
			operadores = daooperador.getOperadores(daoOferta);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daooperador.cerrarRecursos();
				daoOferta.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return operadores;
	}
	
	/**
	 * Metodo que modela la transaccion que busca el Operador en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del Operador a buscar. id != null
	 * @return Operador - Operador que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Operador getOperadorById(Long id) throws Exception {
		DAOOferta daoOferta = new DAOOferta();
		DAOOperador daoOperador = new DAOOperador();
		Operador Operador = null;
		try 
		{
			this.conn = darConexion();
			daoOperador.setConn(conn);
			daoOferta.setConn(conn);
			Operador = daoOperador.findOperadorById(id, daoOferta);
			if(Operador == null)
			{
				throw new Exception("El Operador con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOperador.cerrarRecursos();
				daoOferta.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Operador;
	}
	

	/**
	 * Metodo que modela la transaccion que agrega un Operador a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el Operador que entra como parametro <br/>
	 * @param Operador - el Operador a agregar. Operador != null
	 * @throws Exception - Cualquier error que se genere agregando el Operador
	 */
	public void addOperador(Operador Operador) throws Exception 
	{
		DAOOferta daoOferta = new DAOOferta();
		DAOOperador daoOperador = new DAOOperador( );
		try
		{
			this.conn = darConexion();
			daoOperador.setConn(conn);
			daoOferta.setConn(conn);
			daoOperador.addOperador(Operador,daoOferta);
			
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOperador.cerrarRecursos();
				daoOferta.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * Metodo que modela la transaccion que actualiza en la base de datos al Operador que entra por parametro.<br/>
	 * Solamente se actualiza si existe el Operador en la Base de Datos <br/>
	 * <b> post: </b> se ha actualizado el Operador que entra como parametro <br/>
	 * @param Operador - Operador a actualizar. Operador != null
	 * @throws Exception - Cualquier error que se genere actualizando al Operador.
	 */
	public void updateOperador(Operador Operador) throws Exception 
	{
		DAOOperador daoOperador = new DAOOperador( );
		DAOOferta daoOferta = new DAOOferta();
		try
		{
			this.conn = darConexion();
			daoOperador.setConn( conn );
			daoOferta.setConn(conn);
			Operador actual = daoOperador.findOperadorById(Operador.getId(),daoOferta);
			if (actual!= null)
			{
				daoOperador.updateOperador(Operador);
			}
			else
			{
				throw new Exception ("[EXCEPTION] General Exception: No se puede actualizar el Operador debido a que no existe un Operador en la base de datos con ese id.");
			}

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOperador.cerrarRecursos();
				daoOferta.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}
	/**
	 * Metodo que modela la transaccion que elimina de la base de datos al Operador que entra por parametro. <br/>
	 * Solamente se actualiza si existe el Operador en la Base de Datos <br/>
	 * <b> post: </b> se ha eliminado el Operador que entra por parametro <br/>
	 * @param Operador - Operador a eliminar. Operador != null
	 * @throws Exception - Cualquier error que se genere eliminando al Operador.
	 */
	public void deleteOperador(Operador Operador) throws Exception 
	{
		DAOOperador daoOperador = new DAOOperador( );
		DAOOferta daoOferta = new DAOOferta();
		try
		{
			this.conn = darConexion();
			daoOperador.setConn( conn );
			daoOferta.setConn(conn);
			Operador actual = daoOperador.findOperadorById(Operador.getId(),daoOferta);
			if (actual!= null)
			{
				daoOperador.deleteOperador(Operador,daoOferta);
			}
			else
			{
				throw new Exception ("[EXCEPTION] General Exception: No se puede eliminar el Operador debido a que no existe un Operador en la base de datos con ese id.");
			}

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOferta.cerrarRecursos();
				daoOperador.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	/**
	 * Metodo que modela la transaccion que retorna todos los Clientes de la base de datos. <br/>
	 * @return List<Cliente> - Lista de Clientes que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Cliente> getAllClientes() throws Exception {
		DAOCliente daoCliente = new DAOCliente();
		List<Cliente> Clientes;
		try 
		{
			this.conn = darConexion();
			daoCliente.setConn(conn);
			
			Clientes = daoCliente.getClientes();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Clientes;
	}
	
	/**
	 * Metodo que modela la transaccion que busca el Cliente en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del Cliente a buscar. id != null
	 * @return Cliente - Cliente que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Cliente getClienteById(Long id) throws Exception {
		DAOCliente daoCliente = new DAOCliente();
		Cliente Cliente = null;
		try 
		{
			this.conn = darConexion();
			daoCliente.setConn(conn);
			Cliente = daoCliente.findClienteById(id);
			if(Cliente == null)
			{
				throw new Exception("El Cliente con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Cliente;
	}
	

	/**
	 * Metodo que modela la transaccion que agrega un Cliente a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el Cliente que entra como parametro <br/>
	 * @param Cliente - el Cliente a agregar. Cliente != null
	 * @throws Exception - Cualquier error que se genere agregando el Cliente
	 */
	public void addCliente(Cliente Cliente) throws Exception 
	{
		
		DAOCliente daoCliente = new DAOCliente( );
		try
		{
			this.conn = darConexion();
			daoCliente.setConn(conn);
			daoCliente.addCliente(Cliente);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * Metodo que modela la transaccion que actualiza en la base de datos al Cliente que entra por parametro.<br/>
	 * Solamente se actualiza si existe el Cliente en la Base de Datos <br/>
	 * <b> post: </b> se ha actualizado el Cliente que entra como parametro <br/>
	 * @param Cliente - Cliente a actualizar. Cliente != null
	 * @throws Exception - Cualquier error que se genere actualizando al Cliente.
	 */
	public void updateCliente(Cliente Cliente) throws Exception 
	{
		DAOCliente daoCliente = new DAOCliente( );
		try
		{
			this.conn = darConexion();
			daoCliente.setConn( conn );
			Cliente actual = daoCliente.findClienteById(Cliente.getId());
			if (actual!= null)
			{
				daoCliente.updateCliente(Cliente);
			}
			else
			{
				throw new Exception ("[EXCEPTION] General Exception: No se puede actualizar el Cliente debido a que no existe un Cliente en la base de datos con ese id.");
			}

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}
	/**
	 * Metodo que modela la transaccion que elimina de la base de datos al Cliente que entra por parametro. <br/>
	 * Solamente se actualiza si existe el Cliente en la Base de Datos <br/>
	 * <b> post: </b> se ha eliminado el Cliente que entra por parametro <br/>
	 * @param Cliente - Cliente a eliminar. Cliente != null
	 * @throws Exception - Cualquier error que se genere eliminando al Cliente.
	 */
	public void deleteCliente(Cliente Cliente) throws Exception 
	{
		DAOCliente daoCliente = new DAOCliente( );
		try
		{
			this.conn = darConexion();
			daoCliente.setConn( conn );
			Cliente actual = daoCliente.findClienteById(Cliente.getId());
			if (actual!= null)
			{
				daoCliente.deleteCliente(Cliente);
			}
			else
			{
				throw new Exception ("[EXCEPTION] General Exception: No se puede eliminar el Cliente debido a que no existe un Cliente en la base de datos con ese id.");
			}

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}
	/**
	 * Metodo que modela la transaccion que retorna todos los Ofertas de la base de datos. <br/>
	 * @return List<Oferta> - Lista de Ofertas que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Oferta> getAllOfertas() throws Exception {
		DAOOferta daoOferta = new DAOOferta();
		List<Oferta> Ofertas;
		try 
		{
			this.conn = darConexion();
			daoOferta.setConn(conn);
			
			Ofertas = daoOferta.getOfertas();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOferta.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Ofertas;
	}
	
	/**
	 * Metodo que modela la transaccion que busca el Oferta en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del Oferta a buscar. id != null
	 * @return Oferta - Oferta que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Oferta getOfertaById(Long id) throws Exception {
		DAOOferta daoOferta = new DAOOferta();
		Oferta Oferta = null;
		try 
		{
			this.conn = darConexion();
			daoOferta.setConn(conn);
			Oferta = daoOferta.findOfertaById(id);
			if(Oferta == null)
			{
				throw new Exception("El Oferta con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOferta.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Oferta;
	}
	

	/**
	 * Metodo que modela la transaccion que agrega un Oferta a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el Oferta que entra como parametro <br/>
	 * @param Oferta - el Oferta a agregar. Oferta != null
	 * @throws Exception - Cualquier error que se genere agregando el Oferta
	 */
	public void addOferta(Oferta Oferta) throws Exception 
	{
		
		DAOOferta daoOferta = new DAOOferta( );
		try
		{
			this.conn = darConexion();
			daoOferta.setConn(conn);
			daoOferta.addOferta(Oferta);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOferta.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * Metodo que modela la transaccion que agrega un Apartamento a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el Apartamento que entra como parametro <br/>
	 * @param Apartamento - el Apartamento a agregar. Apartamento != null
	 * @throws Exception - Cualquier error que se genere agregando el Apartamento
	 */
	public void addApartamento(Apartamento Apartamento) throws Exception 
	{
		
		DAOOferta daoApartamento = new DAOOferta( );
		try
		{
			this.conn = darConexion();
			daoApartamento.setConn(conn);
			daoApartamento.addApartamento(Apartamento);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoApartamento.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * Metodo que modela la transaccion que agrega un Hostal a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el Hostal que entra como parametro <br/>
	 * @param Hostal - el Hostal a agregar. Hostal != null
	 * @throws Exception - Cualquier error que se genere agregando el Hostal
	 */
	public void addHostal(Hostal Hostal) throws Exception 
	{
		DAOOferta daoHostal = new DAOOferta( );
		try
		{
			this.conn = darConexion();
			daoHostal.setConn(conn);
			daoHostal.addHostal(Hostal);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHostal.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que agrega un Hotel a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el Hotel que entra como parametro <br/>
	 * @param Hotel - el Hotel a agregar. Hotel != null
	 * @throws Exception - Cualquier error que se genere agregando el Hotel
	 */
	public void addHotel(Hotel Hotel) throws Exception 
	{
		
		DAOOferta daoHotel = new DAOOferta( );
		try
		{
			this.conn = darConexion();
			daoHotel.setConn(conn);
			daoHotel.addHotel(Hotel);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHotel.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que agrega un ViviendaUniversitaria a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el ViviendaUniversitaria que entra como parametro <br/>
	 * @param ViviendaUniversitaria - el ViviendaUniversitaria a agregar. ViviendaUniversitaria != null
	 * @throws Exception - Cualquier error que se genere agregando el ViviendaUniversitaria
	 */
	public void addViviendaUniversitaria(ViviendaUniversitaria ViviendaUniversitaria) throws Exception 
	{
		
		DAOOferta daoViviendaUniversitaria = new DAOOferta( );
		try
		{
			this.conn = darConexion();
			daoViviendaUniversitaria.setConn(conn);
			daoViviendaUniversitaria.addViviendaUniversitaria(ViviendaUniversitaria);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoViviendaUniversitaria.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que agrega un Habitacion a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el Habitacion que entra como parametro <br/>
	 * @param Habitacion - el Habitacion a agregar. Habitacion != null
	 * @throws Exception - Cualquier error que se genere agregando el Habitacion
	 */
	public void addHabitacion(Habitacion Habitacion) throws Exception 
	{
		
		DAOOferta daoHabitacion = new DAOOferta( );
		try
		{
			this.conn = darConexion();
			daoHabitacion.setConn(conn);
			daoHabitacion.addHabitacion(Habitacion);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHabitacion.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que retorna todos los Reservas de la base de datos. <br/>
	 * @return List<Reserva> - Lista de Reservas que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Reserva> getAllReservas( ) throws Exception {
		DAOOferta daoOferta = new DAOOferta();
		DAOReserva daoReserva = new DAOReserva();
		DAOCliente daoCliente = new DAOCliente();
		List<Reserva> Reservas;
		try 
		{
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoOferta.setConn(conn);
			daoCliente.setConn(conn);
			Reservas = daoReserva.getReservas(daoOferta,daoCliente);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoReserva.cerrarRecursos();
				daoOferta.cerrarRecursos();
				daoCliente.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Reservas;
	}
	
	/**
	 * Metodo que modela la transaccion que busca el Reserva en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del Reserva a buscar. id != null
	 * @return Reserva - Reserva que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Reserva getReservaById(Long id) throws Exception {
		DAOOferta daoOferta = new DAOOferta();
		DAOCliente daoCliente = new DAOCliente();
		DAOReserva daoReserva = new DAOReserva();
		Reserva Reserva = null;
		try 
		{
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoOferta.setConn(conn);
			daoCliente.setConn(conn);;
			Reserva = daoReserva.findReservaById(id, daoOferta,daoCliente);
			if(Reserva == null)
			{
				throw new Exception("El Reserva con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoReserva.cerrarRecursos();
				daoOferta.cerrarRecursos();
				daoCliente.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Reserva;
	}
	

	/**
	 * Metodo que modela la transaccion que agrega un Reserva a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el Reserva que entra como parametro <br/>
	 * @param Reserva - el Reserva a agregar. Reserva != null
	 * @throws Exception - Cualquier error que se genere agregando el Reserva
	 */
	public void addReserva(Reserva Reserva) throws Exception 
	{
		DAOReserva daoReserva = new DAOReserva( );
		try
		{
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoReserva.addReserva(Reserva);
			
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * Metodo que modela la transaccion que actualiza en la base de datos al Oferta que entra por parametro.<br/>
	 * Solamente se actualiza si existe la oferta en la Base de Datos <br/>
	 * <b> post: </b> se ha actualizado la oferta que entra como parametro <br/>
	 * @param Oferta - Oferta a actualizar. Oferta != null
	 * @throws Exception - Cualquier error que se genere actualizando la oferta.
	 */
	public void retirarOferta(Oferta oferta) throws Exception 
	{
		DAOOferta daoOferta = new DAOOferta( );
		try
		{
			this.conn = darConexion();
			daoOferta.setConn( conn );
			Oferta actual = daoOferta.findOfertaById(oferta.getId());
			if (actual!= null)
			{
				daoOferta.retirarOferta( oferta);
			}
			else
			{
				throw new Exception ("[EXCEPTION] General Exception: No se puede actualizar la oferta debido a que no existe una oferta en la base de datos con ese id.");
			}

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOferta.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}
	


	
}