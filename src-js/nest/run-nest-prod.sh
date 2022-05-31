cd "$(dirname "$0")" || exit

npm i
npm run build
npm run start:prod