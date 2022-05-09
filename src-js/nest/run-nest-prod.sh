cd "$(dirname "$0")" || exit

npm i -f
pkill -f nest
npm run start:prod
# go to http://localhost/