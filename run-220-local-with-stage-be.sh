cd "$(dirname "$0")" || exit
  #"http://localhost:8080/"


pkill -f react
lsof -ti tcp:3000 | xargs kill -kill

#FE
#todo add new
nohup ./src-js/react/run-react-local.sh &
