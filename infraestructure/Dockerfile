FROM ubuntu:latest

RUN apt update && apt install openssh-server sudo -y
RUN useradd -rm -s /bin/bash -g root -G sudo -u 1000 username
RUN echo 'username:12345678' | chpasswd
RUN service ssh start

EXPOSE 22

CMD ["/usr/sbin/sshd", "-D"]