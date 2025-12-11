# Linux Commands

| #  | Command    | Description / Use                  | Common Options / Examples                              |       |
| -- | ---------- | ---------------------------------- | ------------------------------------------------------ | ----- |
| 1  | `ls`       | List files and directories         | `ls -l` (long format), `ls -a` (all files)             |       |
| 2  | `cd`       | Change directory                   | `cd /path/to/dir`, `cd ..`, `cd ~`                     |       |
| 3  | `pwd`      | Print working directory            | `pwd`                                                  |       |
| 4  | `mkdir`    | Create directory                   | `mkdir mydir`, `mkdir -p /a/b/c`                       |       |
| 5  | `rmdir`    | Remove empty directory             | `rmdir mydir`                                          |       |
| 6  | `rm`       | Remove files or directories        | `rm file.txt`, `rm -rf folder`                         |       |
| 7  | `cp`       | Copy files / directories           | `cp file1 file2`, `cp -r dir1 dir2`                    |       |
| 8  | `mv`       | Move / rename files or directories | `mv old.txt new.txt`                                   |       |
| 9  | `touch`    | Create empty file                  | `touch file.txt`                                       |       |
| 10 | `cat`      | Display file content               | `cat file.txt`, `cat file1 file2`                      |       |
| 11 | `less`     | View file content page by page     | `less file.txt`                                        |       |
| 12 | `more`     | View file content page by page     | `more file.txt`                                        |       |
| 13 | `head`     | Display first N lines              | `head -n 10 file.txt`                                  |       |
| 14 | `tail`     | Display last N lines               | `tail -n 10 file.txt`, `tail -f file.log`              |       |
| 15 | `echo`     | Print text / variables             | `echo "Hello"`, `echo $PATH`                           |       |
| 16 | `find`     | Search files/directories           | `find / -name "*.log"`, `find . -type f -size +1M`     |       |
| 17 | `grep`     | Search text in files               | `grep "error" file.log`, `grep -r "TODO" .`            |       |
| 18 | `wc`       | Word / line / char count           | `wc -l file.txt`, `wc -w file.txt`                     |       |
| 19 | `cut`      | Extract columns/fields             | `cut -d',' -f1 file.csv`                               |       |
| 20 | `sort`     | Sort file lines                    | `sort file.txt`, `sort -r file.txt`                    |       |
| 21 | `uniq`     | Filter duplicate lines             | `sort file.txt                                         | uniq` |
| 22 | `diff`     | Compare files line by line         | `diff file1.txt file2.txt`                             |       |
| 23 | `cmp`      | Compare files byte by byte         | `cmp file1 file2`                                      |       |
| 24 | `tar`      | Archive files                      | `tar -cvf archive.tar folder/`, `tar -xvf archive.tar` |       |
| 25 | `gzip`     | Compress files                     | `gzip file.txt`, `gunzip file.txt.gz`                  |       |
| 26 | `zip`      | Compress files                     | `zip archive.zip file1 file2`, `unzip archive.zip`     |       |
| 27 | `df`       | Disk space usage                   | `df -h` (human-readable)                               |       |
| 28 | `du`       | Disk usage of files/dirs           | `du -sh *`, `du -h folder/`                            |       |
| 29 | `free`     | Memory usage                       | `free -h`                                              |       |
| 30 | `top`      | Process monitoring                 | Interactive, press `q` to exit                         |       |
| 31 | `htop`     | Interactive process monitor        | Needs installation (`sudo apt install htop`)           |       |
| 32 | `ps`       | Show running processes             | `ps aux`, `ps -ef`                                     |       |
| 33 | `kill`     | Kill process by PID                | `kill 1234`, `kill -9 1234`                            |       |
| 34 | `pkill`    | Kill process by name               | `pkill java`                                           |       |
| 35 | `chmod`    | Change file permissions            | `chmod 755 file`, `chmod +x script.sh`                 |       |
| 36 | `chown`    | Change file owner                  | `chown user:group file.txt`                            |       |
| 37 | `ln`       | Create link                        | `ln -s /source/path /link/path`                        |       |
| 38 | `wget`     | Download files                     | `wget https://example.com/file.zip`                    |       |
| 39 | `curl`     | HTTP requests / download           | `curl -O https://example.com/file`                     |       |
| 40 | `scp`      | Copy files over SSH                | `scp file user@host:/path`                             |       |
| 41 | `rsync`    | Sync files/directories             | `rsync -avz source/ dest/`                             |       |
| 42 | `ssh`      | Connect to remote server           | `ssh user@host`                                        |       |
| 43 | `who`      | Show logged-in users               | `who`, `w`                                             |       |
| 44 | `uptime`   | System uptime                      | `uptime`                                               |       |
| 45 | `hostname` | Show hostname                      | `hostname`                                             |       |
| 46 | `uname`    | Show system info                   | `uname -a`                                             |       |
| 47 | `env`      | Show environment variables         | `env`, `printenv`                                      |       |
| 48 | `export`   | Set env variable                   | `export PATH=$PATH:/opt/bin`                           |       |
| 49 | `history`  | Show command history               | `history`, `!123` (run command #123)                   |       |
| 50 | `man`      | Show manual/help                   | `man ls`, `man grep`                                   |       |

---

# ✅ **Extra Notes**

* `ls -lah` → long, human-readable, all files (hidden included)
* `grep -i` → case-insensitive search
* `du -sh` → summarize human-readable disk usage
* `tar -czvf archive.tar.gz folder/` → archive + compress in one step

---


# ✅ Docker

# ✅ Dockerfiles & Multi-Stage Builds

# ✅ Kubernetes (Pods, Deployments, Services, ConfigMaps, Secrets)

# ✅ CI/CD (GitHub Actions / Jenkins / GitLab CI)

# ✅ Cloud Fundamentals (IAM, VPC, Load Balancers in AWS/GCP/Azure)

This is long and detailed but **clean, structured, and job-oriented**.

---

# 🚀 **1. DOCKER (Containerization)**

## ⭐ What is Docker?

Docker is a platform to **package applications into containers** — lightweight, portable environments containing:

✔ Your app
✔ Dependencies
✔ Runtime
✔ Config

Like a **mini-VM without OS overhead**.

---

## ⭐ Why containers?

| Feature                    | Explanation                              |
| -------------------------- | ---------------------------------------- |
| **Consistent environment** | Works same on Windows, Linux, cloud      |
| **Lightweight**            | Uses host kernel, not full OS            |
| **Portable**               | Move from laptop → server → cloud easily |
| **Isolated**               | Runs independent from other apps         |
| **Easy scaling**           | Spin up 100 containers instantly         |

---

## ⭐ Image vs Container

| Term          | Meaning                       |
| ------------- | ----------------------------- |
| **Image**     | Blueprint (template)          |
| **Container** | Running instance of the image |

---

# 🧱 **Dockerfile (Build Instructions)**

A Dockerfile describes **how to build the image**.

Sample for a Spring Boot Java app:

```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build:

```
docker build -t myapp .
```

Run:

```
docker run -p 8080:8080 myapp
```

---

# 🏗 **Multi-Stage Docker Builds**

Used to keep final image **small**.

```dockerfile
# Stage 1: Build
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Benefits:
✔ Remove build tools from final image
✔ Small runtime footprint
✔ Faster deployments

---

# ---------------------------------------------------

# 🚀 **2. KUBERNETES BASICS**

Kubernetes (K8s) is a **container orchestration platform**.

---

# ⭐ Core Concepts

## 1️⃣ **POD**

Smallest runnable unit in K8s.
A pod = 1 or more containers that share:

✔ Network
✔ Storage
✔ Namespace

---

## 2️⃣ **DEPLOYMENT**

Used to manage pods.

✔ Handles scaling
✔ Rolling updates
✔ Self-healing

Example:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp-deployment
spec:
  replicas: 3
  selector:
    matchLabels:
      app: myapp
  template:
    metadata:
      labels:
        app: myapp
    spec:
      containers:
        - name: myapp
          image: pratyush/myapp:latest
          ports:
            - containerPort: 8080
```

---

## 3️⃣ **SERVICE**

Exposes Pods inside or outside cluster.

| Type             | Purpose                |
| ---------------- | ---------------------- |
| **ClusterIP**    | Internal communication |
| **NodePort**     | Exposes port on Node   |
| **LoadBalancer** | Cloud external LB      |

Example:

```yaml
apiVersion: v1
kind: Service
metadata:
  name: myapp-service
spec:
  type: LoadBalancer
  selector:
    app: myapp
  ports:
    - port: 80
      targetPort: 8080
```

---

## 4️⃣ **CONFIGMAP**

Stores **non-sensitive** app configuration.

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: app-config
data:
  DB_HOST: localhost
```

Mount as env:

```yaml
env:
  - name: DB_HOST
    valueFrom:
      configMapKeyRef:
        name: app-config
        key: DB_HOST
```

---

## 5️⃣ **SECRET**

Stores **sensitive** info (Base64 encoded).

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: db-secret
type: Opaque
data:
  password: cGFzc3dvcmQ=
```

---

# ---------------------------------------------------

# 🚀 **3. CI/CD PIPELINES**

CI/CD = Continuous Integration + Continuous Deployment.

Pipeline stages:

```
commit → build → test → package → deploy
```

---

# ⭐ **GitHub Actions Example**

`.github/workflows/deploy.yml`

```yaml
name: Build & Deploy

on:
  push:
    branches: [ "main" ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Build project
        run: mvn clean package -DskipTests
      - name: Build Docker image
        run: docker build -t pratyush/myapp .
      - name: Push to Docker Hub
        run: docker push pratyush/myapp
```

---

# ⭐ **Jenkins Pipeline**

`Jenkinsfile`:

```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Docker Build') {
            steps {
                sh 'docker build -t myapp .'
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f k8s/deployment.yml'
            }
        }
    }
}
```

---

# ⭐ **GitLab CI**

`.gitlab-ci.yml`

```yaml
build:
  script:
    - mvn clean package
    - docker build -t registry/myapp .
    - docker push registry/myapp
