# Backend setup

## Setup in server

### Steps
- Install dependencies
- Setup gitlab runner
- Create necessary files in specified locations in the server
- run in opt/web-project/db: `sudo docker-compose up -d`
- run Gitlab pipeline


### Dependencies

#### Install Docker and Docker-compose
- Update system:
    ```
    sudo apt-get update
    sudo apt-get install \
    ca-certificates \
    curl \
    gnupg \
    lsb-release
    ```
- add dockers gpg key:
    ```
    sudo mkdir -p /etc/apt/keyrings
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
    ```
- Set up the repository:
    ```
    echo \
      "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
      $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
    ```
- Install docker and docker-compose: `sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin`
- Verify installation: `sudo docker run hello-world`
- Enable service `sudo systemctl enable --now docker`
- Confirm installation `docker --version`
- Confirm docker-compose `docker-compose --version`

#### Install Java
- `sudo apt install openjdk-17-jdk openjdk-17-jre`
#### Install Gitlab runner
- Add the official gitlab repo: `curl -L "https://packages.gitlab.com/install/repositories/runner/gitlab-runner/script.deb.sh" | sudo bash`
- Install Gitlab runner: `sudo apt-get install gitlab-runner`
- Check status: `sudo gitlab-runner status`
- Start command: `sudo gitlab-runner start`
- Add runner to sudoers group: `gitlab-runner ALL=(ALL:ALL) ALL`
- Set NOPASSWD: `gitlab-runner ALL=(ALL) NOPASSWD: ALL`

#### Register Gitlab runner
- In gitlab on your project page go to Settings > CI/CD > Runners
- There you can find the URL and registration token.
- Run `gitlab-runner register`
- Enter the URL `https://gitlab.cs.ttu.ee/`
- Enter the registration token `GR1348941LR37xkACSNqQjnEGGdtj`
- Enter description: `Backend runner`
- Enter tag: `backend-runner`
- Enter executor: `shell`

### File contents
File locations in the server.
```
opt
`-- web-project
    |-- backend
    |   |-- application.properties
    |   `-- docker-compose.yml
    |-- db
    |   |-- docker-compose.yml
    |   `-- postgres-data
    `-- frontend
        `-- docker-compose.yml
```
/opt/web-project/backend/docker-compose.yml
```
version: "3.7"
services:
    web-project:
        image: web-project-backend:latest
        container_name: web-project-backend
        restart: always
        ports:
            - 8080:8080
        volumes:
            - /opt/web-project/backend/application.properties:/app/application.properties
```
/opt/web-project/backend/application.properties
```
spring.datasource.url=jdbc:postgresql://193.40.255.11:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=docker
spring.datasource.driver-class-name=org.postgresql.Driver
```
/opt/web-project/db/docker-compose.yml
```
version: "3.7" # optional since v1.27.0
services:
    postgres:
        image: postgres:14.1
        environment:
            - POSTGRES_USER=postgres
            - POSTGRES_PASSWORD=docker
        ports:
            - '5432:5432'
        volumes:
        - ./postgres-data:/var/lib/postgresql/data
```

## Setup locally for development

### Setup steps
- Install dependencies
- Make sure docker is running.
- Git clone: `git clone https://gitlab.cs.ttu.ee/andrsa/veebiprojekt-backend.git`
- In project folder: `mvn clean package`
- Build Docker image: `docker build -t web-project-backend .`
- In project folder: `docker-compose up`

### Links
[Wiki](https://gitlab.cs.ttu.ee/andrsa/veebiprojekt-backend/-/wikis/home) <br>
[Frontend repository](https://gitlab.cs.ttu.ee/andrsa/veebiprojekt-frontend)