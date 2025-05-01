package edu.automationexercise;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class AccountCreationAPITest {

    private static final String BASE_URL = "https://automationexercise.com/api/createAccount";
    private static final int NUM_THREADS = 10;
    private static final int RAMP_UP_PERIOD = 5;
    private static final int LOOP_COUNT = 5;

    private static int threads = NUM_THREADS;
    private static int rampUp = RAMP_UP_PERIOD;
    private static int loops = LOOP_COUNT;

    public static void main(String[] args) {
        try {
            parseArgs(args);
            setupJMeterEnvironment();

            TestPlan testPlan = createTestPlan();
            ThreadGroup threadGroup = createThreadGroup();
            HTTPSamplerProxy httpSampler = createHttpSampler();
            HeaderManager headerManager = createHeaderManager();

            HashTree testPlanTree = new HashTree();
            HashTree threadGroupTree = testPlanTree.add(testPlan, threadGroup);
            HashTree httpSamplerTree = threadGroupTree.add(httpSampler);
            httpSamplerTree.add(headerManager);

            Summariser summariser = new Summariser("summary");
            ResultCollector resultCollector = new ResultCollector(summariser);
            String logFileName = "jmeter-results-" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date()) + ".jtl";
            resultCollector.setFilename(logFileName);
            testPlanTree.add(resultCollector);

            SaveService.saveTree(testPlanTree, new FileOutputStream("AccountCreationAPITest.jmx"));

            StandardJMeterEngine jmeterEngine = new StandardJMeterEngine();
            jmeterEngine.configure(testPlanTree);
            jmeterEngine.run();

            System.out.println("Test completed! Results saved to: " + logFileName);
            System.out.println("- Number of threads (users): " + threads);
            System.out.println("- Ramp-up period: " + rampUp + " seconds");
            System.out.println("- Loop count: " + loops);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseArgs(String[] args) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-t":
                        threads = Integer.parseInt(args[++i]);
                        break;
                    case "-r":
                        rampUp = Integer.parseInt(args[++i]);
                        break;
                    case "-l":
                        loops = Integer.parseInt(args[++i]);
                        break;
                    case "-h":
                    case "--help":
                        printHelp();
                        System.exit(0);
                }
            }
        }
    }

    private static void printHelp() {
        System.out.println("Usage: java -jar account-creation-api-test.jar [options]");
        System.out.println("Options:");
        System.out.println("  -t <number>    Number of threads (users)");
        System.out.println("  -r <number>    Ramp-up period in seconds");
        System.out.println("  -l <number>    Loop count per user");
        System.out.println("  -h, --help     Show help message");
    }

    private static void setupJMeterEnvironment() {
        String jmeterHome = System.getProperty("jmeter.home");
        if (jmeterHome == null || jmeterHome.isEmpty()) {
            jmeterHome = System.getenv("JMETER_HOME");
        }

        if (jmeterHome == null || jmeterHome.isEmpty()) {
            throw new RuntimeException("JMeter home not set. Please set 'jmeter.home' system property or JMETER_HOME environment variable.");
        }

        String jmeterPropertiesFile = jmeterHome + (jmeterHome.endsWith("/") ? "bin/jmeter.properties" : "/bin/jmeter.properties");

        JMeterUtils.setJMeterHome(jmeterHome);
        JMeterUtils.loadJMeterProperties(jmeterPropertiesFile);
        JMeterUtils.initLocale();

        System.out.println("JMeter initialized at: " + jmeterHome);
    }

    private static TestPlan createTestPlan() {
        TestPlan testPlan = new TestPlan("Account Creation API Test Plan");
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, "org.apache.jmeter.control.gui.TestPlanGui");
        testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());
        return testPlan;
    }

    private static ThreadGroup createThreadGroup() {
        LoopController loopController = new LoopController();
        loopController.setLoops(loops);
        loopController.setFirst(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, "org.apache.jmeter.control.gui.LoopControlPanel");
        loopController.initialize();

        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setName("Account Creation Thread Group");
        threadGroup.setNumThreads(threads);
        threadGroup.setRampUp(rampUp);
        threadGroup.setSamplerController(loopController);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, "org.apache.jmeter.threads.gui.ThreadGroupGui");

        return threadGroup;
    }

    private static HTTPSamplerProxy createHttpSampler() {
        HTTPSamplerProxy httpSampler = new HTTPSamplerProxy();
        httpSampler.setDomain("automationexercise.com");
        httpSampler.setPath("/api/createAccount");
        httpSampler.setMethod("POST");
        httpSampler.setProtocol("https");
        httpSampler.setPort(443);
        httpSampler.setFollowRedirects(true);
        httpSampler.setUseKeepAlive(true);

        String randomSuffix = System.currentTimeMillis() + String.valueOf(new Random().nextInt(9999));
        addParameter(httpSampler, "name", "TestUser" + randomSuffix);
        addParameter(httpSampler, "email", "test" + randomSuffix + "@example.com");
        addParameter(httpSampler, "password", "Password123");
        addParameter(httpSampler, "title", "Mr");
        addParameter(httpSampler, "birth_date", "10");
        addParameter(httpSampler, "birth_month", "5");
        addParameter(httpSampler, "birth_year", "1990");
        addParameter(httpSampler, "firstname", "Test");
        addParameter(httpSampler, "lastname", "User" + randomSuffix);
        addParameter(httpSampler, "company", "Test Company");
        addParameter(httpSampler, "address1", "123 Test Street");
        addParameter(httpSampler, "address2", "Apt 456");
        addParameter(httpSampler, "country", "United States");
        addParameter(httpSampler, "zipcode", "12345");
        addParameter(httpSampler, "state", "Test State");
        addParameter(httpSampler, "city", "Test City");
        addParameter(httpSampler, "mobile_number", "1234567890");

        httpSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSampler.setProperty(TestElement.GUI_CLASS, "org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui");
        return httpSampler;
    }

    private static void addParameter(HTTPSamplerProxy httpSampler, String name, String value) {
        HTTPArgument argument = new HTTPArgument(name, value);
        argument.setAlwaysEncoded(false);
        argument.setUseEquals(true);
        httpSampler.getArguments().addArgument(argument);
    }

    private static HeaderManager createHeaderManager() {
        HeaderManager headerManager = new HeaderManager();
        headerManager.setName("Header Manager");
        headerManager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
        headerManager.setProperty(TestElement.GUI_CLASS, "org.apache.jmeter.protocol.http.control.gui.HeaderPanel");
        headerManager.add(new org.apache.jmeter.protocol.http.control.Header("Content-Type", "application/x-www-form-urlencoded"));
        return headerManager;
    }

}
