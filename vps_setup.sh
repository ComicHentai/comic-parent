#!/bin/sh

#对VPS进行初始化设置

#首先支持中文
echo '[+] Add Chinese Support';
echo 'LANG="zh_CN.UTF-8"
LANGUAGE="zh_CN:zh:en_US:en"' >> /etc/environment
echo '
en_US.UTF-8 UTF-8
zh_CN.UTF-8 UTF-8
zh_CN.GBK GBK
zh_CN GB2312
' >>  /var/lib/locales/supported.d/local
sudo locale-gen
rm -rf /etc/default/locale
echo 'LANG="zh_CN.UTF-8"
LANGUAGE="zh_CN:zh:en_US:en"' >> /etc/default/locale

#SSH密钥
echo '[+] add ssh-key authorized_keys';
mkdir ~/.ssh;
touch ~/.ssh/authorized_keys;
echo 'ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCjK6q0Z482Doylnbg30PsqywoOBEQk0lvP3oeWVqwHV6l1Hu1cwqSs+tQK3cbGhVu8VzZESF2LoGkvz18mLVuVtPO+727VAQm9qm8BDdaUtqv+wd4yg8rMNzhCoKVtbuarPma9lJldbVaw7c/Hgwq5EPDw1qYq4F7XK+2LTEvkOQ1HghAIU9trnC1PdF8vCZlDt+s/MXsTEMB4qTjsxrdNYRD07brM43CkVI3I+AigJolCfhb8s0xaVTxJxbexyVWz/k6lNyKQiwx7t6nJbq3BeYSQG5RyIkgSAMajHcWZV4dW60VDleyYRWeNNTYfPn1iuTnbemN1+unf0Zk6jmqB root@ding
ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC+sgIEPorYX/T/AYWIx2CYiLyVaavglfa/sR+J6wHdkDyMbijzbHAlBcSFzlmFLqgaJH5sS5RRW1i/O1x2T/7BG4QPMiK0O/Hxp5hoi8AIUXDKYR9f5sHz1hQuSNPi4wZEhtfXetIVk7q2gYBMPlM8SGY1MHxxl+e/MoUJmoTrkfVaPHO2WZtKe/dn7mwbKH8hWr6EYl6pk4KyRc3sLJyLJ6keZDHTJEr4GlvPycPbc6hac25DoHHRaY+fkr/BBP3OsJ7eepqJhbc3cvEGtIOcWDyrIq+YMjIOMDu77LG0HtLJiH2fKc4zKX9Ov10/mUktil+67wXE6ah6axj7YGtr root@hope6537
ssh-rsa AAAAB3NzaC1yc2EAAAABIwAAAQEAtqvfe64oVhqWSGDJE+/TD7JXK/KfuTppYMxX+y31hKvLCjm/YeSzbgINJB3EuLKWYltgcbwwjl/yVRl6GsO8oKdnbNnLoucV/dRUcjR65vx6pcch3RsZT9U68mXDuz1t0uM32sfT1wmpQZaE6ASPS81ddcha23OXlejjEQeaQK55zVO+TvfI/FsVnYPC9MuBZ6+IJOWW3hRw/vip/NwtqnGbBZXlAOp46RZlGNen+HqhrAVQzmV0h5vd3woKPvFxfl3H/jTbBqDUnk49OKjelvvKZcYnI4ARa6rQ1x1otJVEXKvc1ylh0qzk9dsc9E5JkzJWQyM0rBOQm2ZyzymJLw== Hope6537@HOPE6537-RABOOK
ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC/8/9gJ7ShCLeKxznpgauz4vuw+XZgRqSbT260LRaw5FI1KIdSOm3+mx7rtR2qsWwjh/ykQt1TVzOSA7mRNVJnVAfWymbWcWmqPMicVnPc1gKWj0RhfLfU3tlHHr5FNGIXtJmgDcf7JfVmK7qKp7jZXNjj7gXMfqni2LVF11i4EV4zoZK59wxvCa1PAM+CY/GH09KlKMNDWcIrKNBSNB8Qlq6Eo+8Xtdj8JDnx+4UfuB+rJywEnNywLeUjgXZ8FltbM/YRU0rsht1LTWSclj1o5kzkShC/nkGue9u0GeRrIGUFthes47SfwfC61f2s6o74gGZk+C318RvG0akYAQkT hope6537@hope6537deMacBook-Pro.local
ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDI/gkmdCU06b8bZd60FSJ+9HkdCEOw9DBbfV0NEbbZwsMLwwd+amve52/D/91Q0FBsagcBaDP1ZsO3ACqhHEt2LF8BRCiv8CJp0QBlYnOX2Vpa5jeHnEm5gUO7U71agH0WuVXiAjJa/YWfK0jqatdSnwHYudNuOj/BbNsNYkr4/r2dayB3GZdxbhZ4k0wdiwOkRBr48ulhxRj2FjJumcRWws77c582BSBq80Eo7JZ/XwAXcGfQS3fdBaWJqV2tHSOHi2rXsjbOSUPh/3KL+5UZu2j0mGA3L3sgNMxPGppLiTNyA1U2etoQCLsjO/jm7IyueY4CUa3xJL7Q9hNvVg9Z dintama@dintamadeMacBook-Pro.local
ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCc3SY0fLX1qiYFO5aiQvUP7F4j/GQpYCsT36aezqB+lXK6tO4tcOmu73YMHmKoLuhYM+HDrOIdeOn+Fww3OTFAQEmc9uQY6qQFtJKvAvetCAsY7TUtBo277JypYCji2TziwodEGNLn19yP5SRTuE7uJ7PcrAKCj7vmxTKgOrm+SxTZrtLfUcv4WR+S1iiIn5pprhQE82x54k87qhqw40DIJ6Yqj2KNWGzQ/O9TvSyvbUyLT7AgfL6oH8I/Uj0ZpTX58TdhO/tnEM44zJ8yx+1l5d6BuvO4WKhXYIezilfNkCK+4eFrcD4YDQlkZhM1EeODHvpydwPhPJOrHj+qy1Ap root@iZ281hgovp7Z
' >> ~/.ssh/authorized_keys;

