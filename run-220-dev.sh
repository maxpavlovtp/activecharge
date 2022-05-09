cd "$(dirname "$0")" || exit

nohup ./src-js/react/run-react-dev.sh &
nohup ./src-js/nest/run-nest-dev.sh &