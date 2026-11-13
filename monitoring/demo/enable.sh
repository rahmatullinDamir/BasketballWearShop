#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
kubectl -n monitoring create configmap shop-alert-rules \
  --from-file=alerts.yml=alerts.yml \
  --from-file=demo.yml=demo/runtime-demo.yml \
  --dry-run=client -o yaml | kubectl apply -f -
kubectl -n monitoring rollout restart deployment/shop-vmalert
kubectl -n monitoring rollout status deployment/shop-vmalert --timeout=180s
