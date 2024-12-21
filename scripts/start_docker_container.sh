# Docker 이미지 이름
IMAGE_NAME="syber911911/share_table_be:latest"

# 컨테이너 실행
echo "컨테이너를 실행합니다: $IMAGE_NAME"
sudo docker run -d \
    --name sharetable-server \
    -p 80:8080 \
    $IMAGE_NAME

# 실행 결과 확인
if [ $? -eq 0 ]; then
    echo "컨테이너가 성공적으로 실행되었습니다."
    exit 0  # 성공 시 0 반환
else
    echo "컨테이너 실행 중 오류가 발생했습니다."
    exit 1  # 실패 시 1 반환
fi
