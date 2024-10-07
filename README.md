# run microbenchmarks locally

* install async profiler, https://github.com/async-profiler/async-profiler/releases/tag/v3.0, use binary for your target CPU architecture
* install hsdis (https://chriswhocodes.com/hsdis/) into your $JAVA_HOME/lib/server/, use binary for your target CPU architecture
* LINUX ONLY: configure your system with `sysctl kernel.perf_event_paranoid=1` and `sysctl kernel.kptr_restrict=0`
* install JDK 21
* run `./mvnw clean package`

# run microbenchmarks with docker

Build docker image:

    cd src/main/docker
    docker build . -t microbenchmarks

And run benchmarks with:

    ./mvnw clean package
    docker run --privileged --rm -v $PWD/target:/work microbenchmarks java -jar /work/benchmarks.jar -prof "async:output=flamegraph;dir=/work"

Without docker, you can run benchmarks directly on your machine with:

    LD_LIBRARY_PATH=/home/jarek/tools/async-profiler/lib/ java -jar target/benchmarks.jar -prof "async:output=flamegraph"
