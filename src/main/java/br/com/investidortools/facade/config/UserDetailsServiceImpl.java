package br.com.investidortools.facade.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.investidortools.facade.dto.UserResponseDto;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final String PATH_GET_USER = "/v1/users/%s";
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value(value = "${url.service.user}")
    private String uriUserService;
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		
		ResponseEntity<UserResponseDto> user = 
				restTemplate.getForEntity(uriUserService.concat(String.format(PATH_GET_USER, email)), UserResponseDto.class);
        
		return UserDetailsImpl.build(user.getBody());
	}

}