#首先需要对apt资源包进行升级
echo '[+] 正在更新apt-get资源包列表中'
sudo apt-get update -y;
echo '[+] 正在更新apt-get已安装资源中'
sudo apt-get upgrade -y;
echo '[+] 正在安装基础依赖'
sudo apt-get install gcc git zsh python expect curl libdnet openssl wget gcc python-dev python-pypcap autoconf automake python3-dev python-gevent python-pip python-m2crypto libxml2-dev libxslt1-dev zlib1g-dev libmysqlclient-dev libxml2-dev libxslt1-dev -y;
echo '[+] 正在从远程获取源代码'

#切换终端为zsh
echo '[+] 获取zsh'
git clone git://github.com/robbyrussell/oh-my-zsh.git ~/.oh-my-zsh
cp ~/.zshrc ~/.zshrc.bak
cp ~/.oh-my-zsh/templates/zshrc.zsh-template ~/.zshrc
echo '[+] 切换默认shell为zsh'
chsh -s /bin/zsh

#安装科学上网客户端
echo '[+] 正在安装python基础库'
echo '[+] 安装科学上网'
sudo pip install shadowsocks
sudo touch /etc/shadowsocks.json
sudo echo '{ "server":"0.0.0.0", "server_port":8388, "local_port":1080,"password":"password", "timeout":600, "method":"aes-256-cfb" }' >> /etc/shadowsocks.json
echo "alias ss.start='ssserver -c /etc/shadowsocks.json -d start'" >> ~/custom_alias
echo "alias ss.stop='ssserver -c /etc/shadowsocks.json -d stop'" >> ~/custom_alias

#安装其他Python基础库
echo '[+] 安装其他Python基础库 mysql-python PyQuery requests scapy pcapy pypcap numpy scipy opencv'
pip install mysql-python
pip install PyQuery
pip install requests
pip install scapy
#pip install numpy
#pip install scipy
#pip install opencv

#获取基础工具包
#配置Java
echo '[+] 正在配置JDK中'
if [ ! -f jdk.tar.gz ]; then
  curl 'http://mirrors.hope6537.com/jdk-8u65-linux-x64.tar.gz' >> jdk.tar.gz
fi
echo '[+] 完成JDK下载,正在解压至share目录'
tar -xzf jdk.tar.gz -C /usr/local/share/;
echo '[+] 导出环境变量中'
echo 'export JAVA_HOME=/usr/local/share/jdk1.8.0_65' >> ~/custom_profile
echo 'export PATH=$PATH:$JAVA_HOME/bin' >> ~/custom_profile

#配置Maven
if [ ! -f maven.tar.gz ]; then
  curl 'http://mirrors.hope6537.com/apache-maven-3.3.3-bin.tar.gz' >> maven.tar.gz;
fi
wget http://mirrors.hope6537.com/settings.xml;
echo '[+] 完成Maven下载,完成m2仓库坐标描述文件下载,正在移动中'
tar -xzf maven.tar.gz -C /usr/local/share/;
mkdir ~/.m2/
cp settings.xml ~/.m2/
echo '[+] 导出环境变量中'
echo 'export M2_HOME=/usr/local/share/apache-maven-3.3.3' >> ~/custom_profile
echo 'export PATH=$PATH:$M2_HOME/bin' >> ~/custom_profile

