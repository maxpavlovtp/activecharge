cd "$(dirname "$0")" || exit


pkill -f java
pkill -f react
pkill -f nest

systemctl start run-220-nest.service
systemctl start run-220-spring.service
systemctl start run-220.service

