package bl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

		@Path("/anadir")
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public ResultCode anadirCarrito(JsonObject data) {
			
			System.out.println("New order request received: User=" + data.getString("username") + " Password=" + data.getString("password") + " Product=" + data.getInt("idProducto"));
			
			try {
				AppUser cliente = em.createNamedQuery("AppUser.findAllUsername", AppUser.class).setParameter("username", data.getString("username")).getSingleResult();

				if(cliente.getPassword().equals(data.getString("password"))) {
					Pedido pedido = new Pedido();

					Producto producto = em.find(Producto.class, data.getInt("idProducto"));

					pedido.setAppUser(cliente);
					pedido.setProducto(producto);
					pedido.setEnviado(false);
					pedido.setFechaHora(new Date());

					em.persist(pedido);
					return ResultCode.OK;
				} else {
					return ResultCode.BAD_PASWD;
				}

				
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
}
