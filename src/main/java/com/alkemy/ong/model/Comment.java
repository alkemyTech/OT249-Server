package com.alkemy.ong.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@SQLDelete(sql = "UPDATE comments SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@NoArgsConstructor
@Getter @Setter
public class Comment {
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	private String body;
	
	private Timestamp timestamp;

    private boolean deleted = Boolean.FALSE;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
    
    @ManyToOne
    @JoinColumn(name = "news_id", referencedColumnName = "id")
	private News news;
}
