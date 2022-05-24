package com.senla.course.announcementPlatform.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "announcement", schema = "private_announcements")
public class Announcement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Announcement")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Integer price;
    @Temporal(TemporalType.DATE)
    @Column(name = "startDate", columnDefinition = "DATE", nullable = true)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "soldDate", columnDefinition = "DATE", nullable = true)
    private Date endDate;
    @Column(name = "description")
    private String description;
    @Column(name = "vip")
    private boolean vip;
    @Column(name = "sold")
    private boolean sold;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(targetEntity = Comment.class, mappedBy = "announcement", fetch=FetchType.EAGER)
    private List<Comment> comments;
    @Column(name = "rating_user")
    private Double rating;

    public Double getRating() {
        Double thisRating;
        if (user.getRatings() == null) {
            thisRating = rating;
        } else {
            thisRating = user.getRatings().stream().collect(Collectors.averagingInt(Rating::getRating));
        }
        return thisRating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static final Comparator<Announcement> COMPARE_BY_RATING = new Comparator<Announcement>() {
        @Override
        public int compare(Announcement lhs, Announcement rhs) {
            return (int) (lhs.getRating() - rhs.getRating());
        }
    };
}

