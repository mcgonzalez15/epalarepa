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
import vos.Oferta;
import vos.Apartamento;
import vos.Cliente;
import vos.Hostal;
import vos.Hotel;
import vos.ViviendaUniversitaria;
import vos.Operador;
import vos.Habitacion;



/**
 * Clase que expone servicios REST con ruta base: http://localhost:8080/AlohAndes/rest/operadores/...
 */
@Path("ofertas")
public class OfertasService {

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
	 * Metodo GET que trae a todos los Ofertas en la Base de datos. <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/AlohAndes/rest/Ofertas <br/>
	 * @return	<b>Response Status 200</b> - JSON que contiene a todos los Ofertas que estan en la Base de Datos <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */			
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getOfertas() {
		
		try {
			TransactionManager tm = new TransactionManager(getPath());
			
			List<Oferta> Ofertas;
			Ofertas = tm.getAllOfertas();
			return Response.status(200).entity(Ofertas).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	/**
	 * Metodo GET que trae al Oferta en la Base de Datos con el ID dado por parametro <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/TransactionManager/rest/Ofertas/{id} <br/>
	 * @return	<b>Response Status 200</b> - JSON Oferta que contiene al Oferta cuyo ID corresponda al parametro <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getOfertaById( @PathParam( "id" ) Long id )
	{
		try{
			TransactionManager tm = new TransactionManager( getPath( ) );
			
			Oferta Oferta = tm.getOfertaById( id );
			return Response.status( 200 ).entity( Oferta ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	/**
	 * Metodo que recibe un Oferta en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se agrega a la Base de datos la informacion correspondiente al Oferta. <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/Ofertas <br/>
	 * @param Oferta JSON con la informacion del Oferta que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al Oferta que ha sido agregado <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@POST
	@Produces( { MediaType.APPLICATION_JSON } )
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response addOferta(Oferta Oferta) {
		
		try{
			TransactionManager tm = new TransactionManager( getPath( ) );
			tm.addOferta(Oferta);
			return Response.status( 200 ).entity( Oferta ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	/**
	 * Metodo que recibe un Apartamento en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se agrega a la Base de datos la informacion correspondiente al Apartamento. <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/Apartamentos <br/>
	 * @param Apartamento JSON con la informacion del Apartamento que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al Apartamento que ha sido agregado <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@POST
	@Produces( { MediaType.APPLICATION_JSON } )
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("apartamentos")
	public Response addApartamento(Apartamento apartamento) {
		try{
			TransactionManager tm = new TransactionManager( getPath( ) );
			tm.addApartamento(apartamento);
			return Response.status( 200 ).entity( apartamento ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	/**
	 * Metodo que recibe un Hostal en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se agrega a la Base de datos la informacion correspondiente al Hostal. <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/Hostals <br/>
	 * @param Hostal JSON con la informacion del Hostal que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al Hostal que ha sido agregado <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@POST
	@Produces( { MediaType.APPLICATION_JSON } )
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("habitacioneshostales")
	public Response addHostal(Hostal Hostal) {
		try{
			TransactionManager tm = new TransactionManager( getPath( ) );
			tm.addHostal(Hostal);
			return Response.status( 200 ).entity( Hostal ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	/**
	 * Metodo que recibe un Hotel en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se agrega a la Base de datos la informacion correspondiente al Hotel. <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/Hotels <br/>
	 * @param Hotel JSON con la informacion del Hotel que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al Hotel que ha sido agregado <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@POST
	@Produces( { MediaType.APPLICATION_JSON } )
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("habitacioneshoteles")
	public Response addHotel(Hotel Hotel) {
		try{
			TransactionManager tm = new TransactionManager( getPath( ) );
			tm.addHotel(Hotel);
			return Response.status( 200 ).entity( Hotel ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	/**
	 * Metodo que recibe un ViviendaUniversitaria en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se agrega a la Base de datos la informacion correspondiente al ViviendaUniversitaria. <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/ViviendaUniversitarias <br/>
	 * @param ViviendaUniversitaria JSON con la informacion del ViviendaUniversitaria que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al ViviendaUniversitaria que ha sido agregado <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@POST
	@Produces( { MediaType.APPLICATION_JSON } )
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("viviendasuniversitarias")
	public Response addViviendaUniversitaria(ViviendaUniversitaria ViviendaUniversitaria) {
		try{
			TransactionManager tm = new TransactionManager( getPath( ) );
			tm.addViviendaUniversitaria(ViviendaUniversitaria);
			return Response.status( 200 ).entity( ViviendaUniversitaria ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	/**
	 * Metodo que recibe un Habitacion en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se agrega a la Base de datos la informacion correspondiente al Habitacion. <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/Habitacions <br/>
	 * @param Habitacion JSON con la informacion del Habitacion que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al Habitacion que ha sido agregado <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@POST
	@Produces( { MediaType.APPLICATION_JSON } )
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("habitaciones")
	public Response addHabitacion(Habitacion Habitacion) {
		try{
			TransactionManager tm = new TransactionManager( getPath( ) );
			tm.addHabitacion(Habitacion);
			return Response.status( 200 ).entity( Habitacion ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	/**
	 * Metodo que recibe un Oferta en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se actualiza la Base de datos con la informacion correspondiente al Oferta.<br/>
	 * @param Oferta JSON con la informacion del Oferta que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al Oferta que se desea modificar <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@PUT	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response retirarOferta(Oferta Oferta) {
		try{
			TransactionManager tm = new TransactionManager( getPath( ) );
			
			tm.retirarOferta(Oferta);
			return getOfertaById(Oferta.getId());			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
}