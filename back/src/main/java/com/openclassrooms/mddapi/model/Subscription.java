// package com.openclassrooms.mddapi.model;

// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.ManyToOne;
// import javax.persistence.Table;

// @Entity
// @Table(name = "subscriptions") 
// public class Subscription {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Integer id;
    
//     @ManyToOne
//     @JoinColumn(name = "user_id")
//     private User user;
//     @ManyToOne
//     @JoinColumn(name = "topic_id")
//     private Topic topic;

// }
