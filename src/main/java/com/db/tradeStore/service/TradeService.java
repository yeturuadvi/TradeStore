package com.db.tradeStore.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.tradeStore.domain.Trade;
import com.db.tradeStore.exception.InvalidTradeException;
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
    
    /**
     * Validates the availability of the lower version of the Trade.
     * 
     * */
    private boolean validateTradeVersion(Trade nt,Trade ot) throws InvalidTradeException{
        if(nt.getVersion() >= ot.getVersion()){
            return true;
        }else {
        	 throw new InvalidTradeException(nt.getTradeId(), "Older version of trade recieved");
        }  
    }
    
    /**
     * 2. Store should not allow the trade which has less maturity date then today date
     * 
     * */
    private boolean validateMaturityDate(Trade trade) {
    	return trade.getMaturityDate().isBefore(LocalDate.now())  ? false : true;
    }
    
    
    /**
     * 1. During transmission if the lower version is being received by the store it will reject the trade and throw an exception.
     * 
     * */
    public boolean isValid(Trade trade) throws InvalidTradeException {
        if(validateMaturityDate(trade)) {
            Optional<Trade> exsitingTrade = tradeRepository.findById(trade.getTradeId());
             if (exsitingTrade.isPresent()) {
                 return validateTradeVersion(trade, exsitingTrade.get());
             }else{
                 return true;
             }
         }else {
        	 throw new InvalidTradeException(trade.getTradeId(), "Maturity date of Trade should not be less than today's date");
         }
    }
    
    /**
     * Save or Update the trade in the Trade store
     * 
     * */
    public void  persist(Trade trade){
    	
        trade.setCreatedDate(LocalDate.now());
        tradeRepository.save(trade);
     }

    /**
     * Fetches all the trades available in the Trade store
     * 
     * */
     public List<Trade> findAll(){
        return  (List<Trade>) tradeRepository.findAll();
     }

     
     /**
      * Validate expire date of available trades in the Trade store, if required update the same. 
      * 
      * */
     public void updateTradeExpiryFlag(){
         ((Collection<Trade>) tradeRepository.findAll()).stream().forEach(t -> {
                 if (!validateMaturityDate(t)) {
                     t.setExpired("Y");
                     log.info("Updating the expire date of the Trade {}", t);
                     tradeRepository.save(t);
                 }
             });
         }


}
