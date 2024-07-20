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

- install check `$ java -version`

### Install MySQL
- `$ sudo dnf install mysql-8.0.36-1.el9`
- `$ sudo dnf install mysql-server-8.0.36-1.el9`
- `$ sudo systemctl enable mysqld` (for auto restart)
- active check
    - `$ sudo systemctl status mysqld`
- check log
    - `$ tail -100 /var/log/mysql/mysqld.log`
-create user
    - `$ create user '[username]'@'%' identified by '[password]';`
    - `$ select user,host from mysql.user;` (for check)
### Install Redis
- `$ sudo dnf install redis-6.2.7-1.el9`
- active check 
    - `$ sudo cp /usr/lib/systemd/system/redis.service /etc/systemd/system/redis.service`
    - `$ sudo systemctl daemon-reload`
    - `$ sudo systemctl enable redis` (for auto restart)
    - `$ sudo systemctl start redis`
    - `$ sudo systemctl status redis`
- check log
    - `$ tail -100 /var/log/redis/redis.log`
