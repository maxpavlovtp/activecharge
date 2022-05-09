cd "$(dirname "$0")" || exit

npm i -f
npm run start:prod
# go to http://localhost/