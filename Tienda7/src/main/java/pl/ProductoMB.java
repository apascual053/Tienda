package pl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bl.AdminEJB;
import dl.Categoria;
import dl.Producto;

@Named
@RequestScoped
public class ProductoMB implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private AdminEJB ejb;
	
	private Producto entity = new Producto();
	private int indexCategoria;
	
	private List<Categoria> categorias;

	public Producto getEntity() {
		return entity;
	}

	public void setEntity(Producto entity) {
		this.entity = entity;
	}
	
	public int getCategoria() {
		return indexCategoria;
	}
	
	public void setCategoria(int indice) {
		indexCategoria = indice;
		entity.setCategoria(getCategorias().get(indexCategoria));;
	}
	
	public List<Categoria> getCategorias() {
		if (categorias == null) {
			categorias = ejb.getCategorias();
		}
		return categorias;
	}
}
