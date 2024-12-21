# 성공 시 Discord 에 웹훅 전송
WEBHOOK_URL="https://discord.com/api/webhooks/1296073811682136074/2XzCfdXNwc66Oe7HvJhcnV4tRk3HfDMZi39ZJ_NKA5ZJQmZBGPoRxFIxA2O37sj0Oe4y"

curl -H "Content-Type: application/json" \
     -X POST \
     -d '{"content": "배포 성공! 트래픽이 성공적으로 Green 버전으로 전환되었습니다."}' \
     $WEBHOOK_URL
