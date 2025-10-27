#! /bin/bash

./pmd/bin/pmd check -d ../../guava -f html -R category/java/bestpractices.xml,category/java/design.xml,category/java/unusedcode.xml > report.html