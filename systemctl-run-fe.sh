cd "$(dirname "$0")" || exit

#npm install -g serve
export PORT=80
serve -s src-js/react/build

