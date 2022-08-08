cd ../src/main/wavedrom
for d in $(find "`pwd`" -type d); do
  cd "$d"
  for f in *.py; do python3 "$f";done
done
