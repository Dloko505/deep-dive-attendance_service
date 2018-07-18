package edu.cnm.deepdive.attendance.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.net.URI;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.stereotype.Component;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(value={"id", "create", "absences"}, allowGetters = true, ignoreUnknown = true)
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

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL)
  @OrderBy("start DESC")
  private List<Absence> absences = new LinkedList<>();

  public URI getHref() {
    return  entityLinks.linkForSingleResource(Student.class, id).toUri();
  }

  @Formula("(SELECT COUNT (*) FROM absence AS ab WHERE ab.student_id = student_id)")
  private int totalAbsences;

  @Formula("(SELECT COUNT (*) FROM absence AS ab WHERE ab.student_id = student_id AND NOT ab.excused)")
  private int unexcusedAbsences;

  public long getId() {
    return id;
  }

  public Date getCreated() {
    return created;
  }

  public int getTotalAbsences() {
    return totalAbsences;
  }

  public int getUnexcusedAbsences() {
    return unexcusedAbsences;
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

  public List<Absence> getAbsences() {
    return absences;
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
