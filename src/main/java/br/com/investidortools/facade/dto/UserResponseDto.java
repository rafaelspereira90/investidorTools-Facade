package br.com.investidortools.facade.dto;

import br.com.investidortools.facade.enums.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

	private String name;
	
	private String email;
	
	private String tel;
	
	private UserTypeEnum type;
	
	private String pass;
}