deploy:
  script:
    - kubectl apply -f deployment.yml
```

---

# ---------------------------------------------------

# 🚀 **4. CLOUD FUNDAMENTALS**

Applicable for AWS / GCP / Azure.

---

# ⭐ IAM (Identity & Access Management)

Used to control:

✔ Who can access
✔ What they can do
✔ On which resource

IAM Consists of:

* **Users**
* **Groups**
* **Roles**
* **Policies (JSON permissions)**

Example AWS IAM Policy:

```json
{
  "Effect": "Allow",
  "Action": ["s3:*"],
  "Resource": "*"
}
```

---

# ⭐ VPC (Virtual Private Cloud)

A private network inside cloud.

VPC contains:

✔ Subnets (public, private)
✔ Route Tables
✔ Internet Gateway
✔ NAT Gateway
✔ Security Groups

Example architecture:

```
      Internet
          |
  ┌──────────────────┐
  │  VPC             │
  │  ┌──────┐        │
  │  │IGW   │        │
  │  └──┬───┘        │
  │     │            │
  │ Public Subnet    │ ← Load Balancer
  │ Private Subnet   │ ← App, DB
  └──────────────────┘
```

---

# ⭐ Load Balancers

| Cloud | LB Type         |
| ----- | --------------- |
| AWS   | ALB, NLB        |
| GCP   | HTTP LB, TCP LB |
| Azure | App Gateway, LB |

Responsibilities:

✔ Distribute traffic
✔ Health checks
✔ SSL termination
✔ Auto scaling support

---

# ⭐ Example Cloud Deployment Flow

```
Code → GitHub
  → GitHub Actions → Docker image
    → Push to registry
      → Kubernetes cluster (AWS EKS / GCP GKE / Azure AKS)
