package com.cms.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "mess_menus")
public class MessMenu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "hostel_id", nullable = false) private Hostel hostel;
    @Column(name = "day_of_week", nullable = false, length = 20) private String dayOfWeek;
    @Column(name = "meal_type", nullable = false, length = 30) private String mealType;
    @Column(nullable = false, columnDefinition = "TEXT") private String items;
    @Column private LocalDate date;

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Hostel getHostel() { return hostel; } public void setHostel(Hostel v) { this.hostel = v; }
    public String getDayOfWeek() { return dayOfWeek; } public void setDayOfWeek(String v) { this.dayOfWeek = v; }
    public String getMealType() { return mealType; } public void setMealType(String v) { this.mealType = v; }
    public String getItems() { return items; } public void setItems(String v) { this.items = v; }
    public LocalDate getDate() { return date; } public void setDate(LocalDate v) { this.date = v; }
}
