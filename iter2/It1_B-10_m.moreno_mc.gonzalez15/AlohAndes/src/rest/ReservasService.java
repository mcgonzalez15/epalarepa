package rest;

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
import vos.Operador;
import vos.Reserva;



/**
 * Clase que expone servicios REST con ruta base: http://localhost:8080/AlohAndes/rest/Reservas/...
 */
@Path("reservas")
public class ReservasService {

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
	 * Metodo GET que trae a todos los Reservas en la Base de datos. <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/AlohAndes/rest/Reservas <br/>
	 * @return	<b>Response Status 200</b> - JSON que contiene a todos los Reservas que estan en la Base de Datos <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */			
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getReservas() {
		
		try {
			TransactionManager tm = new TransactionManager(getPath());
			
			List<Reserva> Reservas;
			Reservas = tm.getAllReservas();
			return Response.status(200).entity(Reservas).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	/**
	 * Metodo GET que trae al Reserva en la Base de Datos con el ID dado por parametro <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/TransactionManager/rest/Reservas/{id} <br/>
	 * @return	<b>Response Status 200</b> - JSON Reserva que contiene al Reserva cuyo ID corresponda al parametro <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getReservaById( @PathParam( "id" ) Long id )
	{
		try{
			TransactionManager tm = new TransactionManager( getPath( ) );
			
			Reserva Reserva = tm.getReservaById( id );
			return Response.status( 200 ).entity( Reserva ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	/**
	 * Metodo que recibe un Reserva en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se agrega a la Base de datos la informacion correspondiente al Reserva. <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/Reservas <br/>
	 * @param Reserva JSON con la informacion del Reserva que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al Reserva que ha sido agregado <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@POST
	@Produces( { MediaType.APPLICATION_JSON } )
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response addReserva(Reserva Reserva) {
		
		try{
			TransactionManager tm = new TransactionManager( getPath( ) );
			tm.addReserva(Reserva);
			return Response.status( 200 ).entity( Reserva ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	



}