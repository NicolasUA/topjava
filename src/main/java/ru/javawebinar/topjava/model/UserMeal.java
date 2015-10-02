package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.NotEmpty;
import ru.javawebinar.topjava.repository.jpa.LocalDateTimePersistenceConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
@NamedQueries({
        @NamedQuery(name = UserMeal.GET, query = "SELECT m FROM UserMeal m WHERE m.id=:id and m.user=:user"),
        @NamedQuery(name = UserMeal.DELETE, query = "DELETE FROM UserMeal m WHERE m.id=:id and m.user=:user"),
        @NamedQuery(name = UserMeal.ALL_SORTED, query = "SELECT m FROM UserMeal m WHERE m.user=:user ORDER BY m.dateTime DESC"),
        @NamedQuery(name = UserMeal.GET_BETWEEN, query = "SELECT m FROM UserMeal m WHERE m.user=:user AND m.dateTime BETWEEN :start AND :end ORDER BY m.dateTime DESC"),
        @NamedQuery(name = UserMeal.MERGE, query = "UPDATE UserMeal m SET m.description=:description, m.calories=:calories, m.dateTime=:date_time WHERE m.id=:id AND m.user=:user"),
})
@Entity
@Table(name = "meals")
public class UserMeal extends BaseEntity {

    public static final String GET = "Meal.get";
    public static final String DELETE = "Meal.delete";
    public static final String ALL_SORTED = "Meal.getAllSorted";
    public static final String GET_BETWEEN = "Meal.getBetween";
    public static final String MERGE = "Meal.merge";

    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()")
    @NotEmpty
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    protected LocalDateTime dateTime;

    @Column(name = "description")
    protected String description;

    @Column(name = "calories", nullable = false)
    @NotEmpty
    protected int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public UserMeal() {
    }

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public Integer getId() {
        return id;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}