package ru

import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import ru.model.Employee
import spock.lang.Specification

/**
 * Created by Dmitry Tkachenko on 2/18/18
 */
@Log4j
@ContextConfiguration
@SpringBootTest(classes = [Application])
class H2Test extends Specification {

    @Autowired
    private JdbcTemplate h2jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate h2NamedJdbcTemplate

    def 'test 1'() {
        given: 'a list of employees'
        def employees = [
                new Employee(name: 'Max'),
                new Employee(name: 'Den'),
                new Employee(name: 'John')
        ]
        def insertQuery = 'INSERT INTO employee (name) VALUES(:name)'
        def selectAllQuery = 'SELECT * FROM employee'
        when:
        employees.each {
            println it.name
        }
        h2jdbcTemplate.execute('DROP TABLE employee IF EXISTS')
        h2jdbcTemplate.execute("CREATE TABLE employee(id SERIAL, name VARCHAR(255))")
        employees.each {
            h2NamedJdbcTemplate.update(insertQuery, new MapSqlParameterSource().addValue('name', it.name))
        }
//        h2jdbcTemplate.q(selectAllQuery)
//        h2jdbcTemplate.batchUpdate("INSERT INTO employee(name) VALUES (?)", employees)
        then:
        1 == 1

    }

    @Transactional
    def "delete rows from table"() {
        setup:
        jdbc.update("delete from data")
    }

    @Transactional
    def "table is back to initial state"() {
        expect:
        jdbc.queryForList("select * from data order by id") == [[ID: 1, NAME: "fred"], [ID: 2, NAME: "tom"]]
    }
}
