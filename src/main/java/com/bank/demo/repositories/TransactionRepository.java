package com.bank.demo.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bank.demo.entities.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
                SELECT t FROM Transaction t
                WHERE t.fromAccount.id = :accountId
                   OR t.toAccount.id = :accountId
            """)
    List<Transaction> findAllByAccountId(@Param("accountId") long accountId);

    Optional<Transaction> findByPublicId(UUID publicId);
}
