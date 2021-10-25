package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rent_proposal", uniqueConstraints = @UniqueConstraint(columnNames = {"start_date", "end_date", "article_id", "renter_id"}))
public class RentProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rent_proposal_id_seq")
    @SequenceGenerator(sequenceName = "rent_proposal_id_seq", name = "rent_proposal_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 310)
    private String message;

    @Column(nullable = false)
    private Integer state;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "renter_id", referencedColumnName = "id")
    private User renter;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;

    RentProposal(){

    }

    public RentProposal(long id, String message, Integer state, Date startDate, Date endDate) {
        this(message, state, startDate, endDate);
        this.id = id;
    }

    public RentProposal(String message, Integer state, Date startDate, Date endDate) {
        this.message = message;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public String toString() {
        return '\n' + "startDate: " + startDate + " | endDate: " +
                endDate + " | Message: " + message + " | Article id: " + article.getId() + '\n';
    }
}
