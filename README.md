# TEAM 1 KMS
![image](https://github.com/final-kms/main/assets/44054359/ee167743-9c9e-4470-9d94-56baa296ac30)

### FRONTEND
- 참조 : https://github.com/final-kms/main/pull/92
### BACKEND
- 참조 : https://github.com/final-kms/main/pull/91

# BUILD

## FRONTEND
  
``` 
$cd frontend
$npm run install
$npm run build
```

## BACKEND

```
$cd backend
$mvn -Dmaven.test.skip=true install
```

# TEST

## FRONTEND
  
``` 
$cd frontend
$npm install
$npm run dev
```

## BACKEND

```
$cd backend
$java -jar {APP_NAME}
```

### SWAGGER

- 참조 : swagger/index.html

# DEPLOY

## FRONTEND

### nginx

- 참조 : https://github.com/final-kms/main/issues/93
- 빌드 이후,
- (ubuntu)  cp -r dist/* /var/www/html/
- (window) nginx-{VERSION}/html/ 내에 복사

## BACKEND

### nginx

- 참조 : https://github.com/final-kms/main/issues/94
- 빌드 이후,
- deploy/ 내에 파일 복사
```
$docker compose up
```
