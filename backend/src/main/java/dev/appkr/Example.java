package dev.appkr;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("examples")
@Getter
@Setter
@ToString
public class Example {
  @Id
  private Long id;
  private String name;

  public Example(String name) {
    this.name = name;
  }
}
