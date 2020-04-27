package com.example.emailsending.repositories;

import com.example.emailsending.model.StockData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDataRepository extends CrudRepository<StockData, String> {
}
