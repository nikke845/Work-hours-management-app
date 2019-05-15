package hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain.Account;
import hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain.AccountRepository;

/**
 * This class is used by spring security to authenticate and authorize user
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService  {
	private final AccountRepository repository;

	@Autowired
	public UserDetailServiceImpl(AccountRepository accountRepository) {
		this.repository = accountRepository;
	}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {   
    	Account curruser = repository.findByUsername(username);
        UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPasswordHash(), 
        		AuthorityUtils.createAuthorityList(curruser.getRole()));
        return user;
    }

}