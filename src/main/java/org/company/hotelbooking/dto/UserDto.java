package org.company.hotelbooking.dto;

import lombok.*;

/**
 * Data transfer object class for class User.
 * @author Volodymyr
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;
    private String surname;

}
