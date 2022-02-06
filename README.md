## spring webflux + vusjs 3 setup

### backend develop
- For Database setup refer to docker/init.sql
- Download and unzip jhipster-uaa from https://github.com/appkr/msa-starter/raw/master/jhipster-uaa.zip

```bash
# build jhipster-uaa
~/jhipster-uaa $ ./gradlew clean jibDockerBuild

# start MySQL & jhipster-uaa
~/webflux-vue3-scaffolding $ ./gradlew up

# start application (backend + frontend)
~/webflux-vue3-scaffolding $ ./gradlew clean bootRun

# test
open http://localhost:8080

RESPONSE=$(curl -s -X POST 'http://localhost:9999/oauth/token' -H 'Content-Type: application/x-www-form-urlencoded' -H 'Accept: application/json' -H 'Authorization: Basic d2ViX2FwcDpjaGFuZ2VpdA==' -d 'grant_type=password&username=user&password=user&scope=openid')
ACCESS_TOKEN=$(echo $RESPONSE | jq .access_token | xargs)

curl -s -H "Authorization: bearer $ACCESS_TOKEN" GET 'http://localhost:8080/api/examples'
curl -s -H "Authorization: bearer $ACCESS_TOKEN" GET 'http://localhost:8080/api/users' 
```

### frontend develop

```bash
cd frontend && npm install && npm run serve
```

### docker deploy (frontend + backend)

```bash
./gradlew clean jibDockerBuild

docker image ls
```
