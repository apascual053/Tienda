package bl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dl.AppUser;
import dl.Pedido;
import dl.Producto;

@Path("/userEJB")
@LocalBean
@Stateless
public class UserEJB {
	
		public enum ResultCode {OK,BAD_PASWD, NO_USER};
	
		@PersistenceContext 
		private EntityManager em;
		@Resource 
		private EJBContext ejbContext;

		public ResultCode anadirCarrito(int idProducto) {
			
			try {
				String username = ejbContext.getCallerPrincipal().getName();
				AppUser cliente = em.createNamedQuery("AppUser.findAllUsername", AppUser.class).setParameter("username", username).getSingleResult();

				Pedido pedido = new Pedido();

				Producto producto = em.find(Producto.class, idProducto);

				pedido.setAppUser(cliente);
				pedido.setProducto(producto);
				pedido.setEnviado(false);
				pedido.setFechaHora(new Date());

				em.persist(pedido);
				return ResultCode.OK;


			} catch(Exception e) {
				return ResultCode.NO_USER;
			}
		}
		
		@Path("/getProductos")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public List<Producto> getProductos() {
			
			List<Producto> productos = em.createNamedQuery("Producto.findAll", Producto.class).getResultList();
			
			return productos;
		}

		public String obtenerNombre() {
			try {
				String username = ejbContext.getCallerPrincipal().getName();
				AppUser cliente = em.createNamedQuery("AppUser.findAllUsername", AppUser.class).setParameter("username", username).getSingleResult();
				return cliente.getNombre();
			} catch (Exception e) {
				return e.getMessage();
			}
		}	
}
