package dl;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the Pedido database table.
 * 
 */
@Entity
@NamedQuery(name="Pedido.findAll", query="SELECT p FROM Pedido p")
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idPedido;
	private boolean enviado;
	private Date fechaHora;
	private AppUser appUser;
	private Producto producto;

	public Pedido() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getIdPedido() {
		return this.idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}


	public boolean getEnviado() {
		return this.enviado;
	}

	public void setEnviado(boolean enviado) {
		this.enviado = enviado;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}


	//uni-directional many-to-one association to AppUser
	@ManyToOne
	@JoinColumn(name="idCliente")
	public AppUser getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}


	//uni-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="idProducto")
	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}