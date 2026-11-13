#!/usr/bin/env bash
set -euo pipefail
BASE_URL="${1:-http://localhost:8080}"
echo "Read-only requests to missing product -1, once per second for 4 minutes."
echo "Run only against your local homework environment."
# Warm up the metric series and allow two 15-second scrapes before the test.
code=$(curl --max-time 10 -sS -o /dev/null -w '%{http_code}' \
  -H 'X-Requested-With: XMLHttpRequest' "$BASE_URL/api/products/-1")
if [ "$code" != "500" ]; then
  echo "Expected HTTP 500 in the reviewed revision, got $code. Stop: inspect route/handler." >&2
  exit 1
fi
sleep 35
for ((i=1; i<=240; i++)); do
  code=$(curl --max-time 10 -sS -o /dev/null -w '%{http_code}' \
    -H 'X-Requested-With: XMLHttpRequest' "$BASE_URL/api/products/-1")
  if [ "$code" != "500" ]; then
    echo "Unexpected HTTP $code at request $i. Test stopped." >&2
    exit 1
  fi
  if (( i % 30 == 0 )); then echo "$i requests: HTTP $code"; fi
  sleep 1
done
echo "Traffic stopped. Leave the stack running to receive RESOLVED after the 5-minute window clears."
