package com.nagarro.nagarroproject.Entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "statement")
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    private String dateField;
    private String amount;

    public Statement() {
    }

    public Statement(Account account, String dateField, String amount) {
        super();
        this.account = account;
        this.dateField = dateField;
        this.amount = amount;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    public String getDateField() {
        return dateField;
    }
    public void setDateField(String dateField) {
        this.dateField = dateField;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    @Override
    public String toString() {
        return "Statement [id=" + id + ", account=" + account + ", dateField=" + dateField + ", amount=" + amount + "]";
    }

}
