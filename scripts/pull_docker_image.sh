#!/bin/bash

# Docker 이미지 이름
IMAGE_NAME="syber911911/share_table_be:latest"

# Docker 이미지 pull
echo "Docker 이미지를 pull 하고 있습니다: $IMAGE_NAME"
sudo docker pull $IMAGE_NAME

# 결과 확인
if [ $? -eq 0 ]; then
    echo "Docker 이미지가 성공적으로 pull 되었습니다."
    exit 0
else
    echo "Docker 이미지 pull 중 오류가 발생했습니다."
    exit 1
fi
