package com.db.tradeStore.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.db.tradeStore.domain.Trade;

/**
 * @author Advitha
 *
 * Repository used to do CURD operations on Trade table  
 *
 */

@Repository
public interface TradeRepository extends CrudRepository<Trade, String>{
	
}
