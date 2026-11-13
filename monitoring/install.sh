#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
VM_URL="${1:?Usage: bash monitoring/install.sh http://YOUR_VM_SERVICE.monitoring.svc.cluster.local:8428}"
case "$VM_URL" in
  http://*|https://*) ;;
  *) echo "VM_URL must start with http:// or https://" >&2; exit 1 ;;
esac
kubectl get namespace monitoring >/dev/null 2>&1 || kubectl create namespace monitoring
kubectl -n monitoring create configmap shop-monitoring-settings \
  --from-literal=VM_URL="$VM_URL" --dry-run=client -o yaml | kubectl apply -f -
kubectl -n monitoring create configmap shop-alert-rules \
  --from-file=alerts.yml=alerts.yml --dry-run=client -o yaml | kubectl apply -f -
kubectl -n monitoring create configmap shop-alertmanager-config \
  --from-file=alertmanager.yml=alertmanager.yml --dry-run=client -o yaml | kubectl apply -f -
kubectl -n monitoring create configmap shop-alert-inbox-code \
  --from-file=server.py=inbox/server.py --dry-run=client -o yaml | kubectl apply -f -
kubectl -n monitoring apply -f kubernetes.yaml
for deployment in shop-alert-inbox shop-alertmanager shop-vmalert; do
  kubectl -n monitoring rollout restart "deployment/$deployment"
  kubectl -n monitoring rollout status "deployment/$deployment" --timeout=180s
done
