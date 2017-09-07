package com.umanteam.dadakar.back.security;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.umanteam.dadakar.back.dto.AccountDTO;
import com.umanteam.dadakar.back.service.interfaces.IAccountService;

@Service
public class AccountDetailService implements UserDetailsService {
	
	private final IAccountService accountService;

	public AccountDetailService(IAccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final AccountDTO account = accountService.findByUsername(username);
		if(account == null || account.getAccountId().equals("")) throw new UsernameNotFoundException("L'utilisateur n'existe pas.");
		
		return User
				.withUsername(username)
				.password(account.getPassword())
				.authorities(Collections.emptyList())
				.accountExpired(false)
				.accountLocked(false)
				.credentialsExpired(false)
				.disabled(false)
				.build();
	}

}
