package com.ekocaman.microservices.account.repo;


import com.ekocaman.microservices.account.model.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface AccountRepository extends Repository<Account, Long> {
    public Account findByNumber(String accountNumber);

    public List<Account> findByOwnerContainingIgnoreCase(String partialName);

    @Query("SELECT count(*) from Account")
    public int countAccounts();
}
