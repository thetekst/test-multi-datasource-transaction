import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.transaction.annotation.Transactional
import ru.config.PostgreSqlConfig
import ru.config.PrimaryDbConfig
import ru.model.Employee
import spock.lang.Specification

/**
 * Created by Dmitry Tkachenko on 2/18/18
 */
@Log4j
@SpringBootTest(classes = [PrimaryDbConfig, PostgreSqlConfig])
class DbTest extends Specification {

    @Autowired
    @Qualifier('postgreSqlNamedParameterJdbcTemplate')
    NamedParameterJdbcTemplate jdbc

    def 'test 1'() {
        given: 'a list of employees'
        def employees = [
                new Employee(name: 'Max'),
                new Employee(name: 'Den'),
                new Employee(name: 'John')
        ]
        when:
        employees.each {
            println it.name
        }
        jdbc.execute('DROP TABLE employee IF EXISTS')
        jdbc.execute("CREATE TABLE employee(id SERIAL, name VARCHAR(255))");
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
