package ch.uzh.ifi.seal.soprafs17.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.test.context.web.WebAppConfiguration;

import ch.uzh.ifi.seal.soprafs17.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class ExampleServiceTest {

	@Autowired
	ExampleService exampleService;
	
	@Test
	public void testDoLogic() {
		assertThat(exampleService.doLogic("a", "b"), is("ab"));
	}
}
