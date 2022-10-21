#Book store

# Infra setup
## Local env 
### Start redis in local
docker run -d --name redis -p 6379:6379 -p 8001:8001 -v D:\Work-benches\redis\data:/data redis/redis-stack-server:latest

# User service
## RSA key generation
keytool -genkeypair -alias rsa-4096-20221017 -keyalg rsa -keysize 4096 -keystore keystore.p12 -storetype PKCS12

openssl genrsa -out private.pem 4096
openssl pkcs12 -in keystore.p12 -nocerts -nodes -out rsa-20221017.key
openssl x509 -in rsa-20221017.cer -out rsa-20221017.pem -outform PEM

# Reference articles
1.DNS utils - https://www.cnblogs.com/varden/p/15098696.html