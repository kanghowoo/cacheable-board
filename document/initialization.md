### Create AWS ec2 Instance
- type: t3.micro
- zone: ap-southeast-2b
- name: board-service-001-prod

### Setup Timezone
- `$ timedatectl set-timezone Asia/Seoul`

### Install java
- `$ sudo dnf install java-17-openjdk-devel.x86_64`
- `$ sudo dnf install wget`
- `$ wget https://dlcdn.apache.org/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz`
- `$ tar -xvf apache-maven-3.8.8-bin.tar.gz`

### Install MySQL
- `$ sudo dnf install mysql mysql-server`

### Install Redis
- `$ sudo dnf install redis`
