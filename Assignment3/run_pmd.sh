#! /bin/bash

./pmd/bin/pmd check -d src/risk -f text -R \
  category/java/bestpractices.xml,category/java/design.xml,category/java/unusedcode.xml \
  > report.html