cd "$(dirname "$0")" || exit

pkill -f react
lsof -ti tcp:3000 | xargs kill -kill

#FE
nohup ./src-js/react/run-react-localLimited.sh &
