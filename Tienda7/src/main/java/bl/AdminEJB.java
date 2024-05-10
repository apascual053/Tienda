package bl;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.websocket.server.PathParam;

import org.jboss.ejb3.annotation.SecurityDomain;

import dl.AppUser;
import dl.Categoria;
import dl.Pedido;
import dl.Producto;

@LocalBean
@Stateless
@SecurityDomain("ExamDbApplicationDomain")
@RolesAllowed("admin")
public class AdminEJB {
	
	public enum ResultCode {OK,ERROR, SHORT_PSWD, USER_EXIST, PROD_EXIST};
	
	@PersistenceContext 
	private EntityManager em;
	@Resource 
	private EJBContext ejbContext;
	
	public List<Categoria> getCategorias() {
		return em.createNamedQuery("Categoria.findAll", Categoria.class).getResultList();
	}
	
	public List<AppUser> getClientes() {
		return em.createNamedQuery("AppUser.findAll", AppUser.class).getResultList();
	}
	
	public List<Producto> getProductos() {
		return em.createNamedQuery("Producto.findAll", Producto.class).getResultList();
	}
	
	public List<Pedido> getPedidos() {
		return em.createNamedQuery("Pedido.findAll", Pedido.class).getResultList();
	}
	
	public ResultCode eliminaProducto(@PathParam("idProducto") int idProducto) {
		Producto producto = em.find(Producto.class, idProducto);
		if(producto != null) {
			em.remove(producto);
			return ResultCode.OK;
		} else {return ResultCode.ERROR;}
	}
	
	public ResultCode eliminaPedido(@PathParam("idPedido") int idPedido) {
		Pedido pedido = em.find(Pedido.class, idPedido);
		if(pedido != null) {
			em.remove(pedido);
			return ResultCode.OK;
		} else {return ResultCode.ERROR;}
	}
	
	public ResultCode eliminaCliente(@PathParam("idCliente") int idCliente) {
		AppUser cliente = em.find(AppUser.class, idCliente);
		if(cliente != null) {
			em.remove(cliente);
			return ResultCode.OK;
		} else {return ResultCode.ERROR;}
	}
	
	public ResultCode anadirCliente(AppUser cliente) {
		if(em.createQuery("SELECT c FROM AppUser c WHERE c.username = :username")
				.setParameter("username", cliente.getUsername())
				.getResultList()
				.isEmpty()) {
			if(cliente.getPassword().length() >= 3) {
				em.persist(cliente);
				return ResultCode.OK;
			} else {
				return ResultCode.SHORT_PSWD;
			}
		} else {
			return ResultCode.USER_EXIST;
		}
	}
	
	public ResultCode anadirProducto(Producto producto) {
		if(em.createQuery("SELECT p FROM Producto p WHERE p.descripcion = :descripcion")
					.setParameter("descripcion", producto.getDescripcion())
					.getResultList()
					.isEmpty()) {
				em.persist(producto);
				return ResultCode.OK;
			} else {
				return ResultCode.PROD_EXIST;
			}
	}
	
	public ResultCode enviarPedido(@PathParam("idPedido") int idPedido) {
		Pedido pedido = em.find(Pedido.class, idPedido);
		pedido.setEnviado(true);
		em.persist(pedido);
		return ResultCode.OK;
	}

}
