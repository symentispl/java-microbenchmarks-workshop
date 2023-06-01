# run microbenchmarks locally

* install async profiler, https://github.com/async-profiler/async-profiler/releases/download/v2.10/async-profiler-2.10-linux-x64.tar.gz
* install hsdis (https://chriswhocodes.com/hsdis/) into your $JAVA_HOME/lib/server/
* in Linux configure your system with `sysctl kernel.perf_event_paranoid=1` and `sysctl kernel.kptr_restrict=0`
* install JDK 20
* run `./mvnw clean package`

# run microbenchmarks with docker

Build docker image:

    cd src/main/docker
    docker build . -t microbenchmarks
And run benchmarks with:

    ./mvnw clean package
    docker run --privileged --rm -v $PWD/target:/work microbenchmarks java -jar /work/benchmarks.jar -prof "async:output=flamegraph;dir=/work"
