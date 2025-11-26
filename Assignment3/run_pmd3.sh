#! /bin/bash

./pmd/bin/pmd check -d ../../../assignment3-landrove-perez-gorgoroso/src -f text -R \
  category/java/bestpractices.xml,category/java/design.xml,category/java/unusedcode.xml \
  > report.html