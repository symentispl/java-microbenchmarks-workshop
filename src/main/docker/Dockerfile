FROM eclipse-temurin:17
ADD https://github.com/async-profiler/async-profiler/releases/download/v2.10/async-profiler-2.10-linux-x64.tar.gz /opt/async-profiler.tar.gz
ADD https://chriswhocodes.com/hsdis/hsdis-amd64.so /opt/java/openjdk/lib/server/
RUN apt update && apt install --yes linux-tools-generic && \
    mkdir /opt/async-profiler && tar xzvf /opt/async-profiler.tar.gz --strip 1 -C /opt/async-profiler
ENV JMH_PERF=/usr/lib/linux-tools/5.4.0-150-generic/perf LD_LIBRARY_PATH=/opt/async-profiler/lib/