```

---

# ---------------------------------------------------

# 🎯 FINAL SUMMARY TABLE

| Topic             | Key Concepts                                  |
| ----------------- | --------------------------------------------- |
| Docker            | Containerize apps using Dockerfile            |
| Multi-stage build | Build + runtime layers                        |
| Kubernetes        | Pods, Deployment, Service, ConfigMap, Secrets |
| CI/CD             | Build → Test → Package → Deploy pipelines     |
| Cloud             | IAM, VPC, Load Balancers                      |

---

If you want, I can also provide:

✅ A complete Spring Boot + Docker + Kubernetes project setup
✅ A GitHub repository structure for DevOps projects
✅ High-level cloud architecture diagrams
✅ Mock interview questions for DevOps, Docker, K8s, Cloud

Just tell me!
Here is a **deep, interview-ready, architecture-level explanation** of:

# ✅ Deploying Java apps on AWS ECS, AWS EKS, GKE

# ✅ Logging & Monitoring (ELK/EFK, Prometheus, Grafana)

# ✅ Distributed Tracing (OpenTelemetry / Jaeger / Zipkin)

# ✅ Terraform basics (IaC)

This is complete enough for **system design + DevOps + cloud interviews**.

---

# 🚀 **1. DEPLOYING JAVA APPS ON CLOUD**

## ⭐ **1.1 AWS ECS (Elastic Container Service)**

ECS = AWS-managed container orchestration (simpler than Kubernetes).

### ✔ When to use ECS?

* Want easy container orchestration
* Don’t want to manage Kubernetes
* Using AWS Fargate (serverless containers)

---

## ⭐ ECS Architecture

```
Task Definition → ECS Service → Cluster → Fargate/EC2
                        ↓
                      Load Balancer
