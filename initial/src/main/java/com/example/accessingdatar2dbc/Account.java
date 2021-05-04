package com.example.accessingdatar2dbc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

/**
 * Persistent account entity with JPA markup. Accounts are stored in an H2
 * relational database.
 * 
 * @author Paul Chapman
 */

public class Account implements Persistable {

//	private static final long serialVersionUID = 1L;

//	public static Long nextId = 0L;

	@Id
	protected String id;

	protected String number;

//	@Column(name = "name")
	protected String owner;

	protected String lastName;

	protected String firstName;

	protected int balance;

	/**
	 * This is a very simple, and non-scalable solution to generating unique
	 * ids. Not recommended for a real application. Consider using the
	 * <tt>@GeneratedValue</tt> annotation and a sequence to generate ids.
	 *
	 * @return The next available id.
	 */
//	protected static Long getNextId() {
//		synchronized (nextId) {
//			return nextId++;
//		}
//	}

	/**
	 * Default constructor for JPA only.
	 */
//	protected Account() {
//		balance = BigDecimal.ZERO;
//	}

	public Account(String number, String lastName, String firstName, int balance) {
//		id = getNextId();
//		this.id = id;
		this.number = number;
		this.lastName = lastName;
		this.firstName = firstName;
		this.balance = balance;
	}

	public String getId() {
		return id;
	}

	/**
	 * Set JPA id - for testing and JPA only. Not intended for normal use.
	 *
	 * @param id
	 *            The new id.
	 */
	protected void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	protected void setNumber(String accountNumber) {
		this.number = accountNumber;
	}

	public String getFirstName() {
		return number;
	}

	protected void setFirstName(String firstName) {
		this.number = firstName;
	}

	public String getLastName() {
		return number;
	}

	protected void setLastName(String lastName) {
		this.number = lastName;
	}

	public String getOwner() {
		return owner;
	}

	protected void setOwner(String owner) {
		this.owner = owner;
	}

	public int getBalance() {
//		return balance.setScale(2, RoundingMode.HALF_EVEN);
		return balance;
	}

//	public void withdraw(BigDecimal amount) {
//		balance.subtract(amount);
//	}
//
//	public void deposit(BigDecimal amount) {
//		balance.add(amount);
//	}

	@Transient
	private boolean newAccount;

	@Override
	@Transient
	public boolean isNew() {
		return this.newAccount || id == null;
	}

	public Account setAsNew(){
		this.newAccount = true;
		return this;
	}

	@Override
	public String toString() {
		return number + " [" + lastName + "," + firstName + "]: $" + balance;
	}

//	@Override
//	public String toString() {
//		return String.format(
//				"Account[id=%s, number='%s', owner='%s', balance='%s']",
//				id, number, owner, balance);
//	}

}