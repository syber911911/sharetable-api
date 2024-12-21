WEBHOOK_URL="https://discord.com/api/webhooks/1296073811682136074/2XzCfdXNwc66Oe7HvJhcnV4tRk3HfDMZi39ZJ_NKA5ZJQmZBGPoRxFIxA2O37sj0Oe4y"

# CodeDeploy가 설정하는 배포 상태 환경 변수를 확인
if [[ "$DEPLOYMENT_STATUS" == "Succeeded" ]]; then
  # 배포가 성공적인 경우 (Blue 그룹의 트래픽을 차단할 때)
  echo "배포 성공: Blue 그룹의 트래픽을 차단합니다."
  curl -H "Content-Type: application/json" \
       -X POST \
       -d '{"content": "배포 성공: Blue 그룹의 트래픽을 차단합니다."}' \
       $WEBHOOK_URL

elif [[ "$DEPLOYMENT_STATUS" == "Failed" ]]; then
  # 배포가 실패한 경우 (Green 그룹의 트래픽을 차단할 때)
  echo "배포 실패: Green 그룹의 트래픽을 차단합니다."
  curl -H "Content-Type: application/json" \
       -X POST \
       -d '{"content": "배포 실패: Green 그룹의 트래픽을 차단합니다."}' \
       $WEBHOOK_URL
fi
