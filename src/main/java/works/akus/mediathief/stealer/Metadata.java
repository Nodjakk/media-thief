package works.akus.mediathief.stealer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class Metadata {

    final int duration;
    final String name;
    final String author;
    final String image;

}
