package com.test.graphql.benchmarking;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.data.repository.query.Param;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 1)
@Measurement(iterations = 1)
@State(Scope.Benchmark)
@Timeout(time = 1)
public class MeasureE2E {

    private List<String> DATA_FOR_TESTING = new ArrayList<>();

    public static void main(String[] argv) throws Exception {

        Options opt = new OptionsBuilder()
                .include(MeasureE2E.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {

    }

    @Benchmark
    @Measurement(iterations = 1)
    public void sendGraphQlPost(Blackhole bh) throws Exception {
        URL url = new URL("http://localhost:8080/graphql");
        String postData = "{\"query\":\"query{\\n  vehicles(count: 10){    id,type,modelCode,brandName,launchDate } }\"}";

        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", Integer.toString(postData.length()));

        try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
            dos.writeBytes(postData);
        }

        try (BufferedReader bf = new BufferedReader(new InputStreamReader(
                conn.getInputStream())))
        {
            String line;
            while ((line = bf.readLine()) != null) {
                System.out.println(line);
                bh.consume(line);
            }
        }
    }

    @Benchmark
    @Measurement(iterations = 1)
    public void sendRestPost(Blackhole bh) throws Exception {
        URL url = new URL("http://localhost:8080/vehicles");
        String postData = "{\"query\":\"query{\\n  vehicles(count: 10){    id,    type,    modelCode, brandName, launchDate}\"}";

        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", Integer.toString(postData.length()));

        try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
            dos.writeBytes(postData);
        }

        try (BufferedReader bf = new BufferedReader(new InputStreamReader(
                conn.getInputStream())))
        {
            String line;
            while ((line = bf.readLine()) != null) {
                System.out.println(line);
                bh.consume(line);
            }
        }
    }

}
