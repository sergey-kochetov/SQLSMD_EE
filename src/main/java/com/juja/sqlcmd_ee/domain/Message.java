package com.juja.sqlcmd_ee.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer author;

    private String filename;

    public Message() {
    }

    public Message(String text, String tag, Customer author) {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }

    public String getAuthor() {
        return author != null ? author.getUsername() : "<none>";
    }
}
