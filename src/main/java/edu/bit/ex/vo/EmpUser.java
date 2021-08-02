package edu.bit.ex.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmpUser extends User {
	
	private EmpVO emp;
	
	public EmpUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public EmpUser(EmpVO empVO) {
		super(empVO.getEname(), empVO.getEmpno(), getAuth(empVO)); // Sting, String �삎�떇�씠�뼱�꽌 �씪�떒 empno瑜� String�쑝濡� 諛붽퓭以щ뒗�뜲 �맆吏�..?
		this.emp = empVO;
		
	}
	
	private static Collection<? extends GrantedAuthority> getAuth(EmpVO empVO) {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	
		authorities.add(new SimpleGrantedAuthority(empVO.getAuthority()));
	     
		return authorities;
	}
}