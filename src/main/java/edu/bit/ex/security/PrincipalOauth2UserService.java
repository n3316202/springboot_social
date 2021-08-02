package edu.bit.ex.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import edu.bit.ex.mapper.EmpMapper;
import edu.bit.ex.provider.KakaoUserInfo;
import edu.bit.ex.provider.OAuth2UserInfo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	@Autowired
	private EmpMapper mapper;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info("Login for OAuth2Service =======================");
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		OAuth2UserInfo userInfo = null;
		
		log.info(userRequest.getClientRegistration().toString());
		log.info(userRequest.getAccessToken().toString());
		log.info(oauth2User.getAttributes().toString());
		
		if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
			userInfo = new KakaoUserInfo((Map)oauth2User.getAttributes());
		}
		
		log.info("Reqeust.. Access Token num" + userRequest.getAccessToken());
		
		/*
		MemberVO member = mapper.selectMember(userInfo.getProviderId());
		
		if(member == null) {			
			member = new MemberVO();
			member.setMember_id(userInfo.getProviderId());
			member.setPw(userInfo.getProvider() + "PW");
			member.setEmail(userInfo.getEmail());
			member.setMember_name(userInfo.getName());
			member.setSns_type(userInfo.getProvider());
			member.setPhone_num("unknown");
			mapper.insertMember(member);
			mapper.insertAuthorities(member);
			member = mapper.selectMember(userInfo.getProviderId());
		}
		

		return new MemberDetails(member, oauth2User.getAttributes());
		*/
		return null;
	}
	

}
