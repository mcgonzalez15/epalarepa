package rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.TransactionManager;
import vos.Cliente;
import vos.ClientePremium;
import vos.CondicionF10;
import vos.Documentacion;
import vos.EstadisticasCliente;
import vos.UtilizacionAloh;



/**
 * Clase que expone servicios REST con ruta base: http://localhost:8080/AlohAndes/rest/operadores/...
 */
@Path("clientes")
public class ClientesService {

	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual.
	 */
	@Context
	private ServletContext context;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS REST
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo GET que trae a todos los Clientes en la Base de datos. <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/AlohAndes/rest/Clientes <br/>
	 * @return	<b>Response Status 200</b> - JSON que contiene a todos los Clientes que estan en la Base de Datos <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */			
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getClientes() {

		try {
			TransactionManager tm = new TransactionManager(getPath());

			List<Cliente> Clientes;
			Clientes = tm.getAllClientes();
			return Response.status(200).entity(Clientes).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	/**
	 * Metodo GET que trae al Cliente en la Base de Datos con el ID dado por parametro <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/TransactionManager/rest/Clientes/{id} <br/>
	 * @return	<b>Response Status 200</b> - JSON Cliente que contiene al Cliente cuyo ID corresponda al parametro <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getClienteById( @PathParam( "id" ) Long id )
	{
		try{
			TransactionManager tm = new TransactionManager( getPath( ) );

			Cliente Cliente = tm.getClienteById( id );
			return Response.status( 200 ).entity( Cliente ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	/**
	 * Metodo que recibe un Cliente en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se agrega a la Base de datos la informacion correspondiente al Cliente. <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/Clientes <br/>
	 * @param Cliente JSON con la informacion del Cliente que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al Cliente que ha sido agregado <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@POST
	@Produces( { MediaType.APPLICATION_JSON } )
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response addCliente(Cliente Cliente) {

		try{
			TransactionManager tm = new TransactionManager( getPath( ) );
			tm.addCliente(Cliente);
			return Response.status( 200 ).entity( Cliente ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}


	/**
	 * Metodo que recibe un Cliente en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se actualiza la Base de datos con la informacion correspondiente al Cliente.<br/>
	 * @param Cliente JSON con la informacion del Cliente que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al Cliente que se desea modificar <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@PUT	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCliente(Cliente Cliente) {
		try{
			TransactionManager tm = new TransactionManager( getPath( ) );
			tm.updateCliente(Cliente);
			return Response.status( 200 ).entity( Cliente ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	/**
	 * Metodo que recibe un Cliente en formato JSON y lo elimina de la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se elimina de la Base de datos al Cliente con la informacion correspondiente.<br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/Clientes <br/>
	 * @param Cliente JSON con la informacion del Cliente que se desea eliminar
	 * @return	<b>Response Status 200</b> - JSON que contiene al Cliente que se desea eliminar <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response deleteCliente(Cliente Cliente) {
		try{
			TransactionManager tm = new TransactionManager( getPath( ) );
			tm.deleteCliente(Cliente);
			return Response.status( 200 ).entity( Cliente ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	 /**
		 * Metodo GET que trae a todos los Clientes en la Base de datos. <br/>
		 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
		 * <b>URL: </b> http://localhost:8080/AlohAndes/rest/Clientes <br/>
		 * @return	<b>Response Status 200</b> - JSON que contiene a todos los Clientes que estan en la Base de Datos <br/>
		 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
		 */			
		@GET
		@Produces({ MediaType.APPLICATION_JSON })
		@Path("usoalohandes")
		public Response getUsoAlohAndes() {
			
			try {
				TransactionManager tm = new TransactionManager(getPath());
				
				List<UtilizacionAloh> Clientes;
				Clientes = tm.getUtilizacionAloh();
				return Response.status(200).entity(Clientes).build();
			} 
			catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
		}
		 /**
			 * Metodo GET que trae a todas las estadisticas de un cliente dado por parametro. <br/>
			 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
			 * <b>URL: </b> http://localhost:8080/AlohAndes/rest/Clientes <br/>
			 * @return	<b>Response Status 200</b> - JSON que contiene a todos los Clientes que estan en la Base de Datos <br/>
			 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
			 */			
			@PUT
			@Produces({ MediaType.APPLICATION_JSON })
			@Path("estadisticascliente")
			public Response getEstadisticasCliente(Cliente clie) {
				
				try {
					TransactionManager tm = new TransactionManager(getPath());
					
					EstadisticasCliente Clientes;
					Clientes = tm.getEstadisticasCliente(clie);
					return Response.status(200).entity(Clientes).build();
				} 
				catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
			}
			
			/**
			 * Metodo GET que trae a los Cliente que son fieles al alojmaiento con id dado por parametro<br/>
			 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
			 * <b>URL: </b> http://localhost:8080/AlohAndesMaster/rest/clientesfieles/{id} <br/>
			 * @return	<b>Response Status 200</b> - JSON Cliente que contiene al Cliente cuyo ID corresponda al parametro <br/>
			 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
			 */
			@GET
			@Path( "clientesfieles/{id: \\d+}" )
			@Produces( { MediaType.APPLICATION_JSON } )
			public Response getClientesFielesById( @PathParam( "id" ) Long id )
			{
				try{
					TransactionManager tm = new TransactionManager( getPath( ) );
					
					Documentacion Cliente = tm.getClientesFieles(id);
					return Response.status( 200 ).entity( Cliente ).build( );			
				}
				catch( Exception e )
				{
					return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
				}
			}



	/**
	 * Metodo GET que trae el consumo de los clientes que cubren <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/AlohAndesMaster/rest/clientesfieles/{id} <br/>
	 * @return	<b>Response Status 200</b> - JSON Cliente que contiene al Cliente cuyo ID corresponda al parametro <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@PUT
	@Path( "consumoAlohandes/{id: \\d+}" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getConsumoAlohandes(  @PathParam( "id" ) Long id, CondicionF10 pCondicion )
	{
		try{
			TransactionManager tm = new TransactionManager( getPath( ) );

			String id1 =id+"";
			String id2 =pCondicion.getIdAlojamiento()+"";

			if(!id1.equals(id2))
			{
				throw new Exception ("Usted no tiene acceso a esos datos");
			}

			ArrayList <Cliente> Cliente = tm.getConsumoAlohandes(pCondicion);
			return Response.status( 200 ).entity( Cliente ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	/**
	 * Metodo GET que trae el consumo de los clientes que cubren <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/AlohAndesMaster/rest/clientesfieles/{id} <br/>
	 * @return	<b>Response Status 200</b> - JSON Cliente que contiene al Cliente cuyo ID corresponda al parametro <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@PUT
	@Path( "consumoAlohandesAlternativo/{id: \\d+}" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getConsumoAlohandesAlternativo(  @PathParam( "id" ) Long id, CondicionF10 pCondicion )
	{
		try{
			TransactionManager tm = new TransactionManager( getPath( ) );

			String id1 =id+"";
			String id2 =pCondicion.getIdAlojamiento()+"";

			if(!id1.equals(id2))
			{
				throw new Exception ("Usted no tiene acceso a esos datos");
			}

			ArrayList <Cliente> Cliente = tm.getConsumoAlohandesAlternativo(pCondicion);
			return Response.status( 200 ).entity( Cliente ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	/**
	 * Metodo GET que trae a todos los cliente premium de alohandes. <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/AlohAndes/rest/Clientes <br/>
	 * @return	<b>Response Status 200</b> - JSON que contiene a todos los Clientes que estan en la Base de Datos <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */			
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("premium")
	public Response getClientesPremium() {
		
		try {
			TransactionManager tm = new TransactionManager(getPath());

			List<ClientePremium> clientes = tm.getClientesPremium();
			return Response.status(200).entity(clientes).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
}


