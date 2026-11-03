#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
mkdir -p out
javac -Xlint:all -d out src/assig3_3/*.java
for n in 0 1 3 10; do
  output=$(printf '%s\n' "$n" | timeout 15 java -cp out assig3_3.Main)
  actual=$(grep -c '== preparing one more salad ==' <<< "$output" || true)
  if [[ "$actual" -ne "$n" ]] || ! grep -q "Prepared $n salads. Done." <<< "$output"; then
    printf 'FAIL count=%s actual=%s\n' "$n" "$actual" >&2; exit 1
  fi
  echo "PASS requested=$n prepared=$actual"
done
