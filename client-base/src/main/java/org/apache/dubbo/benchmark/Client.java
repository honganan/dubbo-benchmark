package org.apache.dubbo.benchmark;

import com.youzan.platform.demo.api.DemoService1;
import com.youzan.platform.demo.api.dto.ResultDTO;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.dubbo.benchmark.rpc.AbstractClient;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@State(Scope.Benchmark)
public class Client extends AbstractClient {
    private static final int CONCURRENCY = 32;

    private final ClassPathXmlApplicationContext context;
//    private final UserService userService;
    private final DemoService1 demoService1;

    public Client() {
        context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
//        userService = (UserService) context.getBean("userService");
        demoService1 = (DemoService1) context.getBean("demoService1");
    }

//    @Override
//    protected UserService getUserService() {
//        return userService;
//    }

    protected DemoService1 getDemoService1() {
        return demoService1;
    }

    @TearDown
    public void close() throws IOException {
        context.close();
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Override
    public ResultDTO findAuthorTopics() throws Exception {
        return super.findAuthorTopics();
    }

    /*@Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Override
    public boolean existUser() throws Exception {
        return super.existUser();
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Override
    public boolean createUser() throws Exception {
        return super.createUser();
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Override
    public User getUser() throws Exception {
        return super.getUser();
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Override
    public Page<User> listUser() throws Exception {
        return super.listUser();
    }*/

    public static void main(String[] args) throws Exception {
        System.out.println(args);
        org.apache.commons.cli.Options options = new org.apache.commons.cli.Options();

        options.addOption(Option.builder().longOpt("warmupIterations").hasArg().build());
        options.addOption(Option.builder().longOpt("warmupTime").hasArg().build());
        options.addOption(Option.builder().longOpt("measurementIterations").hasArg().build());
        options.addOption(Option.builder().longOpt("measurementTime").hasArg().build());

        CommandLineParser parser = new DefaultParser();

        CommandLine line = parser.parse(options, args);

        int warmupIterations = Integer.valueOf(line.getOptionValue("warmupIterations", "3"));
        int warmupTime = Integer.valueOf(line.getOptionValue("warmupTime", "10"));
        int measurementIterations = Integer.valueOf(line.getOptionValue("measurementIterations", "3"));
        int measurementTime = Integer.valueOf(line.getOptionValue("measurementTime", "10"));

        Options opt;
        ChainedOptionsBuilder optBuilder = new OptionsBuilder()
                .include(Client.class.getSimpleName())
                .warmupIterations(warmupIterations)
                .warmupTime(TimeValue.seconds(warmupTime))
                .measurementIterations(measurementIterations)
                .measurementTime(TimeValue.seconds(measurementTime))
                .threads(CONCURRENCY)
                .forks(1);

        opt = doOptions(optBuilder).build();

        new Runner(opt).run();

    }

    private static ChainedOptionsBuilder doOptions(ChainedOptionsBuilder optBuilder) {
        String output = System.getProperty("benchmark.output");
        if (output != null && !output.trim().isEmpty()) {
            optBuilder.output(output);
        }
        return optBuilder;
    }
}
