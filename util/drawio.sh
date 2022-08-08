cd ../src/main/drawio
ls
for d in $(find "`pwd`" -type d)
do
  cd "$d" && /Applications/draw.io.app/Contents/MacOS/draw.io -x -f svg -o ./ ./
done
