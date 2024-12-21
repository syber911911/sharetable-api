# Docker 설치 및 업데이트
if command -v docker &> /dev/null; then
    echo "Docker 가 이미 설치되어 있습니다. 업데이트 중입니다..."
    sudo apt-get update
    sudo apt-get install --only-upgrade -y docker.io
else
    echo "Docker 설치 중입니다..."
    sudo apt-get update
    sudo apt-get install -y docker.io
fi

# Docker Compose 설치 및 업데이트
echo "Docker Compose 설치 중입니다..."
sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

echo "설치가 완료되었습니다. 현재 버전:"
docker --version
docker-compose --version
