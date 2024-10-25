# Application in prod!!!!!!!!

#### Request DNS for tests
    https://prisma-vi-api.onrender.com/
#### Request DNS for real
    https://prisma-vi-api-production.up.railway.app
# How to run local

[Download postgres docker compose](https://github.com/SamuelSoaresSilva/postgres-docker/blob/compose/docker-compose.yaml) | [Download docker desktop](https://www.docker.com/products/docker-desktop/)

## After start Docker Desktop
>Note: In the folder where you downloaded [docker compose](https://github.com/SamuelSoaresSilva/postgres-docker/blob/compose/docker-compose.yaml), run your terminal and execute the following codes bellow
### Start container

```
docker-compose up -d
```

### See running containers 

```
docker ps
```

### stop containers 

```
docker-compose down
```

# EndPoints

### Is api running?
> Params: none
```
/api/is-running
```

### Login
> Params:
>>     {
>> 
>>      "email":  "",
>>      "password":  ""
>> 
>>     }
```
/auth/login
```

### Register
> Params:
>>      {
>>        "nickName": "",
>>        "email":  "",
>>        "password":  ""
>>      }
```
/auth/register
```