#配置Tomcat
if [ ! -f tomcat.tar.gz ]; then
  curl 'http://mirrors.hope6537.com/apache-tomcat-8.0.29.tar.gz' >> tomcat.tar.gz;
fi
echo '[+] 完成Tomcat下载'
tar -xzf tomcat.tar.gz -C /usr/local/share/;
echo '[+] 导出环境变量中'
echo 'export CATALINA_HOME=/usr/local/share/apache-tomcat-8.0.29' >> ~/custom_profile
echo 'export PATH=$PATH:$CATALINA_HOME/bin' >> ~/custom_profile

#配置Zookeeper
if [ ! -f zookeeper.tar.gz ]; then
  curl 'http://mirrors.hope6537.com/zookeeper-3.4.7.tar.gz'>> zookeeper.tar.gz;
fi
echo '[+] 完成Zookeeper下载'
tar -xzf zookeeper.tar.gz -C /usr/local/share/;
echo '[+] 配置Zookeeper默认设置中'
mkdir ~/zk
mkdir ~/zk/data
rm -rf ~/zk/data/myid
touch ~/zk/data/myid
echo '10' >> ~/zk/data/myid
echo '[.] 完成zkId设置,正在修改配置文件'
touch /usr/local/share/zookeeper-3.4.7/conf/zoo.cfg
echo 'tickTime=2000
initLimit=10
syncLimit=5
dataDir=/root/zk/data
clientPort=2181
server.1=www.hope6537.com:2555:3555
server.2=ding.hope6537.com:2555:3555
#server.10=jp.hope6537.com:2555:3555' >> /usr/local/share/zookeeper-3.4.7/conf/zoo.cfg
echo '[.] 完成zk配置文件初始化'
echo '[+] 导出环境变量中'
echo 'export ZK_HOME=/usr/local/share/zookeeper-3.4.7' >> ~/custom_profile
echo 'export PATH=$PATH:$ZK_HOME/bin' >> ~/custom_profile

#配置Node
if [ ! -f node.tar.gz ]; then
  curl 'http://mirrors.hope6537.com/node-v5.1.1-linux-x64.tar.gz' >> node.tar.gz;
fi
echo '[+] 完成node下载'
tar -xzf node.tar.gz -C /usr/local/share/;
echo '[+] 导出环境变量中'
echo 'export NODE_HOME=/usr/local/share/node-v5.1.1-linux-x64' >> ~/custom_profile
echo 'export PATH=$PATH:$NODE_HOME/bin' >> ~/custom_profile
echo '[+] 配置npm和cnpm'
/usr/local/share/node-v5.1.1-linux-x64/bin/npm install -g cnpm --registry=https://registry.npm.taobao.org


#其他资源
if [ ! -f ocservauto.sh ]; then
  wget 'http://mirrors.hope6537.com/ocservauto.sh'
fi
echo '[+] 完成VPN部署脚本下载'


#配置Nginx
echo '[-] 开始进行Nginx编译构建'
if [ ! -f nginx.tar.gz ]; then
  curl 'http://mirrors.hope6537.com/nginx-1.8.0.tar.gz' >> nginx.tar.gz;
fi
echo '[+] 完成nginx下载'
if [ ! -f zlib-1.2.8.tar.gz ]; then
wget 'http://mirrors.hope6537.com/zlib-1.2.8.tar.gz'
fi
echo '[+] 完成zlib下载'

if [ ! -f pcre-8.38.tar.gz ]; then
wget 'http://mirrors.hope6537.com/pcre-8.38.tar.gz'
fi
echo '[+] 完成pcre下载'
if [ ! -f ngx_cache_purge-2.3.tar.gz ]; then
wget http://labs.frickle.com/files/ngx_cache_purge-2.3.tar.gz
fi
echo '[+] 完成nginx 缓存清除插件下载'
echo '[+] 正在解压一堆nginx大礼包中'
tar -xzf nginx.tar.gz -C /usr/local/share/;
tar -xzf zlib-1.2.8.tar.gz -C /usr/local/share/;

tar -xzf pcre-8.38.tar.gz  -C /usr/local/share/;
tar -xzf solr-5.4.0.tgz -C /usr/local/share;
tar -xzf ngx_cache_purge-2.3.tar.gz -C /usr/local/share;

echo '[+] 首先编译pcre'
cd /usr/local/share/pcre-8.38
./configure;
make;
make install;

echo '[+] 然后编译zlib'
cd /usr/local/share/zlib-1.2.8
./configure;
make;
make install;

echo '[+]最后带上nginx自己'
cd /usr/local/share/nginx-1.8.0;
 ./configure --add-module=/usr/local/share/ngx_cache_purge-2.3;
