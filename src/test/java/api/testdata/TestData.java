package api.testdata;

import api.models.User;

public class TestData {
    public static final User DEFAULT_USER = User.builder()
            .username("Moiva")
            .firstName("Moiva")
            .lastName("Glagoleva")
            .email("glagol@mail.ru")
            .password("123123")
            .phone("8935546786")
            .userStatus(1)
            .build();

    public static final User INVALID_USER = User.builder()
            .username("Bonia")
            .build();
}
