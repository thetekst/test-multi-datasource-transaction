package ru.driver

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @author Dmitry Tkachenko
 * 19.02.18
 */
@SpringBootTest
class DbDriverTest extends Specification {

    def 'postgres driver test'() {
        expect:
        Class.forName("org.postgresql.Driver");
    }

    def 'h2 driver test'() {
        expect:
        Class.forName("org.h2.Driver");
    }
}
