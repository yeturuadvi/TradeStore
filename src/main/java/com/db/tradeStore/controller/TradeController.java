package com.db.tradeStore.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.db.tradeStore.service.TradeService;

/**
 * @author Advitha
 * 
 * Controller class which handles all the trade transmissions of a TradeStore
 *
 */
public class TradeController {
	
	@Autowired
    TradeService tradeService;

}
