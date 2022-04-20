package tech.kerok.portfolio.rps.service;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class HashServiceTest {

    private Faker faker;

    @BeforeEach
    public void beforeTest() {
        faker = new Faker();
    }

    @Test
    void createMD5Hash() throws NoSuchAlgorithmException {

        String event = faker.esports().event();
        System.out.println(event);
        String md5Hash = HashService.createMD5Hash(event);
        System.out.println(md5Hash);

    }
    @Test
    void sameInputShouldEqualOutput() throws NoSuchAlgorithmException {

        String player = faker.esports().player();
        System.out.println(player);
        String md5Hash1 = HashService.createMD5Hash(player);
        String md5Hash2 = HashService.createMD5Hash(player);
        System.out.println(md5Hash1);
        System.out.println(md5Hash2);

        assertEquals(md5Hash1, md5Hash2);
        assertTrue(HashService.verify(md5Hash1, md5Hash2));
    }

    @Test
    void differentInputShouldNotEqualOutput() throws NoSuchAlgorithmException {

        String md5Hash1 = HashService.createMD5Hash(faker.esports().player());
        String md5Hash2 = HashService.createMD5Hash(faker.harryPotter().quote());

        System.out.println(md5Hash1);
        System.out.println(md5Hash2);

        assertNotEquals(md5Hash1, md5Hash2);
        assertFalse(HashService.verify(md5Hash1, md5Hash2));
    }

}