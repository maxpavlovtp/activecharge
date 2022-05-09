cd "$(dirname "$0")" || exit

pkill -f nest
npm run start:dev
# go to http://localhost/