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
import vos.Funcionamiento;
import vos.IndiceOcupacion;
import vos.Operador;
import vos.OperadorGanancia;



/**
 * Clase que expone servicios REST con ruta base: http://localhost:8080/AlohAndes/rest/operadores/...
 */
@Path("operadores")
public class OperadoresService {

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
	 * Metodo GET que trae a todos los operadores en la Base de datos. <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/AlohAndes/rest/operadores <br/>
	 * @return	<b>Response Status 200</b> - JSON que contiene a todos los Operadores que estan en la Base de Datos <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */			
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getOperadores() {
		
		try {
			TransactionManager tm = new TransactionManager(getPath());
			
			List<Operador> operadores;
			operadores = tm.getAllOperadores();
			return Response.status(200).entity(operadores).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	/**
	 * Metodo GET que trae al Operador en la Base de Datos con el ID dado por parametro <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/TransactionManager/rest/Operadores/{id} <br/>
	 * @return	<b>Response Status 200</b> - JSON Operador que contiene al Operador cuyo ID corresponda al parametro <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getOperadorById( @PathParam( "id" ) Long id )
	{
		try{
			TransactionManager tm = new TransactionManager( getPath( ) );
			
			Operador operador = tm.getOperadorById( id );
			return Response.status( 200 ).entity( operador ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	/**
	 * Metodo que recibe un Operador en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se agrega a la Base de datos la informacion correspondiente al Operador. <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/Operadores <br/>
	 * @param Operador JSON con la informacion del Operador que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al Operador que ha sido agregado <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@POST
	@Produces( { MediaType.APPLICATION_JSON } )
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response addOperador(Operador Operador) {
		
		try{
			TransactionManager tm = new TransactionManager( getPath( ) );
			tm.addOperador(Operador);
			return Response.status( 200 ).entity( Operador ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	

	/**
	 * Metodo que recibe un Operador en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se actualiza la Base de datos con la informacion correspondiente al Operador.<br/>
	 * @param Operador JSON con la informacion del Operador que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al Operador que se desea modificar <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@PUT	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateOperador(Operador Operador) {
		try{
			TransactionManager tm = new TransactionManager( getPath( ) );
			tm.updateOperador(Operador);
			return Response.status( 200 ).entity( Operador ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	/**
	 * Metodo que recibe un Operador en formato JSON y lo elimina de la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se elimina de la Base de datos al Operador con la informacion correspondiente.<br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/Operadores <br/>
	 * @param Operador JSON con la informacion del Operador que se desea eliminar
	 * @return	<b>Response Status 200</b> - JSON que contiene al Operador que se desea eliminar <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response deleteOperador(Operador Operador) {
    	try{
			TransactionManager tm = new TransactionManager( getPath( ) );
			tm.deleteOperador(Operador);
			return Response.status( 200 ).entity( Operador ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
    
    /**
   	 * Metodo que recibe un Operador en formato JSON y lo agrega a la Base de Datos <br/>
   	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
   	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/Operadors <br/>
   	 * @param Operador JSON con la informacion del Operador que se desea agregar
   	 * @return	<b>Response Status 200</b> - JSON que contiene al Operador que ha sido agregado <br/>
   	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
   	 */
       @GET
   	@Produces({ MediaType.APPLICATION_JSON})
   	@Path("gananciasactuales")
   	public Response getGananciasActualesOperadores() {
   		try{
   			TransactionManager tm = new TransactionManager( getPath( ) );
   			List <OperadorGanancia> x = tm.getGananciasActualesOperadores();
   			return Response.status( 200 ).entity( x ).build( );			
   		}
   		catch( Exception e )
   		{
   			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
   		}
   	}
       /**
   	 * Metodo que recibe un Operador en formato JSON y lo agrega a la Base de Datos <br/>
   	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
   	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/Operadors <br/>
   	 * @param Operador JSON con la informacion del Operador que se desea agregar
   	 * @return	<b>Response Status 200</b> - JSON que contiene al Operador que ha sido agregado <br/>
   	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
   	 */
       @GET
   	@Produces({ MediaType.APPLICATION_JSON})
   	@Path("gananciaspasadas")
   	public Response getGananciasPasadasOperadores() {
   		try{
   			TransactionManager tm = new TransactionManager( getPath( ) );
   			List <OperadorGanancia> x = tm.getGananciasPasadasOperadores();
   			return Response.status( 200 ).entity( x ).build( );			
   		}
   		catch( Exception e )
   		{
   			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
   		}
   	}
       /**
   	 * Metodo que recibe un Operador en formato JSON y lo agrega a la Base de Datos <br/>
   	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
   	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/Operadors <br/>
   	 * @param Operador JSON con la informacion del Operador que se desea agregar
   	 * @return	<b>Response Status 200</b> - JSON que contiene al Operador que ha sido agregado <br/>
   	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
   	 */
       @GET
   	@Produces({ MediaType.APPLICATION_JSON})
   	@Path("indicesocupaciones")
   	public Response getIndicesOcupacion() {
   		try{
   			TransactionManager tm = new TransactionManager( getPath( ) );
   			List <IndiceOcupacion> x = tm.getIndicesOcupacion();
   			return Response.status( 200 ).entity( x ).build( );			
   		}
   		catch( Exception e )
   		{
   			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
   		}
   	}
       /**
   	 * Metodo que consulta la Base de Datos para dar un informe del uso de alohandes <br/>
   	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
   	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/Operadors <br/>
   	 * @param Operador JSON con la informacion del Operador que se desea agregar
   	 * @return	<b>Response Status 200</b> - JSON que contiene al Operador que ha sido agregado <br/>
   	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
   	 */
       @GET
   	@Produces({ MediaType.APPLICATION_JSON})
   	@Path("funcionamiento")
   	public Response getFuncionamientoAlohandes() {
   		try{
   			TransactionManager tm = new TransactionManager( getPath( ) );
   			List <Funcionamiento> x = tm.getFuncionamientoAlohandes();
   			return Response.status( 200 ).entity( x ).build( );			
   		}
   		catch( Exception e )
   		{
   			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
   		}
   	}
   

}
