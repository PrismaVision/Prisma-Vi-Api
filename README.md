# PrismaVI
___
## How to run local

Requirements:
[Docker Desktop](https://www.docker.com/products/docker-desktop/)

### 1. Choose a folder and create a file named `docker-compose.yaml`
### 2. Inside the file, paste this code: 
```yaml
    services:

        postgres-compose:
        image: postgres
        environment:
            POSTGRES_PASSWORD: admin
        ports:
            - "5432:5432"
```
### 3. Start Docker Desktop
>Note: In the folder where you downloaded or created [docker compose](https://github.com/SamuelSoaresSilva/postgres-docker/blob/compose/docker-compose.yaml), run your terminal and execute the following codes bellow for:
### 4. Start container

```bash
docker-compose up
```
>Note: for stop container, do de following instructions
### Stop container

```
docker-compose down
```
or press

`Ctrl + C`


# EndPoints

### Is api running?
**Endpoint**
```
/api/is-running
```
**Method:** POST

Params:
* `none`


### Login
**Endpoint**
```
/auth/login
```
**Method:** POST
Params:
```Json
{
  "email":  "",
  "password":  ""
}
```

### Register
**Endpoint**
```
/auth/register
```
**Method:** POST

Params:
```Json
{
  "nickName": "",
  "email":  "",
  "password":  ""
}
```

### Search color
**Endpoint**
```
/api/search-color
```
**Method:** GET

Params:
* `color hex` (Example: `#FF5733`)
___

### Mock search color
**Endpoint**
```
/api/mock/search-color
```
**Method:** GET

Params:

* `color hex` (Example: `#FF5733`)

___
#### Links
[Render](https://dashboard.render.com/project/prj-csd8ocqj1k6c73bte4q0) | [Railway](https://railway.app/project/768c5ef8-8bae-4122-94cc-544bdb5c4380)

#### Request DNS for tests
    https://prisma-vi-api.onrender.com/
#### Request DNS for real
    https://prisma-vi-api-production.up.railway.app





