#! /bin/bash

./pmd/bin/pmd check \
  --dir src/risk \
  --rulesets rulesets/java/metrics/basic.xml,rulesets/java/metrics/coupling.xml,rulesets/java/metrics/cyclomatic.xml \
  --format text \
  --report-file project-metrics-counts.txt