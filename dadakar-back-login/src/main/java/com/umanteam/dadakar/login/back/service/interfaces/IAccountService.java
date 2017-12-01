package com.umanteam.dadakar.login.back.service.interfaces;

import java.util.List;

import com.umanteam.dadakar.login.back.dto.AccountDTO;
import com.umanteam.dadakar.login.back.enums.Role;

public interface IAccountService {
	AccountDTO addOrUpdate(AccountDTO accountDTO);
	void delete(String id);
	List<AccountDTO> findAll();
	AccountDTO findById(String id);
	AccountDTO findByUsername(String username);
	List<AccountDTO> findByRole(Role role);
	List<AccountDTO> findAdminsAndSuperUsers();
	List<AccountDTO> findByBanned(boolean banned);
	List<AccountDTO> findByDeleted(boolean deleted);
	List<AccountDTO> findByDeletedAndRole(boolean deleted, Role role);
}
