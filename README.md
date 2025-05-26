# PrismaVI
___
## How to run local

Requirements:
[Docker Desktop](https://www.docker.com/products/docker-desktop/)
___

### 1. Create a `docker-compose.yaml` File
Choose a folder where you'd like to run the project and create a file named `docker-compose.yaml`.
___
### 2. Add the Following Configuration
Paste the following code into the `docker-compose.yaml` file:
```yaml
services:

  postgres-compose:
      image: postgres
      environment:
        POSTGRES_PASSWORD: admin
      ports:
        - "5432:5432"
```
___
### 3. Start Docker Desktop
Make sure that Docker Desktop is running on your system.
___
### 4. Start container
In the folder where you downloaded or created [docker compose](https://github.com/SamuelSoaresSilva/postgres-docker/blob/compose/docker-compose.yaml), run your terminal and execute the following codes bellow for:

```bash
docker-compose up
```
For stop container, do the following instructions
### Stop container

```bash
docker-compose down
```
or press

`Ctrl + C`
___
### 6. Environment Variables
There are some environment variables required for the application to run. The only one needed to run locally is:
* `GEMINI_API_KEY`: This is your Gemini API key.
> Get your key in [generate a gemini api key](https://aistudio.google.com/apikey?hl=pt-br&_gl=1*wgg145*_ga*MTE1ODU2MTA0Ni4xNzQ0MDQ5ODk5*_ga_P1DBVKWT6V*MTc0NDEyNDUzOS4yLjEuMTc0NDEyNDU2OS4zMC4wLjEzNzYxMzUxNDc.)

**Important:** Never commit the `GEMINI_API_KEY` or any sensitive keys into your codebase. Always ensure that they are stored securely, for example, in an `.env` file or through your environment configuration, and are excluded from version control using a `.gitignore` file.
___

### 7. Running the Spring Service
>After starting the database, you can run the Spring service in two ways:

Option 1: Using Your IDE (Eclipse, IntelliJ, etc.)
Open the project in your preferred IDE.
Right-click on the Application.java file or the main file of your Spring application.
Select the option to Run as Java Application or Run Spring Boot App.

Option 2: Using Maven from the Command Line
If you prefer to run the Spring service via the command line, follow these steps:
Navigate to the root folder of your Spring project (where the pom.xml file is located).
Run the following Maven command to start the service:
```bash
mvn spring-boot:run
```
___
## EndPoints


### Is api running?
**Endpoint**
```
/api/is-running
```
**Method:** `GET`

Params:
* `none`

___
### Login
**Endpoint**
```
/auth/login
```
**Method:** `POST`
Params:
```Json
{
  "email":  "",
  "password":  ""
}
```
___
### Register
**Endpoint**
```
/auth/register
```
**Method:** `POST`

Params:
```Json
{
  "nickName": "",
  "email":  "",
  "password":  ""
}
```
___
### Search color
**Endpoint**
```
/api/search-color
```
**Method:** `GET`

Params:
* `color hex` (Example: `#FF5733`)
___

### Mock search color
**Endpoint**
```
/api/mock/search-color
```
**Method:** `GET`

Params:

* `color hex` ( Example: `#FF5733` )

___

## How to Contribute 
> If you are reading this, I believe you want to contribute or at least are 
interested in the project, in any case, thank you for the initiative.
We appreciate your help in making this project better!

### Additional Guidelines
- **Coding Style:** Please follow the existing coding conventions and style used in the project.

- **Tests:** Make sure that your code is well-tested.

- **Documentation:** Update the documentation if you add or modify any features.
___
### 1. Fork the Repository
- Click on the `Fork` button at the top right of the repository page to create your own copy of the project.
___
### 2. Clone Your Fork Locally
- After forking, clone the repository to your local machine:
```bash
git clone https://github.com/<your-username>/Prisma-Vi-Api.git
```
___
### 3. Create a New Branch
- Always create a new branch for your feature or bug fix. This keeps the main branch clean and allows for easier collaboration. Use a descriptive name for your branch:
```bash
git checkout -b <my-feature-branch>
```
___
### 4. Make Your Changes
- Work on your feature or bug fix. Ensure that you follow the project's coding style and write clear, concise code.
___
### 5. Test Your Changes
- Before pushing your changes, make sure everything works properly by running the project locally and testing the relevant parts.
___
### 6. Commit Your Changes
- After making your changes, commit them with a clear and concise commit message:
```bash
git commit -m "Add new feature X or fix bug Y"
```
___
### 7. Push Your Changes
- Push your changes to your forked repository:
```bash
git push origin <my-feature-branch>
```
### 8. Open a Pull Request
- Go to the original repository on GitHub and click on New Pull Request. 
- Select your branch and submit the pull request (PR) with a description of what you’ve done and why it's needed. 
- Our team will review your changes and provide feedback if necessary.

### 9. Address Feedback
- If there is any feedback on your PR, make the necessary changes and push them to your branch. GitHub will automatically update the PR.

### 10. Celebrate!
- Once the PR is approved, it will be merged into the main repository. You’ve successfully contributed to the project!
___
#### Links
[Render](https://dashboard.render.com/project/prj-csd8ocqj1k6c73bte4q0) | [Railway](https://railway.app/project/768c5ef8-8bae-4122-94cc-544bdb5c4380)

#### Request DNS for tests
    https://prisma-vi-api.onrender.com/
#### Request DNS for real
    https://prisma-vi-api-production.up.railway.app





