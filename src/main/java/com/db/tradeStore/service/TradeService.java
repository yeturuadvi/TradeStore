package com.db.tradeStore.service;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.tradeStore.repository.TradeRepository;

/**
 * @author Advitha
 * 
 * Service class using which we can communicate to the data base via repository
 *
 */
@Slf4j
@Service
public class TradeService {
	
	private static final Logger log = LoggerFactory.getLogger(TradeService.class);
	
    @Autowired
    TradeRepository tradeRepository;
    
    

}
