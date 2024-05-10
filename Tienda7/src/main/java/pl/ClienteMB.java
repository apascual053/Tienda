package pl;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import dl.AppUser;

@Named
@RequestScoped
public class ClienteMB implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AppUser entity = new AppUser();

	public AppUser getEntity() {
		return entity;
	}

	public void setEntity(AppUser entity) {
		this.entity = entity;
	}
}
