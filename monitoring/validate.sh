#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
docker run --rm --entrypoint /bin/promtool \
  -v "$PWD:/work:ro" -w /work prom/prometheus:v3.2.1 check rules alerts.yml demo/runtime-demo.yml
docker run --rm --entrypoint /bin/promtool \
  -v "$PWD:/work:ro" -w /work/tests prom/prometheus:v3.2.1 test rules rules.test.yml
docker run --rm --entrypoint /bin/amtool \
  -v "$PWD:/work:ro" prom/alertmanager:v0.28.1 check-config /work/alertmanager.yml
docker run --rm -v "$PWD:/work:ro" victoriametrics/vmalert:v1.110.0 \
  -rule=/work/alerts.yml -dryRun -rule.validateExpressions=true
