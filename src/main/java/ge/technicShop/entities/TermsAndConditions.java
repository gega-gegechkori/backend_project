package ge.technicShop.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "terms_and_conditions")
public class TermsAndConditions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    public TermsAndConditions() {}

    public TermsAndConditions(String content) { this.content = content; }

    public Long getId() { return id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}