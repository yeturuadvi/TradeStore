package com.db.tradeStore.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.db.tradeStore.domain.Trade;
import com.db.tradeStore.exception.InvalidTradeException;
import com.db.tradeStore.service.TradeService;

/**
 * @author Advitha
 * 
 * Controller class which handles all the trade transmissions of a TradeStore
 *
 */
@Slf4j
@RestController
public class TradeController {
	
	private static final Logger log = LoggerFactory.getLogger(TradeController.class);
	
	@Autowired
    TradeService tradeService;
	
	/**
     * Validates and stores the Trade into Trade Store.
     * 
     * */
	@PostMapping("/trade")
    public ResponseEntity<String> validateStoreTrade(@RequestBody Trade trade){
		try{
			tradeService.isValid(trade);
			tradeService.persist(trade);
			return ResponseEntity.status(HttpStatus.OK).build();
		}catch(InvalidTradeException ex){
			log.error("Error while storing the Tade "+ ex.getTradeId() +" in the Trade Store :: "+ ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
    }

	/**
     * Fetches all the available Trades in the Trade Store.
     * 
     * */
    @GetMapping("/trades")
    public List<Trade> findAllTrades(){
        return tradeService.findAll();
    }

}
