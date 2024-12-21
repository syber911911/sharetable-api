WEBHOOK_URL="https://discord.com/api/webhooks/1296073811682136074/2XzCfdXNwc66Oe7HvJhcnV4tRk3HfDMZi39ZJ_NKA5ZJQmZBGPoRxFIxA2O37sj0Oe4y"

curl -H "Content-Type: application/json" \
     -X POST \
     -d '{"content": "Code Deploy 를 시작합니다."}' \
     $WEBHOOK_URL


# 모든 실행 중인 컨테이너 중지
echo "모든 실행 중인 컨테이너를 중지합니다..."
docker stop $(docker ps -q)

# 모든 컨테이너 삭제
echo "모든 컨테이너를 삭제합니다..."
docker rm $(docker ps -a -q)

# 모든 이미지 삭제
echo "모든 이미지를 삭제합니다..."
docker rmi $(docker images -q)

echo "모든 컨테이너와 이미지가 삭제되었습니다."
