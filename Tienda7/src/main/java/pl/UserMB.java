package pl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import bl.UserEJB;
import bl.UserEJB.ResultCode;
import dl.Producto; 

@Named
@ViewScoped
public class UserMB implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String dni, pswd;
	
	private WebTarget cliente = ClientBuilder.newClient().target("http://localhost:8080/Tienda/rest/userEJB/");
	
	@EJB
	private UserEJB ejb;
	
	private List<Producto> productos;
	private String mensajes[] = {"",
								"Contraseña incorrecta",
								"Usuario no encontrado",
								"Error en el servidor"};
	
	public void anadirCarrito(int idProducto) {
		
		Form data = new Form();
		data.param("username", dni);
		data.param("password", pswd);
		data.param("idProducto", Integer.toString(idProducto));
		
		ResultCode result = cliente.path("anadir").request(MediaType.TEXT_PLAIN).post(Entity.form(data), ResultCode.class);
		
		if (result != ResultCode.OK) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(mensajes[result.ordinal()]));
		}
	}
	
	public List<Producto> getProductos() {
		productos = cliente.path("getProductos").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Producto>>(){});
		return productos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
}
