"""A local homework receiver. Displays only webhooks actually received over HTTP."""
import html
import json
import os
import threading
from collections import deque
from datetime import datetime, timezone
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
from pathlib import Path

DATA = Path(os.getenv("DATA_PATH", "/data/notifications.jsonl"))
LOCK = threading.Lock()


class Handler(BaseHTTPRequestHandler):
    def reply(self, status, body, content_type="text/plain; charset=utf-8"):
        data = body.encode("utf-8")
        self.send_response(status)
        self.send_header("Content-Type", content_type)
        self.send_header("Content-Length", str(len(data)))
        self.send_header("Cache-Control", "no-store")
        self.end_headers()
        self.wfile.write(data)

    def do_POST(self):
        if self.path != "/alerts":
            return self.reply(404, "Not found")
        try:
            size = int(self.headers.get("Content-Length", "0"))
            if not 0 < size <= 1048576:
                return self.reply(413, "Invalid payload length")
            payload = json.loads(self.rfile.read(size))
            if not isinstance(payload, dict) or not isinstance(payload.get("alerts"), list):
                return self.reply(400, "Expected Alertmanager webhook object")
            event = {"received_at": datetime.now(timezone.utc).isoformat(), "payload": payload}
            DATA.parent.mkdir(parents=True, exist_ok=True)
            with LOCK:
                with DATA.open("a", encoding="utf-8") as stream:
                    stream.write(json.dumps(event, ensure_ascii=False) + "\n")
            self.reply(200, "accepted")
        except (ValueError, UnicodeDecodeError):
            self.reply(400, "Invalid JSON")
        except OSError:
            self.reply(500, "Cannot persist notification")

    def do_GET(self):
        if self.path not in ("/", "/api/notifications"):
            return self.reply(404, "Not found")
        with LOCK:
            if DATA.exists():
                with DATA.open(encoding="utf-8") as stream:
                    events = [json.loads(line) for line in deque(stream, maxlen=100)]
            else:
                events = []
        if self.path == "/api/notifications":
            return self.reply(200, json.dumps(events, ensure_ascii=False), "application/json")
        esc = lambda value: html.escape(str(value), quote=True)
        cards = []
        for event in reversed(events):
            for alert in event["payload"]["alerts"]:
                labels = alert.get("labels", {})
                annotations = alert.get("annotations", {})
                status = alert.get("status", event["payload"].get("status", "unknown"))
                color = "#b42318" if status == "firing" else "#067647"
                label_rows = "".join(f"<tr><th>{esc(k)}</th><td>{esc(v)}</td></tr>" for k, v in sorted(labels.items()))
                cards.append(f"""<article><b style="color:{color}">{esc(status.upper())}</b>
                  <h2>{esc(labels.get('alertname', 'Alert'))}</h2>
                  <p>{esc(annotations.get('summary', ''))}</p>
                  <p>{esc(annotations.get('description', ''))}</p>
                  <p><b>Действие:</b> {esc(annotations.get('action', ''))}</p>
                  <p>Получено (UTC): {esc(event['received_at'])}<br>
                  Начало (UTC): {esc(alert.get('startsAt', ''))}</p>
                  <table>{label_rows}</table>
                  <details><summary>Полученный JSON</summary><pre>{esc(json.dumps(event, ensure_ascii=False, indent=2))}</pre></details>
                </article>""")
        body = """<!doctype html><html lang="ru"><meta charset="utf-8">
        <meta http-equiv="refresh" content="10"><title>BasketballWearShop — уведомления</title>
        <style>body{font:16px system-ui;background:#f2f4f7;color:#101828;max-width:1000px;margin:40px auto;padding:0 24px}
        article{background:white;padding:24px;border:1px solid #d0d5dd;border-radius:12px;margin:20px 0}
        h1{font-size:28px}h2{font-size:23px}td,th{padding:5px 15px 5px 0;text-align:left;vertical-align:top}
        pre{white-space:pre-wrap;overflow-wrap:anywhere;font-size:12px}p{line-height:1.5}</style>
        <h1>BasketballWearShop · Уведомления</h1>
        <p>Входящие HTTP webhook-сообщения. Обновление каждые 10 секунд.</p>"""
        body += "".join(cards) if cards else "<article>Уведомления пока не поступали.</article>"
        self.reply(200, body + "</html>", "text/html; charset=utf-8")


if __name__ == "__main__":
    ThreadingHTTPServer(("0.0.0.0", int(os.getenv("PORT", "8080"))), Handler).serve_forever()
