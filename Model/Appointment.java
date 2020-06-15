/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mitchell Bryant
 */
@Entity
@Table(name = "appointment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Appointment.findAll", query = "SELECT a FROM Appointment a")
    , @NamedQuery(name = "Appointment.findByAppointmentId", query = "SELECT a FROM Appointment a WHERE a.appointmentId = :appointmentId")
    , @NamedQuery(name = "Appointment.findByCustomerId", query = "SELECT a FROM Appointment a WHERE a.customerId = :customerId")
    , @NamedQuery(name = "Appointment.findByTitle", query = "SELECT a FROM Appointment a WHERE a.title = :title")
    , @NamedQuery(name = "Appointment.findByUrl", query = "SELECT a FROM Appointment a WHERE a.url = :url")
    , @NamedQuery(name = "Appointment.findByStart", query = "SELECT a FROM Appointment a WHERE a.start = :start")
    , @NamedQuery(name = "Appointment.findByEnd", query = "SELECT a FROM Appointment a WHERE a.end = :end")
    , @NamedQuery(name = "Appointment.findByCreateDate", query = "SELECT a FROM Appointment a WHERE a.createDate = :createDate")
    , @NamedQuery(name = "Appointment.findByCreatedBy", query = "SELECT a FROM Appointment a WHERE a.createdBy = :createdBy")
    , @NamedQuery(name = "Appointment.findByLastUpdate", query = "SELECT a FROM Appointment a WHERE a.lastUpdate = :lastUpdate")
    , @NamedQuery(name = "Appointment.findByLastUpdateBy", query = "SELECT a FROM Appointment a WHERE a.lastUpdateBy = :lastUpdateBy")})
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "appointmentId")
    private Integer appointmentId;
    @Basic(optional = false)
    @Column(name = "customerId")
    private int customerId;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Lob
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Lob
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @Lob
    @Column(name = "contact")
    private String contact;
    @Basic(optional = false)
    @Column(name = "url")
    private String url;
    @Basic(optional = false)
    @Column(name = "start")
    @Temporal(TemporalType.TIMESTAMP)
    private int start;
    @Basic(optional = false)
    @Column(name = "end")
    @Temporal(TemporalType.TIMESTAMP)
    private int end;
    @Basic(optional = false)
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Basic(optional = false)
    @Column(name = "createdBy")
    private String createdBy;
    @Basic(optional = false)
    @Column(name = "lastUpdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Basic(optional = false)
    @Column(name = "lastUpdateBy")
    private String lastUpdateBy;
    private  SimpleStringProperty apptTitle = new SimpleStringProperty("");
    private  SimpleStringProperty apptDescription = new SimpleStringProperty("");
    private  SimpleStringProperty customerName = new SimpleStringProperty("");
    private  SimpleStringProperty apptStart = new SimpleStringProperty("");
    private  SimpleStringProperty apptEnd = new SimpleStringProperty("");

    public Appointment() {
        this(0,0,"","","","","","","");
    }
    public Appointment(String apptTitle, String customerName, String apptStart, String apptEnd) {
        this.apptTitle = new SimpleStringProperty(apptTitle);
        this.customerName = new SimpleStringProperty(customerName);
        this.apptStart = new SimpleStringProperty(apptStart);
        this.apptEnd = new SimpleStringProperty(apptEnd);  
    }

       public Appointment(int appointmentId,int customerId,String apptTitle,String apptDescription, String customerName, String apptStart, String apptEnd, String createdBy, String lastUpdateBy){
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.apptTitle = new SimpleStringProperty(apptTitle);
        this.apptDescription = new SimpleStringProperty(apptDescription);
        this.customerName = new SimpleStringProperty(customerName);
        this.apptStart = new SimpleStringProperty(apptStart);
        this.apptEnd = new SimpleStringProperty(apptEnd);
        this.createdBy = createdBy;
        this.lastUpdateBy = lastUpdateBy;
    }

    public Appointment(Integer appointmentId, int customerId, String title, String description, String location, String contact, String url, int start, int end, Date createDate, String createdBy, Date lastUpdate, String lastUpdateBy) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.url = url;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
    public StringProperty titleProperty(){
        return apptTitle;
    }
    public StringProperty descriptionProperty(){
        return apptDescription;
    }
    public StringProperty customerNameProperty(){
        return customerName;
    }
    public StringProperty startProperty(){
        return apptStart;
    }
    public StringProperty endProperty(){
        return apptEnd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appointmentId != null ? appointmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Appointment)) {
            return false;
        }
        Appointment other = (Appointment) object;
        if ((this.appointmentId == null && other.appointmentId != null) || (this.appointmentId != null && !this.appointmentId.equals(other.appointmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mitchellbryantsoftware2.Appointment[ appointmentId=" + appointmentId + " ]";
    }
    
}
