### Create AWS ec2 Instance
- platform: Cent OS
- type: t2.micro
- zone: ap-northeast-2a
- name: board-service-001-prod

### Setup Timezone
- `$ sudo timedatectl set-timezone Asia/Seoul`

### Install java , maven
- `$ sudo dnf install java-17-openjdk-devel.x86_64`
- `$ sudo dnf install wget`
- `$ wget https://dlcdn.apache.org/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz`
- `$ tar -xvf apache-maven-3.8.8-bin.tar.gz`

- `$ vi ~/.bash_profile`
- ```
  #JAVA
  export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-17.0.6.0.10-3.el9.x86_64
  #MAVEN
  export MAVEN_HOME=/home/ec2-user/apache-maven-3.8.8
  
  export PATH=$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH`
- install check 
    - `$ java -version`
    - `$ mvn --version`

### Install MySQL
- `$ sudo dnf install mysql-8.0.36-1.el9`
- `$ sudo dnf install mysql-server-8.0.36-1.el9`
- `$ sudo systemctl enable mysqld` (for auto restart)
- active check
    - `$ sudo systemctl start mysqld` 
    - `$ sudo systemctl status mysqld`
- check log
    - `$ tail -100 /var/log/mysql/mysqld.log`
- set root password
    - $ `mysql -u root` (Connect to MySQL)
    - ```
      USE mysql;
      ALTER USER 'root'@'localhost' IDENTIFIED BY '[new_password]';
      FLUSH PRIVILEGES;
      EXIT;
      ```
      (Set the root password)
- create user
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

### Setting environment variables for Application
- `$ vi ~/.bashrc`
    - `export ENCRYPTOR_PASSWORD=[password]` (jasypt password key)
- `$ source ~/.bashrc`

### Install git
- `$ sudo dnf install git-2.43.5-1.el9`
- check
    - `$ git --version`

### Install lsof
- `$ sudo dnf install lsof-4.94.0-3.el9`
- install check
    - `$ lsof -v`

### Check the listening ports on Linux
- `$ sudo lsof -i -P -n | grep LISTEN

### Application database DDL
- database
    - ```
      CREATE DATABASE `gangsaeng`;
      ```
- boards
    - `USE gangsaeng;`
    - ```
      CREATE TABLE `boards` (
        `id` bigint unsigned NOT NULL AUTO_INCREMENT,
        `title` varchar(255) NOT NULL,
        `content` text,
        `user_id` bigint unsigned NOT NULL,
        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (`id`)
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
      ```
