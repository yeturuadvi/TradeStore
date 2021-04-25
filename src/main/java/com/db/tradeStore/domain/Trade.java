package com.db.tradeStore.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Advitha
 *
 *Trade table used to store the details of the incoming trades
 *
 */

@Entity
@Table(name = "Trade")
public class Trade {
	
    @Id
    @Column(name = "trade_id", unique = true, nullable = false)
    private String tradeId;

    private int version;

    @Column(name = "counter_party_id")
    private String counterParty;

    @Column(name = "book_id")
    private String bookId;

    @Column(name = "maturity_date")
    private LocalDate maturityDate;

    @Column(name = "created_date")
    private LocalDate createdDate;

    private String expired;

    
	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCounterParty() {
		return counterParty;
	}

	public void setCounterParty(String counterParty) {
		this.counterParty = counterParty;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public LocalDate getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(LocalDate maturityDate) {
		this.maturityDate = maturityDate;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

	@Override
	public String toString() {
		return "Trade [tradeId=" + tradeId + ", version=" + version
				+ ", counterParty=" + counterParty + ", bookId=" + bookId
				+ ", maturityDate=" + maturityDate + ", createdDate="
				+ createdDate + ", expired=" + expired + "]";
	}
    

}
