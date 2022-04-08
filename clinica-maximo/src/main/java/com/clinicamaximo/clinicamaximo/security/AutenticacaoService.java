package com.clinicamaximo.clinicamaximo.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.clinicamaximo.clinicamaximo.model.User;
import com.clinicamaximo.clinicamaximo.repository.UserRepository;

@Service
public class AutenticacaoService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(username);
		
		if (user.isPresent()) {
			return user.get();
		}
		
		throw new UsernameNotFoundException("Dados inválidos");
	}
}