make;
make install

echo '[+] 下载配置文件中'
cd /usr/local/nginx/conf
wget https://raw.githubusercontent.com/Hope6537/hope-tactical-equipment/master/hope-note-module/hope-config-note/nginx_default_tomcat.conf
wget https://raw.githubusercontent.com/Hope6537/hope-tactical-equipment/master/hope-note-module/hope-config-note/nginx_image_server.conf

cd ~
echo '[+] 导出环境变量中'
echo 'export NGINX_HOME=/usr/local/nginx/sbin' >> ~/custom_profile
echo 'export PATH=$PATH:$NGINX_HOME/bin' >> ~/custom_profile
echo "alias nginx.start='/usr/local/nginx/sbin/nginx'" >> ~/custom_alias
echo "alias nginx.stop='/usr/local/nginx/sbin/nginx -s stop'" >> ~/custom_alias


#安装数据库层面的数据

#REDIS 主从和集群
if [ ! -f redis.tar.gz ]; then
  curl 'http://mirrors.hope6537.com/redis-3.0.5.tar.gz' >> redis.tar.gz;
fi
echo '[+] 完成redis下载'
tar -xzf redis.tar.gz -C /usr/local/share/;
cd /usr/local/share/redis-3.0.4
echo '[+] 编译Redis中'
./configure;
make & make install;
echo '[+] 完成编译,主从配置生成,进行sentinel配置生成'
echo 'slaveof www.hope6537.com 6379
masterauth redispass
requirepass redispass' >> /etc/redis_slave.conf
echo 'masterauth redispass
requirepass redispass' >> /etc/redis_master.conf
echo '[+] master配置文件为/etc/redis_master.conf,slave配置文件为/etc/redis_slave.conf'
echo '[+] 进行sentinel配置生成'
echo 'port 26379
 sentinel monitor mymaster 172.17.16.7 6379 2
 sentinel auth-pass mymaster redispass
 sentinel down-after-milliseconds mymaster 30000
 sentinel failover-timeout mymaster 900000
 sentinel can-failover mymaster yes
 sentinel parallel-syncs mymaster 1
' >> /etc/sentinel.conf

cd ~
echo "alias redis.start='redis-server /etc/sentinel.conf --sentinel'"

echo '[+] 导出环境变量中'
echo 'export REDIS_HOME=/usr/local/share/redis-3.0.4' >> ~/custom_profile
echo 'export PATH=$PATH:$REDIS_HOME/bin' >> ~/custom_profile
echo "alias redis.start='/usr/local/share/redis-3.0.4/src/redis-server /etc/sentinel.conf --sentinel'" >> ~/custom_alias
echo "alias redis.stop='/usr/local/share/redis-3.0.4/src/redis-server stop'" >> ~/custom_alias

#MYSQL读写分离

#ElasticSearch

if [ ! -f elasticsearch.tar.gz ]; then
    curl 'http://mirrors.hope6537.com/elasticsearch.tar.gz' >> elasticsearch.tar.gz
fi
echo '[+] 完成elasticsearch下载'
tar -xzf elasticsearch.tar.gz -C /usr/local/share/;
#然后需要添加一个elasticsearch用户
rm -rf /home/elatsicsearch
expect  -c  '
        set timeout 3600;
        spawn adduser elatsicsearch;
        expect "password:"
        send "espasswd\n";
        expect "password:"
        send "espasswd\n";
        expect "Full Name";
        send "es\n";
        expect "Room Number";
        send "es\n";
        expect "Work Phone";
        send "es\n";
        expect "Home Phone";
        send "es\n";
        expect "Other";
        send "es\n";
        expect "correct";
        send Y\n;
        interact;';


#对es目录进行授权
#expect  -c 'set timeout 3600; spawn su elatsicsearch expect "password:"; send "espasswd\n"; interact;';
cd /usr/local/share/elasticsearch-rtf/config
rm /usr/local/share/elasticsearch-rtf/config/elasticsearch.yml
wget https://raw.githubusercontent.com/Hope6537/hope-tactical-equipment/master/hope-note-module/hope-config-note/elasticsearch.conf
sudo chown -R elatsicsearch:admin /usr/local/share/elasticsearch-rtf/

echo '[+] 导出环境变量中'
echo 'export ES_HOME=/usr/local/share/redis-3.0.4' >> ~/custom_profile
echo 'export PATH=$PATH:$ES_HOME/bin' >> ~/custom_profile
echo "alias es.start='/usr/local/share/elasticsearch-rtf/bin/elasticsearch'" >> ~/custom_alias
echo "alias es.stop='/usr/local/share/elasticsearch-rtf/bin/elasticsearch stop'" >> ~/custom_alias

#测试

#启动
