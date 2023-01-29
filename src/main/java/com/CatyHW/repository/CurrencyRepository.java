package com.CatyHW.repository;

import com.CatyHW.model.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findById(long id);
}
