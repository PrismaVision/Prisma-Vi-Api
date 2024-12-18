# Application in prod

#### Links
[Render](https://dashboard.render.com/project/prj-csd8ocqj1k6c73bte4q0) | [Railway](https://railway.app/project/768c5ef8-8bae-4122-94cc-544bdb5c4380)

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
```
/api/is-running
```
> Params:
>> none


### Login
```
/auth/login
```
> Params:
>>     {
>>      "email":  "",
>>      "password":  ""
>>     }


### Register
```
/auth/register
```
> Params:
>>      {
>>        "nickName": "",
>>        "email":  "",
>>        "password":  ""
>>      }

### Search color
```
/api/search-color
```
> Params:
>>      <color hex>

### Mock search color
```
/api/mock/search-color
```
> Params:
>>      <color hex>




