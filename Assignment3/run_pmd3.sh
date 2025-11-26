#! /bin/bash

./pmd/bin/pmd check \
--dir ../../../assignment3-landrove-perez-gorgoroso/src \
--rulesets rulesets/java/metrics/basic.xml,rulesets/java/metrics/coupling.xml,rulesets/java/metrics/cyclomatic.xml \
--format text \
--report-file project-metrics-counts-refactored.txt