package pl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import bl.AdminEJB;
import bl.AdminEJB.ResultCode;
import dl.AppUser;
import dl.Pedido;
import dl.Producto;

@Named
@ViewScoped
public class AdminMB implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private AdminEJB ejb;
	
	private List<Producto> productos;
	private List<AppUser> clientes;
	private List<Pedido> pedidos;
	
	private String mensajes[] = {"", "El producto ya existe",
									"El usuario ya existe",
									"La contraseña es demasiado corta"};
	
	public List<Producto> getProductos() {
		if (productos == null) {
			productos = ejb.getProductos();
		}
		return productos;
	}
	
	public void eliminarProducto(int idProducto) {
		ejb.eliminaProducto(idProducto);
		productos = null;
	}
	
	public String anadirProducto(Producto producto) {
		ResultCode result = ejb.anadirProducto(producto);
		if(result != ResultCode.OK) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(mensajes[result.ordinal()]));
		}
		return "administrador.xthml";
	}
	
	public List<AppUser> getClientes() {
		if(clientes == null) {
			clientes = ejb.getClientes();
		}
		return clientes;
	}
	
	public void eliminarCliente(int dni) {
		ejb.eliminaCliente(dni);
		clientes = null;
		pedidos = null;
	}
	
	public String anadirCliente(AppUser cliente) {
		ResultCode result = ejb.anadirCliente(cliente);
		if(result != ResultCode.OK) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(mensajes[result.ordinal()]));
		}
		return "administrador.xhtml";
	}
	
	public List<Pedido> getPedidos() {
		if(pedidos == null) {
			pedidos = ejb.getPedidos();
		}
		return pedidos;
	}
	
	public void enviarPedido(int idPedido) {
		ejb.enviarPedido(idPedido);
		pedidos = null;
	}
	
	public void eliminarPedido(int idPedido) {
		ejb.eliminaPedido(idPedido);
		pedidos = null;
	}

}
