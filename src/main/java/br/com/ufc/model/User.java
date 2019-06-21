package br.com.ufc.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
@Entity
public class User implements UserDetails{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long code;
	@NotBlank(message="Por favor, preencha o campo email")
	private String email;
	@NotBlank(message="Por favor, preencha o campo senha")
	private String pass;
	@NotBlank(message="Por favor, preencha o campo nome")
	private String name;
	@NotBlank(message="Por favor, preencha o campo CPF")
	private String cpf;
	@NotBlank(message="Por favor, preencha o campo endereço")
	private String address;
	
	@NotNull(message="Por favor, preencha o campo data de nascimento")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable( 
	        name = "users_roles", 
	        joinColumns = @JoinColumn(
	          name = "user_code", referencedColumnName = "code"), 
	        inverseJoinColumns = @JoinColumn(
	          name = "role_code", referencedColumnName = "role")) 
	private List<Role> roles;
	
	@OneToMany(mappedBy = "user", targetEntity = Pedido.class)
	private List<Pedido> pedidos;
	
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public List<Pedido> getPedidos() {
		return pedidos;
	}
	public void setOrders(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return (Collection<? extends GrantedAuthority>) this.roles;
	}
	@Override
	public String getUsername() {
		return email;
	}
	@Override
	public String getPassword() {
		return pass;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