```

### 🚀 Steps to Deploy Spring Boot App on ECS

1. Create Docker image
2. Push to ECR (Elastic Container Registry)
3. Create ECS cluster
4. Create Task Definition

   * CPU/memory
   * Container image
   * Ports
5. Create ECS Service

   * Attach load balancer
   * Auto scaling
6. Run on
   ✔ Fargate → serverless
   ✔ EC2 → your VM instances

---

## ⭐ **1.2 AWS EKS (Elastic Kubernetes Service)**

Fully managed Kubernetes.

### ✔ When to use EKS?

* Microservices + Kubernetes
* Multi-cloud portability
* Need advanced features (Ingress, HPA, CRDs)

---

## ⭐ EKS Architecture

```
EKS Cluster
  ├── Nodes (EC2 or Fargate)
  ├── Deployments
  ├── Services
  └── Ingress (Load Balancer)
```

### 🚀 Steps to Deploy Spring Boot on EKS

1. Containerize app with Docker
2. Push image to ECR
3. Create EKS cluster
4. Deploy YAML files

   * deployment.yaml
   * service.yaml
   * ingress.yaml
5. Enable auto-scaling

   * HPA (CPU/memory-based)
6. Use AWS Load Balancer controller
7. Use ConfigMaps & Secrets

---

## ⭐ **1.3 Deploying on GKE (Google Kubernetes Engine)**

Google’s managed Kubernetes.

### ✔ Why GKE?

* Best Kubernetes performance
* Best autoscaling
* Best networking (GCP internal LB)
* Cheap spot nodes

---

### 🚀 Steps

Same as EKS:

1. Build Docker image
2. Push to Google Artifact Registry
3. Create GKE cluster
4. Apply Kubernetes manifests
5. Expose using `LoadBalancer` service or `Ingress`

---

# ----------------------------------------------------

# 🚀 **2. OBSERVABILITY**

Observability = Logs + Metrics + Tracing
(Not same as monitoring!)

```
LOGS → What happened?
METRICS → How healthy is the system?
TRACING → Why is the system slow?
```

---

# ⭐ **2.1 LOGGING — ELK / EFK Stack**

ELK = Elasticsearch + Logstash + Kibana
EFK = Elasticsearch + Fluentd/Fluentbit + Kibana

### ✔ Architecture

```
App logs (JSON)
  → Fluent Bit (agent)
      → Elasticsearch
          → Kibana (dashboard)
```

---

## ⭐ Log formats for Java (Spring Boot)

Modify application.properties:

```ini
logging.pattern.level=%5p
logging.pattern.console=%d{yyyy-MM-dd'T'HH:mm:ss} %p %c{1} - %m%n
logging.file.name=app.log
```

Better: output **JSON logs** for ELK parsing:

```gradle
implementation 'net.logstash.logback:logstash-logback-encoder:7.4'
```

`logback-spring.xml`:

```xml
<encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder"/>
```

---

# ⭐ **2.2 METRICS — Prometheus + Grafana**

### ✔ What Prometheus monitors?

* CPU, memory
* JVM metrics (heap, GC, threads)
* HTTP requests
* DB connections
* Custom app metrics

---

## ⭐ Setup for Spring Boot

Add dependencies:

```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

Enable actuator endpoint:

```properties
management.endpoints.web.exposure.include=metrics,prometheus
```

Prometheus will scrape:

```
/actuator/prometheus
```

