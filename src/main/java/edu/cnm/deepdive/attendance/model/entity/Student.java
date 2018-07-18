package edu.cnm.deepdive.attendance.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.net.URI;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.stereotype.Component;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(value={"id", "create"}, allowGetters = true, ignoreUnknown = true)
@Component
@Entity
public class Student {

  private static EntityLinks entityLinks;

  @PostConstruct
  private void init() {
    String ignore = entityLinks.toString();
  }
  @Autowired
  private void setEntityLinks( EntityLinks entityLinks) {
    Student.entityLinks = entityLinks;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "student_id", nullable = false, updatable = false)
  private long id;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @Column(length = 100, nullable = false)
  private String lastName;

  @Column(length = 100, nullable = false)
  private String firstName;

  @Column(length = 20)
  private String phone;

  public long getId() {
    return id;
  }

  public Date getCreated() {
    return created;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public URI getHref() {
    return  entityLinks.linkFor(Student.class, id).toUri();
  }

  public void patch(Student update) {
    if (update.lastName != null) {
      lastName = update.lastName;
    }
    if (update.firstName != null) {
      firstName = update.firstName;
    }
    if (update.phone != null) {
      phone = update.phone;
    }
  }

}
