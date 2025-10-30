// package com.openclassrooms.mddapi.model;

// import java.time.Instant;

// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.ManyToOne;
// import javax.persistence.Table;

// import org.hibernate.annotations.CreationTimestamp;

// @Entity
// @Table(name = "comments") 
// public class Comment {
    
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Integer id;

//     @ManyToOne
//     @JoinColumn(name = "author_id")
//     private User author;
//     private String content;

//     @CreationTimestamp
//     @Column(name = "created_at", nullable = false, updatable = false)
//     private Instant createdAt;
// }