Grafana → visualize dashboards.

---

# ⭐ **2.3 TRACING — OpenTelemetry / Jaeger / Zipkin**

Tracing helps understand latency across microservices.

### Architecture:

```
App → OpenTelemetry SDK → Collector → Jaeger/Zipkin → UI
```

---

## ⭐ How to enable in Spring Boot

Add dependencies:

```xml
<dependency>
    <groupId>io.opentelemetry</groupId>
    <artifactId>opentelemetry-exporter-otlp</artifactId>
</dependency>
```

Automatic instrumentation:

```
java -javaagent:opentelemetry-javaagent.jar \
     -Dotel.exporter.otlp.endpoint=http://otel-collector:4317 \
     -jar app.jar
```

Supports tracing for:

* HTTP calls
* DB queries
* Kafka messages
* Redis
* Feign clients

---

# ----------------------------------------------------

# 🚀 **3. TERRAFORM BASICS (Infrastructure as Code)**

Terraform = tool to define cloud resources using code.

Instead of clicking on AWS console:

❌ Create EKS manually
❌ Create VPC manually
❌ Create RDS manually

You write code:

```hcl
resource "aws_instance" "my_vm" {
  ami           = "ami-12345"
  instance_type = "t2.micro"
}
```

Then run:

```
terraform init
terraform plan
terraform apply
```

---

# ⭐ Why Terraform?

| Benefit             | Explanation                     |
| ------------------- | ------------------------------- |
| **Repeatable**      | Same infra for dev, stage, prod |
| **Version Control** | Git stores your infra           |
| **Multi-cloud**     | AWS + GCP + Azure               |
| **Automation**      | Perfect for CI/CD pipelines     |

---

## ⭐ Terraform Structure

```
project/
  main.tf        → resources
  variables.tf   → parameter definitions
  outputs.tf     → info to display
  provider.tf    → AWS/GCP/Azure config
```

Example EKS creation using Terraform:

```hcl
module "eks" {
  source          = "terraform-aws-modules/eks/aws"
  cluster_name    = "mycluster"
  cluster_version = "1.27"
}
```

---

# ----------------------------------------------------

# 🎯 FINAL SUMMARY (Interview Cheat Sheet)

| Topic                       | Summary                                |
| --------------------------- | -------------------------------------- |
| **ECS**                     | Easiest container deployment in AWS    |
| **EKS**                     | Managed Kubernetes for microservices   |
| **GKE**                     | Best auto-scaling + fastest Kubernetes |
| **ELK/EFK**                 | Central logging & log search           |
| **Prometheus + Grafana**    | Metrics & real-time dashboards         |
| **Tracing (OTEL + Jaeger)** | Root-cause analysis across services    |
| **Terraform**               | Write infrastructure like code         |

---

If you want, I can also create:

✅ Full Kubernetes YAML files for a Spring Boot app
✅ Full Terraform module to deploy EKS cluster
✅ CI/CD pipeline for ECS/EKS/GKE
✅ GitHub Repository structure for DevOps projects
✅ Cloud architecture diagrams
✔ Mock interview questions for DevOps + Cloud + Containers

Just tell me!


Here is a **complete, clean, categorized list of all important Docker commands** you must know for interviews and real-world work (DevOps, backend, microservices, deployment, cloud).

---

# ✅ **1. Docker Version & Help**

| Purpose               | Command            |
| --------------------- | ------------------ |
| Check Docker version  | `docker --version` |
| Show system-wide info | `docker info`      |
| Docker help           | `docker help`      |

---

# ✅ **2. Images (Build, List, Remove, Pull, Push)**

| Purpose                | Command                          |
| ---------------------- | -------------------------------- |
| List all images        | `docker images`                  |
| Pull an image          | `docker pull <image>:<tag>`      |
| Build image            | `docker build -t <name>:<tag> .` |
| Remove image           | `docker rmi <image-id>`          |
| Remove unused images   | `docker image prune`             |
| Tag an image           | `docker tag <src> <repo>:<tag>`  |
| Push image to registry | `docker push <repo>:<tag>`       |

---

# ✅ **3. Containers (Run, Stop, Remove)**

