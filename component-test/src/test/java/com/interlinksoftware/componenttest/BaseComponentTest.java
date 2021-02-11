package com.interlinksoftware.componenttest;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.lang.String.format;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.interlinksoftware.Application;
import com.interlinksoftware.componenttest.util.Constants;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
@ComponentScan(basePackages = {"com.interlinksoftware.*"})
@Slf4j
@AutoConfigureWireMock(port = 8888)
@DirtiesContext
public abstract class BaseComponentTest {

  @ClassRule
  public static final WireMockRule WIREMOCK_SERVER =
      new WireMockRule(wireMockConfig().port(Constants.WIREMOCK_PORT));
  public static TestRestTemplate restTemplate;
  static ExecutorService executor;
  @LocalServerPort
  int randomServerPort;
  public final Function<String, String> serverUrl = (String path) -> format("http://localhost:%s%s",
      randomServerPort, path);

  @BeforeClass
  public static void beforeClass() {
    restTemplate = new TestRestTemplate();
    executor = Executors.newSingleThreadExecutor();
  }

  @AfterClass
  public static void afterClass() {
    executor.shutdownNow();
    WIREMOCK_SERVER.resetAll();
  }
}
