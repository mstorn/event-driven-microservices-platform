#!/bin/bash

export JAVA_TOOL_OPTIONS="-javaagent:$(pwd)/infrastructure/observability/opentelemetry-javaagent.jar \
-Dotel.exporter.otlp.endpoint=http://localhost:4317"

echo "OpenTelemetry enabled"

mvn spring-boot:run