| Purpose                       | Command                                          |
| ----------------------------- | ------------------------------------------------ |
| Run container                 | `docker run <image>`                             |
| Run container in background   | `docker run -d <image>`                          |
| Run with port mapping         | `docker run -p 8080:8080 <image>`                |
| Run with name                 | `docker run --name myapp <image>`                |
| Run with volume               | `docker run -v host_path:container_path <image>` |
| List running containers       | `docker ps`                                      |
| List all containers           | `docker ps -a`                                   |
| Stop a container              | `docker stop <id>`                               |
| Start a container             | `docker start <id>`                              |
| Restart a container           | `docker restart <id>`                            |
| Remove a container            | `docker rm <id>`                                 |
| Remove all stopped containers | `docker container prune`                         |

---

# ✅ **4. Container Interaction**

| Purpose                    | Command                                 |
| -------------------------- | --------------------------------------- |
| Enter container shell      | `docker exec -it <container> /bin/bash` |
| View container logs        | `docker logs <container>`               |
| Follow logs live           | `docker logs -f <container>`            |
| Copy file host → container | `docker cp file <container>:/path`      |
| Copy file container → host | `docker cp <container>:/file host-path` |

---

# ✅ **5. Volumes (Data Persistence)**

| Purpose               | Command                        |
| --------------------- | ------------------------------ |
| Create volume         | `docker volume create mydata`  |
| List volumes          | `docker volume ls`             |
| Inspect volume        | `docker volume inspect mydata` |
| Remove volume         | `docker volume rm mydata`      |
| Remove unused volumes | `docker volume prune`          |

---

# ✅ **6. Networks (Communication between containers)**

| Purpose                           | Command                                           |
| --------------------------------- | ------------------------------------------------- |
| List networks                     | `docker network ls`                               |
| Create network                    | `docker network create mynetwork`                 |
| Remove network                    | `docker network rm mynetwork`                     |
| Connect container to network      | `docker network connect mynetwork <container>`    |
| Disconnect container from network | `docker network disconnect mynetwork <container>` |

---

# ✅ **7. Docker Compose Commands (Very Important)**

| Purpose                    | Command                         |
| -------------------------- | ------------------------------- |
| Start services             | `docker-compose up`             |
| Start in background        | `docker-compose up -d`          |
| Stop services              | `docker-compose down`           |
| Rebuild + start            | `docker-compose up --build`     |
| View logs                  | `docker-compose logs`           |
| View specific service logs | `docker-compose logs <service>` |
| Stop services only         | `docker-compose stop`           |
| Restart services           | `docker-compose restart`        |
| List services              | `docker-compose ps`             |

---

# ✅ **8. Clean-Up Commands (Memory + Disk)**

Extremely useful — used daily.

| Purpose                     | Command                            |
| --------------------------- | ---------------------------------- |
| Remove stopped containers   | `docker container prune`           |
| Remove unused images        | `docker image prune`               |
| Remove unused networks      | `docker network prune`             |
| Remove unused volumes       | `docker volume prune`              |
| Remove EVERYTHING unused    | `docker system prune`              |
| Remove everything + volumes | `docker system prune -a --volumes` |

---

# ✅ **9. Inspect & Debug**

| Purpose                    | Command                  |
| -------------------------- | ------------------------ |
| Inspect container metadata | `docker inspect <id>`    |
| Inspect image              | `docker inspect <image>` |
| Resource usage             | `docker stats`           |
| Disk usage summary         | `docker system df`       |

---

# ✅ **10. Docker Registry / Login**

| Purpose             | Command         |
| ------------------- | --------------- |
| Login to Docker Hub | `docker login`  |
| Logout              | `docker logout` |

---

# ✅ **11. Important Flags (used in interviews)**

| Flag                | Meaning                          |
| ------------------- | -------------------------------- |
| `-d`                | Run in background                |
| `-p 8080:8080`      | Port mapping                     |
| `--name myapp`      | Name container                   |
| `-v host:container` | Volume mount                     |
| `-e KEY=value`      | Environment variable             |
| `--network`         | Assign network                   |
| `--rm`              | Auto-delete container after exit |

---

# If you want, I can also give:

✅ Dockerfile full tutorial
✅ Multi-stage Dockerfile example (Spring Boot + React)
✅ Docker Compose real project example
✅ Spring Boot Docker deployment guide
✅ Docker + Kubernetes combination

Just tell me **“Next”** or specify what you want.
