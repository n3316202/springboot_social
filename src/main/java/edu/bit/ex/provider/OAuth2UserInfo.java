package edu.bit.ex.provider;

public interface OAuth2UserInfo {
	
	String getProviderId();
	String getProvider();
	String getEmail();
	String getName();
}
