package edu.bit.ex.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{
	
	private Map<String, Object> attributes;
	
	public KakaoUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getProviderId() {
		return Integer.toString((int)attributes.get("id"));
	}

	@Override
	public String getProvider() {
		return "kakao";
	}

	@Override
	public String getEmail() {
		return (String) ((Map) attributes.get("kakao_account")).get("email");
	}

	@Override
	public String getName() {
		return (String) ((Map)((Map) attributes.get("kakao_account")).get("profile")).get("nickname");
	}

}
